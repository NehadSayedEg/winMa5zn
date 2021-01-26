package com.nehad.wininventory.UI.ScanActivity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.sqlite.db.SimpleSQLiteQuery;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.dbexporterlibrary.ExportDbUtil;
import com.android.dbexporterlibrary.ExporterListener;
import com.nehad.wininventory.Database.Model.HeaderWithDetails;
import com.nehad.wininventory.Database.Model.StockCount_header;
import com.nehad.wininventory.Database.Model.StockDetail;
import com.nehad.wininventory.Database.appDatabase;
import com.nehad.wininventory.R;
import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import au.com.bytecode.opencsv.CSVWriter;

import static java.lang.Float.valueOf;
import static java.util.Comparator.comparing;

public class ScanActivity extends AppCompatActivity {
    ImageView back_btn , attach_bt;
    Intent intent;
    StockCount_header stockCount_header;
    EditText barcodeEt;
    RecyclerView scanRecyclerView ;
    List<StockDetail> stockDetailList;
    private ScanAdapter  scanAdapter ;
    Activity activity ;
    Context context ;
 int updateDate ;



    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.offwhite));
        setSupportActionBar(toolbar);


//        back_btn = findViewById(R.id.back_btn);
        barcodeEt = findViewById(R.id.scan_barcode_et);
        barcodeEt.requestFocus();




        int hours = (int) TimeUnit.MILLISECONDS.toHours(System.currentTimeMillis());
        int minutes = (int) TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis());
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
//        LocalDateTime localDate = LocalDateTime.parse(date, formatter);
//        long timeInMilliseconds = localDate.atOffset(ZoneOffset.UTC).toInstant().toEpochMilli();
//        attach_bt = findViewById(R.id.attach_btn);
        scanRecyclerView = findViewById(R.id.scanrv);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
       // layoutManager.setReverseLayout(true);
       // layoutManager.setStackFromEnd(true);
        scanRecyclerView.setHasFixedSize(true);
        scanRecyclerView.setLayoutManager(layoutManager);
//       scanRecyclerView.scrollToPosition(0);
        stockDetailList = new ArrayList<>();
        scanAdapter = new ScanAdapter(stockDetailList ,activity  ,context);
        scanRecyclerView.setAdapter(scanAdapter);
        barcodeEt.setFocusable(true);



        if (getIntent().getExtras() != null) {
            stockCount_header = (StockCount_header) getIntent().getSerializableExtra("lesson_details");
        Log.e(" Scan Activity ", stockCount_header.getFileName().toString() + "");
        }
        getItemDetails();

//
//        attach_bt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//         export();
//
//            }
//        });

        barcodeEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.v("on text change insert ", s.toString());

                Log.e("on text change insert ", stockCount_header.getDocumentNo()+"");


                if(! s.toString().isEmpty()){
                    insertDetials();



                }
            }


            @Override
            public void afterTextChanged(Editable s) {
            }
        });
//        back_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });



    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu ,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if( id ==R.id.attach){
              export();
        }
        return  true;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public  void insertDetials() {
        if(! barcodeEt.getText().toString().isEmpty()) {
//            barcodeEt.getText().clear();
            String barcode = barcodeEt.getText().toString().trim();
            final StockDetail stockDetail = new StockDetail();
            stockDetail.setBarcode(barcode);
            stockDetail.setQty(1);
            int seconds = (int) TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
            updateDate = seconds;

            String scanDateFormate  =  new
                    SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy" , Locale.getDefault()).format(new Date()).toString();
            String updateDateFormate  =  new
                    SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy" , Locale.getDefault()).format(new Date()).toString();
            String scanDate = scanDateFormate ;
            stockDetail.setUpdateDateFormate(updateDateFormate);
            stockDetail.setScanDate(scanDate);
            stockDetail.setUpdateDate(updateDate);

            long documentNo = stockCount_header.getDocumentNo();
            stockDetail.setDocumentNumber(documentNo);
            stockDetailList.add(stockDetail);
            appDatabase.getInstance(getApplicationContext()).stockHeaderDao().insertOrUpdate(stockDetail);
            for ( int i = 0 ; i<stockDetailList.size(); i++){
                Log.e( "Details updateDate" , stockDetailList.get(i).getUpdateDate()+ "");

                Log.e( "Details " , stockDetailList.get(i).getBarcode()+ "");
                Log.e( "Details " , stockDetailList.get(i).getQty()+ "");



            }


//            List<HeaderWithDetails> headerWithDetails = appDatabase.getInstance(getApplicationContext()).stockHeaderDao().getAlldetials(documentNo);
//            for(int i=0 ; i<headerWithDetails.size() ; i++){
//                Log.e( "Headers ???" , headerWithDetails.get(i).getStockCount_header().getFileName().toString());
//
//                for(int j = 0 ; j<headerWithDetails.get(i).getStockDetail().size() ; j++ ){
//                    Log.e( "Details ???" , headerWithDetails.get(i).getStockDetail().get(j).getBarcode());
//                    Log.e( "Details ???" , headerWithDetails.get(i).getStockDetail().get(j).getQty()+ "");
//                    Log.e( "Details ???" , headerWithDetails.get(i).getStockDetail().get(j).getDocumentNumber()+"");

//
//                }
//            }


            barcodeEt.setText("");
            stockDetailList.clear();
            stockDetailList.addAll(appDatabase.getInstance(getApplicationContext()).stockHeaderDao().getStockDetialsByDate(documentNo));
           //Collections.sort(stockDetailList ,StockDetail::compareTo );
            scanAdapter.notifyDataSetChanged();
//             int  index = stockDetailList.size();
//             scanAdapter.notifyItemInserted(index);
            List<HeaderWithDetails> tablesWithDetials = appDatabase.getInstance(getApplicationContext()).stockHeaderDao()
                    .getAllHeaders();





        }

       // Collections.sort(stockDetailList ,StockDetail::compareTo );
        scanAdapter.notifyDataSetChanged();
    }




    private void export(){

        StringBuilder data  = new StringBuilder();
        data.append("Barcode, Qty,Update Date   , SacnDate" );

        for( int i =0 ; i<stockDetailList.size(); i++){
//            data.append("\n"+ String.valueOf(i) + String.valueOf(stockDetailList.get(i).getBarcode()));

            data.append("\n"+  String.valueOf(stockDetailList.get(i).getBarcode()) +","+
                    String.valueOf(stockDetailList.get(i).getQty()) + ","+ String.valueOf(stockDetailList.get(i).getUpdateDateFormate())+ ","+ String.valueOf(stockDetailList.get(i).getScanDate()));



        }
        try {
             FileOutputStream outputStream  = openFileOutput("data.csv" ,Context.MODE_PRIVATE);
            outputStream.write(data.toString().getBytes());
            outputStream.close();

               //exporting
            Context context = getApplicationContext();
            File fileLocation = new File(getFilesDir() , "data.csv");
            Uri path = FileProvider.getUriForFile(context, "com.nehad.wininventory.fileprovider", fileLocation);

            Intent fileIntent = new Intent(Intent.ACTION_SEND);
            fileIntent.setType("text/csv");
            fileIntent.putExtra(Intent.EXTRA_SUBJECT, "Data");
            fileIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            fileIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);


            fileIntent.putExtra(Intent.EXTRA_STREAM, path);
            startActivity(Intent.createChooser(fileIntent, "Send mail"));

        }catch (Exception e){

        }


    }


    private void  getItemDetails(){
        class GetSheetsAsyncTask extends  AsyncTask<Void ,Void , List<StockDetail>>{

            @Override
            protected List<StockDetail> doInBackground(Void... voids) {

                long docNo = stockCount_header.getDocumentNo();

                return appDatabase.getInstance(getApplication()).stockHeaderDao().getStockDetialsByDate(docNo);

            }
            @Override
            protected void onPostExecute( List<StockDetail> stockDetails) {
                super.onPostExecute(stockDetails);
                if(stockDetailList.size() == 0){
                    stockDetailList.addAll(stockDetails);
                    //Collections.sort(stockDetailList ,StockDetail::compareTo );
                    scanAdapter.notifyDataSetChanged();

                }else {
                    stockDetailList.add(0 ,stockDetails.get(0));
                  //  Collections.sort(stockDetailList ,StockDetail::compareTo );
                    scanAdapter.notifyItemInserted(0);
                }
              // Collections.sort(stockDetailList ,StockDetail::compareTo );
//                scanRecyclerView.smoothScrollToPosition(0);

            }
        }

        new GetSheetsAsyncTask().execute();
    }




}