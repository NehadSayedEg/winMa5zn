package com.nehad.wininventory.Database.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.nehad.wininventory.Database.Model.StockCount_header;
import com.nehad.wininventory.Database.Model.StockDetail;

import java.util.List;

@Dao
public interface StockHeaderDao {


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void  insertSheet(StockCount_header stockCount_header);

//    @Update
//    void  updateItem(Item item);


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
