package com.nehad.wininventory.UI.ScanActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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

import com.nehad.wininventory.Database.Model.HeaderWithDetails;
import com.nehad.wininventory.Database.Model.StockCount_header;
import com.nehad.wininventory.Database.Model.StockDetail;
import com.nehad.wininventory.Database.appDatabase;
import com.nehad.wininventory.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

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
//        scanRecyclerView.setLayoutManager(new
//                StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));

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
//                exportDB();

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


//    private void exportDB(){
//        String DatabaseName = "Sycrypter.db";
//        File sd = Environment.getExternalStorageDirectory();
//        File data = Environment.getDataDirectory();
//        FileChannel source=null;
//        FileChannel destination=null;
//        String currentDBPath = "/data/"+ "com.synnlabz.sycryptr" +"/databases/"+DatabaseName ;
//        String backupDBPath = SAMPLE_DB_NAME;
//        File currentDB = new File(data, currentDBPath);
//        File backupDB = new File(sd, backupDBPath);
//        try {
//            source = new FileInputStream(currentDB).getChannel();
//            destination = new FileOutputStream(backupDB).getChannel();
//            destination.transferFrom(source, 0, source.size());
//            source.close();
//            destination.close();
//            Toast.makeText(this, "Your Database is Exported !!", Toast.LENGTH_LONG).show();
//        } catch(IOException e) {
//            e.printStackTrace();
//        }
//    }





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