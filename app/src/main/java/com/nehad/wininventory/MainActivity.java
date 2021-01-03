package com.nehad.wininventory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nehad.wininventory.Database.Model.Item;
import com.nehad.wininventory.Database.appDatabase;
import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilePermission;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import static com.google.android.material.internal.ContextUtils.getActivity;

public class MainActivity extends AppCompatActivity {
    EditText barcode_et , qty_et ;
    Button save_btn  ,clear_btn ;
    RecyclerView itemsReyclerview ;
    private List<Item> itemList ;
    Menu menu ;

    appDatabase database ;
    ItemAdapter itemAdapter ;
    RecyclerView itemRecyclerView;
    LinearLayoutManager linearLayoutManager ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        barcode_et = findViewById(R.id.barcode_et);
        qty_et = findViewById(R.id.qty_et);
        save_btn = findViewById(R.id.save_btn);
        clear_btn = findViewById(R.id.clea_btn);
        itemsReyclerview = findViewById(R.id.rv);
        database = appDatabase.getInstance(this);
        itemList = database.itemDao().getAllItems();

          linearLayoutManager = new LinearLayoutManager(this);
          itemsReyclerview.setLayoutManager(linearLayoutManager);
          itemAdapter =  new ItemAdapter(itemList , MainActivity.this );
        itemsReyclerview.setNestedScrollingEnabled(false);

          itemsReyclerview.setAdapter(itemAdapter);




        clear_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                barcode_et.setText("");
                qty_et.setText("");
            }
        });





        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                String barcode = barcode_et.getText().toString();
                String qty = qty_et.getText().toString();

                 if(  !barcode.equals("") || !qty.equals("")) {
                     float qtyf = Float.parseFloat(qty);

                     Item item =  new Item( barcode , qtyf);
                     database.itemDao().insertItem(item);

//                     item.setBarcode(barcode);
//                     item.setAmount(qtyf);
                     Log.e("item " , item.getBarcode());

                     barcode_et.setText("");
                     qty_et.setText("");
                     itemList.clear();
                     itemList.addAll(database.itemDao().getAllItems());
                     itemAdapter.notifyDataSetChanged();
                 }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.attach:
                export();
                return  true ;
            case R.id.deleteall:
                deleteAll();
                return true;
                

            default:
                //Nothing to do.
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteAll() {
        new AlertDialog.Builder(this)
                .setTitle("Clear Data")
                .setMessage("Do you really want to Delete All Data?")
                .setIcon(R.drawable.ic_baseline_add_alert_24)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        itemList.addAll(database.itemDao().getAllItems());
                        database.itemDao().deleteAllItems(itemList);
                        itemList.removeAll(database.itemDao().getAllItems());
                        itemAdapter.notifyDataSetChanged();
                        itemList.clear();

                        Toast.makeText(MainActivity.this, "Data Deleted.....", Toast.LENGTH_SHORT).show();
                    }})
                .setNegativeButton(android.R.string.no, null).show();

    }

    public void export()  {
//        CSVReader reader = new CSVReader(new FileReader("yourfile.csv"));
//        itemList = reader.readAll();

        try {
            File csvfile = new File(Environment.getExternalStorageDirectory() + "/csvfile.csv");
            CSVReader reader = new CSVReader(new FileReader(csvfile.getAbsolutePath()));
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                // nextLine[] is an array of values from the line
                System.out.println(nextLine[0] + nextLine[1] + "etc...");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "The specified file was not found", Toast.LENGTH_SHORT).show();
        }





//     try {
//         //saving the file into device
//
//         FileOutputStream out = openFileOutput("ma5zn.csv", Context.MODE_PRIVATE);
//         out.write(database.itemDao().getAllItems().toString().getBytes());
////         out.write(database.itemDao().getAllItems().toString().getBytes());
//
//         out.close();
//
//
//         Context context = getApplicationContext();
//         File filelocation = new File(getFilesDir(), "ma5zn.csv");
//         Uri path = FileProvider.getUriForFile(context, "com.nehad.wininventory.fileprovider", filelocation);
//
//         //exporting
//         Intent fileIntent = new Intent(Intent.ACTION_SEND);
//         fileIntent.setType("text/csv");
//         fileIntent.putExtra(Intent.EXTRA_SUBJECT, "Data");
//         fileIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//         fileIntent.putExtra(Intent.EXTRA_STREAM, path);
//         startActivity(Intent.createChooser(fileIntent, "Send mail"));
//     } catch (Exception e) {
//         e.printStackTrace();
//     }
//
 }



 }