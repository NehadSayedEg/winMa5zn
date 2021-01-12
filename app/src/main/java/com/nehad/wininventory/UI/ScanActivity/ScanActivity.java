package com.nehad.wininventory.UI.ScanActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
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
import java.util.ArrayList;
import java.util.List;

import au.com.bytecode.opencsv.CSVWriter;

import static java.lang.Float.valueOf;

public class ScanActivity extends AppCompatActivity {
    ImageView back_btn , attach_bt;
    Intent intent;
    StockCount_header stockCount_header;
    EditText barcodeEt;
    TextView qtyTV;
    RecyclerView scanRecyclerView ;
    List<StockDetail> stockDetailList;
    private ScanAdapter  scanAdapter ;
    Activity activity ;
    Context context ;
    private ExportDbUtil exportDbUtil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        back_btn = findViewById(R.id.back_btn);
        barcodeEt = findViewById(R.id.scan_barcode_et);
        qtyTV = findViewById(R.id.qty);
        attach_bt = findViewById(R.id.attach_btn);

        scanRecyclerView = findViewById(R.id.scanrv);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        scanRecyclerView.setHasFixedSize(true);
        scanRecyclerView.setLayoutManager(layoutManager);
        scanRecyclerView.scrollToPosition(0);
        stockDetailList = new ArrayList<>();
        scanAdapter = new ScanAdapter(stockDetailList ,activity  ,context);
        scanRecyclerView.setAdapter(scanAdapter);



        if (getIntent().getExtras() != null) {
            stockCount_header = (StockCount_header) getIntent().getSerializableExtra("lesson_details");
        Log.e(" Scan Activity ", stockCount_header.getFileName().toString() + "");
        }
        getItemDetails();


        attach_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


         exportDB();

            }
        });





        barcodeEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                insertDetials();
            }


            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }





    private void exportDB(){


     try {
         //saving the file into device

         FileOutputStream out = openFileOutput("ma5zn.csv", Context.MODE_PRIVATE);
         long docNo = stockCount_header.getDocumentNo();
         List<StockDetail> stockDetailList = appDatabase.getInstance(getApplicationContext()).stockHeaderDao()
                 .getAllItems(docNo);
         for( int i =0 ;  i<stockDetailList.size() ; i++)
                {
                    out.write( stockDetailList.get(i).getBarcode().getBytes());
                    out.write((int) stockDetailList.get(i).getDocumentNumber());
                    out.write((int) stockDetailList.get(i).getQty());
                }



         out.write(appDatabase.getInstance(getApplicationContext()).stockHeaderDao().getAllItemsDetials(docNo).toString().getBytes());
//         out.write(database.itemDao().getAllItems().toString().getBytes());

         out.close();


         Context context = getApplicationContext();
         File filelocation = new File(getFilesDir(), "ma5zn.csv");
         Uri path = FileProvider.getUriForFile(context, "com.nehad.wininventory.fileprovider", filelocation);

         //exporting
         Intent fileIntent = new Intent(Intent.ACTION_SEND);
         fileIntent.setType("text/csv");
         fileIntent.putExtra(Intent.EXTRA_SUBJECT, "Data");
         fileIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
         fileIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);


         fileIntent.putExtra(Intent.EXTRA_STREAM, path);
         startActivity(Intent.createChooser(fileIntent, "Send mail"));
     } catch (Exception e) {
         e.printStackTrace();
     }

//        try {
//            File csvfile = new File(Environment.getExternalStorageDirectory() + "/csvfile.csv");
//            CSVReader reader = new CSVReader(new FileReader(csvfile.getAbsolutePath()));
//            String[] nextLine;
//            while ((nextLine = reader.readNext()) != null) {
//                // nextLine[] is an array of values from the line
//                System.out.println(nextLine[0] + nextLine[1] + "etc...");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
//        }

    }






     public  void insertDetials() {
         if(! barcodeEt.getText().toString().isEmpty()){
             String barcode =  barcodeEt.getText().toString();
             final StockDetail stockDetail = new StockDetail();
             stockDetail.setBarcode(barcode);
             stockDetail.setQty(1);

             long documentNo = stockCount_header.getDocumentNo();
          stockDetail.setDocumentNumber(documentNo);

             stockDetailList.clear();
             stockDetailList.addAll(appDatabase.getInstance(getApplicationContext()).stockHeaderDao().getAllItems(documentNo));
             scanAdapter.notifyDataSetChanged();
//             stockDetailList.add(stockDetail);

//             int  index = stockDetailList.size();
//             scanAdapter.notifyItemInserted(index);
        @SuppressLint("StaticFieldLeak")
        class SaveSheetAsyncTask extends AsyncTask<Void ,Void , Void> {


            @Override
            protected Void doInBackground(Void... objs) {



                // retrieve auto incremented note id
//                long stockDetialId = appDatabase.getInstance(getApplicationContext()).
//                        stockHeaderDao().insertStockItemDetail(stockDetail);
//                stockDetail.setId(stockDetialId);
                appDatabase.getInstance(getApplication()).stockHeaderDao().insertOrUpdate(stockDetail);

                String qtyValue = String.valueOf(stockDetail.getQty());
                qtyTV.setText(qtyValue);



                List<HeaderWithDetails> tablesWithDetials = appDatabase.getInstance(getApplicationContext()).stockHeaderDao()
                        .getAllHeaders();
//                for( int i =0 ;  i<tablesWithDetials.size() ; i++)
//                {
//                   Log.v("Header file name  " ,tablesWithDetials.get(i).stockCount_header.getFileName() );
//                    Log.v("Header  doc id " ,tablesWithDetials.get(i).stockCount_header.getDocumentNo()+"" );
//
//                    for( int j =0 ;  j<tablesWithDetials.size() ; j++)
//                    {
////                        Log.v("detail id00- " ,tablesWithDetials.get(i).stockDetail.get(j).getId()+"" );
//                        Log.v(" Detial barcode " ,tablesWithDetials.get(i).stockDetail.get(j).getBarcode()+"" );
//                        Log.v(" Detial DocID " ,tablesWithDetials.get(i).stockDetail.get(j).getDocumentNumber()+"" );
//
//
//                    }
//                }


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



    private void  getItemDetails(){
        class GetSheetsAsyncTask extends  AsyncTask<Void ,Void , List<StockDetail>>{

            @Override
            protected List<StockDetail> doInBackground(Void... voids) {

                long docNo = stockCount_header.getDocumentNo();
               return appDatabase.getInstance(getApplication()).stockHeaderDao().getAllItems(docNo);
            }
            @Override
            protected void onPostExecute( List<StockDetail> stockDetails) {
                super.onPostExecute(stockDetails);
                if(stockDetailList.size() == 0){
                    stockDetailList.addAll(stockDetails);
                    scanAdapter.notifyDataSetChanged();

                }else {
                    stockDetailList.add(0 ,stockDetails.get(0));
                    scanAdapter.notifyItemInserted(0);
                }
                scanRecyclerView.smoothScrollToPosition(0);
            }
        }

        new GetSheetsAsyncTask().execute();
    }

    private View.OnClickListener onAddingListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog decDialog = new Dialog(ScanActivity.this);
                decDialog.setContentView(R.layout.add_dialog); //layout for dialog
                decDialog.setTitle("Add a Value");
                decDialog.setCancelable(false); //none-dismiss when touching outside Dialog
                // set the custom dialog components - texts and image
                EditText addValue_et = decDialog.findViewById(R.id.addValue_et);
                View btnAdd = decDialog.findViewById(R.id.btn_add);
                View btnCancel = decDialog.findViewById(R.id.btn_cancel);

                btnAdd.setOnClickListener(onConfirmListener(addValue_et, decDialog));
                btnCancel.setOnClickListener(onCancelListener(decDialog));
                decDialog.show();
            }
        };
    }

    private View.OnClickListener onConfirmListener(final EditText addValue,  final Dialog dialog) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                StockDetail stockDetail = new StockDetail();
                float value =  valueOf(addValue.getText().toString());
                String barcode = stockDetail.getBarcode();
                appDatabase.getInstance(getApplication()).stockHeaderDao().updateAddDialog(barcode ,value);
                //adding new object to arraylist
//                stockDetailList.clear();
//                empArrayList.add(friend);
                //notify data set changed in RecyclerView adapter
                scanAdapter.notifyDataSetChanged();
                //close dialog after all
                dialog.dismiss();
            }
        };
    }
    private View.OnClickListener onCancelListener(final Dialog dialog) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        };
    }




}