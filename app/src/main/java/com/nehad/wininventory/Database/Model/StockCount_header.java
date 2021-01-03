     package com.nehad.wininventory.Database.Model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.text.DateFormat;

@Entity(tableName = "stock_count_header_table")

public class StockCount_header implements Serializable {

    @PrimaryKey(autoGenerate = true)
        @NonNull
        @ColumnInfo(name = "document_number")
        private int  documentNo ;

//    @PrimaryKey(autoGenerate = false)
//    @NonNull
    @ColumnInfo(name = "file_name")
    private String  fileName ;


    @ColumnInfo(name = "document_date")
    private int documentDate ;

    @ColumnInfo(name = "document_status")
    private int  documentStatus ;

    public StockCount_header() {
    }

    public StockCount_header(int documentNo, @NonNull String fileName, int documentDate, int documentStatus) {
        this.documentNo = documentNo;
        this.fileName = fileName;
        this.documentDate = documentDate;
        this.documentStatus = documentStatus;
    }

    public int getDocumentNo() {
        return documentNo;
    }

    public void setDocumentNo(int documentNo) {
        this.documentNo = documentNo;
    }

    @NonNull
    public String getFileName() {
        return fileName;
    }

    public void setFileName(@NonNull String fileName) {
        this.fileName = fileName;
    }

    public int getDocumentDate() {
        return documentDate;
    }

    public void setDocumentDate(int documentDate) {
        this.documentDate = documentDate;
    }

    public int getDocumentStatus() {
        return documentStatus;
    }

    public void setDocumentStatus(int documentStatus) {
        this.documentStatus = documentStatus;
    }

    @NonNull
    @Override
    public String toString() {
        return "StockCount_header{" +
                "documentNo=" + documentNo +
                ", fileName='" + fileName + '\'' +
                ", documentDate=" + documentDate +
                ", documentStatus=" + documentStatus +
                '}';
    }
}
