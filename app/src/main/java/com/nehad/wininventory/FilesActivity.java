package com.nehad.wininventory;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.text.DateFormat;

public class FilesActivity extends AppCompatActivity {
    Context context ;
    ImageView addfile_btn ;

    private DateFormat dateFormat ;
    RecyclerView sheetname_recyclerView ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_activity);

        addfile_btn = findViewById(R.id.addfile_btn);


        addfile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               addDialog();
            }


        });



    }

    private void addDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(FilesActivity.this );
        dialog.setIcon(R.drawable.ic_baseline_add_alert_24);
        dialog.setTitle("Please Add Sheet Name");
        final EditText sheetName = new EditText(FilesActivity.this);
        sheetName.setInputType(InputType.TYPE_CLASS_TEXT);
        dialog.setView(sheetName);

           dialog.setPositiveButton("Add", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialog, int which) {



                Toast.makeText(FilesActivity.this , sheetName.getText().toString() , Toast.LENGTH_LONG).show();

               }
           });
           dialog.setNegativeButton(" Cancel ", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialog, int which) {
                   dialog.cancel();
               }
           });
           dialog.show();


    }
}