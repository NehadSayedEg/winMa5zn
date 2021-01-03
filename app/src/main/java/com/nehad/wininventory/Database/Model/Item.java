package com.nehad.wininventory.Database.Model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;


@Entity(tableName = "item_table")

public class Item implements Serializable {

    @PrimaryKey(autoGenerate = false)
    @NonNull
    @ColumnInfo(name = "barcode")
    private String barcode ;

    @ColumnInfo(name = "amount")
    private float amount ;



    public Item(@NonNull String barcode, float amount) {
        this.barcode = barcode;
        this.amount = amount;
    }

    @NonNull
    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(@NonNull String barcode) {
        this.barcode = barcode;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}
