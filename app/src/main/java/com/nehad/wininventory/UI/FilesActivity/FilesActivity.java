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
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.nehad.wininventory.AddSheetDialog;
import com.nehad.wininventory.Database.Model.StockCount_header;
import com.nehad.wininventory.Database.appDatabase;
import com.nehad.wininventory.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FilesActivity extends AppCompatActivity implements AddSheetDialog.addSheetListener {
    Context context ;
    ImageView addfile_btn ;


    private DateFormat dateFormat ;
    RecyclerView filesRecyclerView ;
    List<StockCount_header> stockCountHeaderList;
    private FilesAdapter filesAdapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_activity);

        addfile_btn = findViewById(R.id.addfile_btn);
        filesRecyclerView = findViewById(R.id.files_rv);
        filesRecyclerView.setLayoutManager(new
           StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        stockCountHeaderList = new ArrayList<>();
        filesAdapter = new FilesAdapter(stockCountHeaderList);
        filesRecyclerView.setAdapter(filesAdapter);







        getSheets();
        addfile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               addDialog();
            }
        });



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
//                Log.d("mysheets", stockCountHeaderList.size()+ "size ");
                if(stockCountHeaderList.size() == 0){
                    stockCountHeaderList.addAll(stockCountHeaders);
                    filesAdapter.notifyDataSetChanged();
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
        if(requestCode == RESULT_OK &&  requestCode == Request_code){

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
                SimpleDateFormat("EEEE , dd MMMMM yyyyy  HH:mm a" , Locale.getDefault()).toString();

        final StockCount_header stockCount_header = new StockCount_header();
        stockCount_header.setFileName(sheetName);
        stockCount_header.setDocumentDate(sheetDate);


        @SuppressLint("StaticFieldLeak")
        class SaveSheetAsyncTask extends AsyncTask <Void ,Void , Void> {


            @Override
            protected Void doInBackground(Void... voids) {
                appDatabase.getInstance(getApplicationContext()).stockHeaderDao().insertSheet(stockCount_header);
                return null;

            }

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
