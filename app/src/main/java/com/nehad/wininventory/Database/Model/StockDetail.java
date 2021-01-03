package com.nehad.wininventory.Database.Model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;


@Entity(tableName = "Stock_detail_table")
public class StockDetail implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private int id ;

//        @PrimaryKey(autoGenerate = false)
//        @NonNull
        @ColumnInfo(name = "barcode")
        private String barcode ;
//
//    @PrimaryKey(autoGenerate = false)
//    @NonNull
    @ColumnInfo(name = "document_number")
    private int documentNumber ;


        @ColumnInfo(name = "qty")
        private float qty ;


    public StockDetail() {
    }

    public StockDetail(int id, @NonNull String barcode, int documentNumber, float qty) {
        this.id = id;
        this.barcode = barcode;
        this.documentNumber = documentNumber;
        this.qty = qty;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(@NonNull String barcode) {
        this.barcode = barcode;
    }

    public int getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(int documentNumber) {
        this.documentNumber = documentNumber;
    }

    public float getQty() {
        return qty;
    }

    public void setQty(float qty) {
        this.qty = qty;
    }

@NonNull
    @Override
    public String toString() {
        return "StockDetail{" +
                "id=" + id +
                ", barcode='" + barcode + '\'' +
                ", documentNumber=" + documentNumber +
                ", qty=" + qty +
                '}';
    }
}
