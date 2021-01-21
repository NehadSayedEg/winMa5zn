package com.nehad.wininventory.Database.Model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.nehad.wininventory.Database.DateTypeConverter;

import java.io.Serializable;
import java.util.Date;


@Entity(tableName = "Stock_detail_table")
public class StockDetail implements Serializable{

//}, Comparable<StockDetail> {
    @ForeignKey
            (entity = StockCount_header.class,
                    parentColumns = "document_number",
                    childColumns = "document_number")

    @PrimaryKey (autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id_stock_details")
    private long id ;


    @NonNull
    @ColumnInfo(name = "barcode")
    private String barcode;

    @ColumnInfo(name = "document_number")
    private long documentNumber;


    @ColumnInfo(name = "qty")
    private float qty;


    @ColumnInfo(name = "scanDate")
    @TypeConverters({DateTypeConverter.class})
    private int scanDate;

    @ColumnInfo(name = "updateDate")
    @TypeConverters({DateTypeConverter.class})
    private int updateDate;


    public StockDetail() {

    }

    public StockDetail( @NonNull String barcode, long documentNumber, float qty, int scanDate, int updateDate) {
        this.barcode = barcode;
        this.documentNumber = documentNumber;
        this.qty = qty;
        this.scanDate = scanDate;
        this.updateDate = updateDate;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public int getScanDate() {
        return scanDate;
    }

    public void setScanDate(int scanDate) {
        this.scanDate = scanDate;
    }

    public int getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(int updateDate) {
        this.updateDate = updateDate;
    }

//    @Override
//    public int compareTo(StockDetail o) {
//
//       return  (this.updateDate - o.updateDate);
////        if ((Long) this.updateDate < (Long)  o.updateDate)
////            return -1;
////        if (this.getUpdateDate().equals( o.updateDate))
////        return 0;
////
////        return 1;
//   }


}
