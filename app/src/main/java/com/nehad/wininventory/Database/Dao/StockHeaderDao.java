package com.nehad.wininventory.Database.Dao;

import android.util.Log;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.room.Transaction;
import androidx.sqlite.db.SimpleSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteQuery;

import com.nehad.wininventory.Database.Model.HeaderWithDetails;
import com.nehad.wininventory.Database.Model.StockCount_header;
import com.nehad.wininventory.Database.Model.StockDetail;

import java.util.Date;
import java.util.List;

@Dao
public interface StockHeaderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long  insertSheet(StockCount_header stockCount_header);

    @Query("UPDATE stock_detail_table SET  qty = qty + :value WHERE barcode = :barcode")
    void updateAddDialog(String barcode  , float value);

    @Query("SELECT * from stock_detail_table WHERE   document_number =:document_number ORDER BY updateDate  DESC ")
    List<StockDetail> getStockDetialsByDate(long document_number );


    @Query("UPDATE stock_detail_table SET qty = qty + 1  ,  updateDate =:updateDate WHERE barcode = :barcode AND document_number =:document_number ")
    void updateQuantity(String barcode ,long document_number  , int updateDate);
//    @Query("SELECT * FROM stock_detail_table WHERE updateDate BETWEEN :from AND :to")
//    List<User> findUsersBornBetweenDates(Date from, Date to);

    @Insert
    void insertStockDetails(StockDetail stockDetail);

    @Query("SELECT * from stock_detail_table WHERE barcode = :barcode   AND   document_number =:document_number  ")
    List<StockDetail> getItemByBarcode(String barcode , long document_number );


    default void insertOrUpdate(StockDetail stockDetail) {
        List<StockDetail> itemsFromDB = getItemByBarcode(stockDetail.getBarcode() , stockDetail.getDocumentNumber() );
        if (itemsFromDB.isEmpty())
            insertStockDetails( stockDetail);
        else{
            updateQuantity(stockDetail.getBarcode() , stockDetail.getDocumentNumber()  , stockDetail.getUpdateDate() );
        }
}

    @Query("SELECT * FROM  stock_count_header_table")
    public List<HeaderWithDetails> getAllHeaders();

    @Query("SELECT * FROM  Stock_detail_table  WHERE  document_number = :document_number ")
    List<StockDetail> getAllItems(long  document_number);

    @Query("SELECT * FROM  stock_count_header_table   WHERE  document_number = :document_number")
    public List<HeaderWithDetails> getAllItemsDetials(long  document_number);


    @Insert
    long  insertSheetHeader(StockCount_header stockCount_header);
    @Insert
    long insertStockItemDetail(StockDetail stockDetail);

    @Query("SELECT * FROM  STOCK_COUNT_HEADER_TABLE ")
    public HeaderWithDetails getHeaderWithDetails();



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void  insertItem(StockDetail stockDetail);

    @Query("UPDATE stock_detail_table  SET  qty = :qty    , updateDate = :updateDate  WHERE  document_number = :document_number AND barcode  = :barcode ")
    void  updateItem(String barcode  , float qty, long  document_number  , int updateDate);


    @Query("DELETE FROM    stock_detail_table   WHERE document_number = :document_number AND barcode  = :barcode")
    void  deleteItem(String barcode  , long  document_number);


    @Query("SELECT * FROM  stock_detail_table    WHERE   document_number = :document_number")
    List<HeaderWithDetails> getAlldetials(long document_number);







    @Delete
    void  deleteSheet(StockCount_header stockCount_header);

    @Query("SELECT * FROM  STOCK_COUNT_HEADER_TABLE")
    List<StockCount_header> getAllSheets();

    @Query("SELECT * FROM  STOCK_COUNT_HEADER_TABLE   WHERE  document_number  = :documentNo")
    List<StockCount_header> getArrangedItems(int documentNo);

    @Delete
    void deleteAllItems(List<StockCount_header> stockCount_headers);


}
