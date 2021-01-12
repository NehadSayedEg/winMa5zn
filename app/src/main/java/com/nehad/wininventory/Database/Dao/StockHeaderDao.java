package com.nehad.wininventory.Database.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.nehad.wininventory.Database.Model.HeaderWithDetails;
import com.nehad.wininventory.Database.Model.StockCount_header;
import com.nehad.wininventory.Database.Model.StockDetail;

import java.util.List;

@Dao
public interface StockHeaderDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long  insertSheet(StockCount_header stockCount_header);

    @Query("SELECT * from stock_detail_table WHERE barcode = :barcode")
    List<StockDetail> getItemByBarcode(String barcode);

    @Query("UPDATE stock_detail_table SET qty = qty + 1 WHERE barcode = :barcode")
    void updateQuantity(String barcode);


    @Query("UPDATE stock_detail_table SET  qty = qty + :value WHERE barcode = :barcode")
    void updateAddDialog(String barcode  , float value);

    default void insertOrUpdate(StockDetail stockDetail) {
        List<StockDetail> itemsFromDB = getItemByBarcode(stockDetail.getBarcode());
        if (itemsFromDB.isEmpty())
            insertStockDetails(stockDetail);
        else{
            updateQuantity(stockDetail.getBarcode());
    }
}



    @Insert
    void insertStockDetails(StockDetail stockDetailList);

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

    @Query("UPDATE stock_detail_table  SET  qty = :qty  WHERE  document_number = :document_number AND barcode  = :barcode")
    void  updateItem(String barcode  , float qty, long  document_number);




    @Delete
    void  deleteItem(StockDetail stockDetail);
//    @Query("SELECT * FROM  Stock_detail_table")
//    List<StockDetail> getAllItems();

//    @Query("SELECT * FROM  stock_detail_table   ORDER By  id DESC")
//    List<StockDetail> getArrangedItems();

//    @Delete
//    void deleteAllItems(List<StockDetail> stockDetailList);







    @Query("SELECT * FROM  stock_detail_table    WHERE   document_number = :document_number")
    List<HeaderWithDetails> getAlldetials(long document_number);




//    @Query("UPDATE stock_count_header_table  SET  qty = :qty  WHERE  document_number = :document_number AND barcode  = :barcode")
//    void  updateItem(String barcode  , float qty, int  document_number);



    @Delete
    void  deleteSheet(StockCount_header stockCount_header);

    @Query("SELECT * FROM  STOCK_COUNT_HEADER_TABLE")
    List<StockCount_header> getAllSheets();

    @Query("SELECT * FROM  STOCK_COUNT_HEADER_TABLE   WHERE  document_number  = :documentNo")
    List<StockCount_header> getArrangedItems(int documentNo);

    @Delete
    void deleteAllItems(List<StockCount_header> stockCount_headers);


}
