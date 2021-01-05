package com.nehad.wininventory.UI.ScanActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.nehad.wininventory.Database.Model.StockCount_header;
import com.nehad.wininventory.R;

public class ScanActivity extends AppCompatActivity {
    ImageView back_btn ;
    Intent  intent ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        StockCount_header stockCount_header = (StockCount_header) intent.getSerializableExtra("lesson_details");


        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}