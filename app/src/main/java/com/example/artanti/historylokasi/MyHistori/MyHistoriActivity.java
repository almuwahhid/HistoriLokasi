package com.example.artanti.historylokasi.MyHistori;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.artanti.historylokasi.Model.Histori;
import com.example.artanti.historylokasi.R;
import com.example.artanti.historylokasi.Utility.DBUtil.HistoriDB;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Artanti on 4/3/2017.
 */

public class MyHistoriActivity extends AppCompatActivity {
    HistoriDB historiDB;
    MyHistoriAdapter myHistoriAdapter;
    ArrayList<Histori> historis;
    @BindView(R.id.recyclerView) protected RecyclerView recyclerView;
    @BindView(R.id.empty_data) protected RelativeLayout empty_data;
    boolean isSearchOpened = false;

    private MenuItem mSearchAction;

    EditText edtSearch;

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_history);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayOptions(
                ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_TITLE);
        getSupportActionBar().setTitle("Historiku");
        setRecyclerView();
    }

    private void setRecyclerView(){
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(lm);

        historiDB = new HistoriDB(this);
        historis = historiDB.getHistori();
        myHistoriAdapter = new MyHistoriAdapter(this, historis);
        recyclerView.setAdapter(myHistoriAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        searchBarAction(item);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        mSearchAction = menu.findItem(R.id.menu_search);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void initEditSearch(){
        edtSearch.setHint("Search");
        edtSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                /*if(hasFocus)
                    form_input_close.setVisibility(View.VISIBLE);*/
            }
        });
        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    hideKeyBoard();
                    historis = historiDB.getHistori(edtSearch.getText().toString());
                    myHistoriAdapter = new MyHistoriAdapter(MyHistoriActivity.this, historis);
                    recyclerView.setAdapter(myHistoriAdapter);
                    return true;
                }
                return false;
            }
        });

    }

    private void hideKeyBoard(){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edtSearch.getWindowToken(), 0);
    }

    private void searchBarAction(MenuItem item){
        if(!isSearchOpened){
            switch (item.getItemId()){
                case R.id.menu_search:
                    ActionBar.LayoutParams params = new ActionBar.LayoutParams(
                            ActionBar.LayoutParams.MATCH_PARENT,
                            ActionBar.LayoutParams.MATCH_PARENT);

                    View v = LayoutInflater
                            .from(getSupportActionBar().getThemedContext())
                            .inflate(R.layout.layout_pencarian_default, null);

                    getSupportActionBar().setDisplayShowCustomEnabled(true);
                    getSupportActionBar().setDisplayShowTitleEnabled(false);

                    getSupportActionBar().setCustomView(v, params);
                    edtSearch = getSupportActionBar().getCustomView().findViewById(R.id.edtSearch); //the text editor
                    initEditSearch();
                    edtSearch.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(edtSearch, InputMethodManager.SHOW_FORCED);
                    break;
                case android.R.id.home:
                    finish();
                    break;
            }

            //add the close icon
//            logo.setVisibility(View.GONE);
            mSearchAction.setVisible(false);
            isSearchOpened = true;
        }else{
            switch (item.getItemId()){
                case android.R.id.home:
                    hideKeyBoard();
                    getSupportActionBar().setDisplayShowTitleEnabled(true);
                    getSupportActionBar().setDisplayShowCustomEnabled(false);
                    mSearchAction.setVisible(true);
                    isSearchOpened = false;

                    historis = historiDB.getHistori();
                    myHistoriAdapter = new MyHistoriAdapter(this, historis);
                    recyclerView.setAdapter(myHistoriAdapter);
                    break;
            }
        }
    }
}