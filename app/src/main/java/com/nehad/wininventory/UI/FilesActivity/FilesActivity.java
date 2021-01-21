package com.nehad.wininventory.UI.FilesActivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.nehad.wininventory.AddSheetDialog;
import com.nehad.wininventory.Database.Model.StockCount_header;
import com.nehad.wininventory.Database.appDatabase;
import com.nehad.wininventory.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FilesActivity extends AppCompatActivity implements AddSheetDialog.addSheetListener {
    Context context ;
    ImageView addfile_btn ;
    EditText search_input ;


    private DateFormat dateFormat ;
    RecyclerView filesRecyclerView ;
    List<StockCount_header> stockCountHeaderList;
    private FilesAdapter filesAdapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_activity);
        search_input = findViewById(R.id.search_input);
        addfile_btn = findViewById(R.id.addfile_btn);
        filesRecyclerView = findViewById(R.id.files_rv);
        filesRecyclerView.setLayoutManager(new
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        stockCountHeaderList = new ArrayList<>();
        filesAdapter = new FilesAdapter(stockCountHeaderList);
        filesRecyclerView.setAdapter(filesAdapter);


        getSheets();


        search_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                search_input.setFocusable(true);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                filesAdapter.cancelTimer();

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(stockCountHeaderList.size() != 0){
                    filesAdapter.SearchFiles(s.toString());

                }

            }
        });


        addfile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDialog();
            }
        });


        filesAdapter.notifyDataSetChanged();
//        stockCountHeaderList.add(s)
        int  index = stockCountHeaderList.size();
        filesAdapter.notifyItemInserted(index);

    }

    private void  getSheets(){
        class GetSheetsAsyncTask extends  AsyncTask<Void ,Void , List<StockCount_header>>{

            @Override
            protected List<StockCount_header> doInBackground(Void... voids) {
                return appDatabase.getInstance(getApplication()).stockHeaderDao().getAllSheets();
            }
            @Override
            protected void onPostExecute( List<StockCount_header> stockCountHeaders) {
                super.onPostExecute(stockCountHeaders);
                if(stockCountHeaderList.size() == 0){
                    stockCountHeaderList.addAll(stockCountHeaders);
                    filesAdapter.notifyDataSetChanged();
                    // Log.d("mysheets", stockCountHeaderList.get(1).getDocumentNo() + "size ");

                }else {
                    stockCountHeaderList.add(0 ,stockCountHeaders.get(0));
                    filesAdapter.notifyItemInserted(0);
                }
                filesRecyclerView.smoothScrollToPosition(0);
            }
        }

        new GetSheetsAsyncTask().execute();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_OK ){
            getSheets();

       }
    }

    private void addDialog() {
        AddSheetDialog addSheetDialog = new AddSheetDialog();
        addSheetDialog.show(getSupportFragmentManager() , "Add Dialog");
    }

    @Override
    public void applyText(String sheetName) {
        Log.e(" sheet name " +sheetName , " files activity");

        String sheetDate  =  new
                SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm a" , Locale.getDefault()).format(new Date()).toString();
        Log.e("date" , sheetDate);

        final StockCount_header stockCount_header = new StockCount_header();
        stockCount_header.setFileName(sheetName);
        stockCount_header.setDocumentDate(sheetDate);
        filesAdapter.notifyDataSetChanged();
     stockCountHeaderList.add(stockCount_header);
        int  index = stockCountHeaderList.size();
        filesAdapter.notifyItemInserted(index);



        @SuppressLint("StaticFieldLeak")
        class SaveSheetAsyncTask extends AsyncTask <Void ,Void , Void> {


            @Override
            protected Void doInBackground(Void... objs) {
                // retrieve auto incremented note id
                long j = appDatabase.getInstance(getApplicationContext()).stockHeaderDao().insertSheet(stockCount_header);
                stockCount_header.setDocumentNo(j);

                Log.e("ID ", "doInBackground: " + j);



                return null;
            }
          //  @Override
//            protected Void doInBackground(Void... voids) {
//                appDatabase.getInstance(getApplicationContext()).stockHeaderDao().insertSheet(stockCount_header);
//                return null;
//
//            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Intent intent = new Intent();
                setResult(RESULT_OK,intent);
                //finish();


            }
        }
        
      new SaveSheetAsyncTask().execute();


    }

}

