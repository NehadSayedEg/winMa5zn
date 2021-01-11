package com.nehad.wininventory.Database.Model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;


@Entity(tableName = "Stock_detail_table")
public class StockDetail implements Serializable {

//    @PrimaryKey (autoGenerate = true)
//    @NonNull
//    @ColumnInfo(name = "id_stock_details")
//    private long id ;

    @ForeignKey
            (entity = StockCount_header.class,
                    parentColumns = "document_number",
                    childColumns = "document_number")
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "barcode")
    private String barcode ;

    @ColumnInfo(name = "document_number")
    private long documentNumber ;


    @ColumnInfo(name = "qty")
    private float qty ;


    public StockDetail( @NonNull String barcode, long documentNumber, float qty) {
        this.barcode = barcode;
        this.documentNumber = documentNumber;
        this.qty = qty;
    }

    public StockDetail() {

    }

//    public long getId() {
//        return id;
//    }
//
//    public void setId(long id) {
//        this.id = id;
//    }

    @NonNull
    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(@NonNull String barcode) {
        this.barcode = barcode;
    }

    public long getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(long documentNumber) {
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
//                "id=" + id +
                ", barcode='" + barcode + '\'' +
                ", documentNumber=" + documentNumber +
                ", qty=" + qty +
                '}';
    }
}
