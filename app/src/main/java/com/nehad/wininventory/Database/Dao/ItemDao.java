package com.nehad.wininventory.Database.Dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.nehad.wininventory.Database.Model.Item;

import java.util.List;

@Dao
public interface ItemDao {

     @Insert (onConflict = OnConflictStrategy.REPLACE)
    void  insertItem(Item item);

//    @Update
//    void  updateItem(Item item);


    @Query("UPDATE item_table  SET  amount = :amount  WHERE  barcode  = :barcode")
    void  updateItem(String barcode  , float amount);

//    @Update(onConflict = OnConflictStrategy.REPLACE)


//    @Query("UPDATE item_table  SET  amount = :amount ")
//    void  updateItem( float amount);

    @Delete
    void  deleteItem(Item item);

    @Query("SELECT * FROM  item_table")
    List<Item> getAllItems();

    @Query("SELECT * FROM  item_table   WHERE  barcode  = :barcode")
    List<Item> getuniqueItems(String barcode);

    @Delete
    void deleteAllItems(List<Item> itemList);


}
