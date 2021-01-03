package com.nehad.wininventory.Database.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.nehad.wininventory.Database.Model.Item;
import com.nehad.wininventory.Database.Model.StockDetail;

import java.util.List;
@Dao

public interface StockDetailsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void  insertItem(StockDetail stockDetail);

//    @Update
//    void  updateItem(Item item);


    @Query("UPDATE stock_detail_table  SET  qty = :qty  WHERE  document_number = :document_number AND barcode  = :barcode")
    void  updateItem(String barcode  , float qty, int  document_number);



//    @Query("UPDATE item_table  SET  amount = :amount ")
//    void  updateItem( float amount);

    @Delete
    void  deleteItem(StockDetail stockDetail);

    @Query("SELECT * FROM  Stock_detail_table")
    List<StockDetail> getAllItems();

    @Query("SELECT * FROM  stock_detail_table   ORDER By  id DESC")
    List<StockDetail> getArrangedItems();

    @Delete
    void deleteAllItems(List<StockDetail> stockDetailList);

}
