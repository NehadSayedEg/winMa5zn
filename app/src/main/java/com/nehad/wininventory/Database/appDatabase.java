package com.nehad.wininventory.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.nehad.wininventory.Database.Dao.ItemDao;
import com.nehad.wininventory.Database.Dao.StockDetailsDao;
import com.nehad.wininventory.Database.Dao.StockHeaderDao;
import com.nehad.wininventory.Database.Model.Item;
import com.nehad.wininventory.Database.Model.StockCount_header;
import com.nehad.wininventory.Database.Model.StockDetail;


@Database(entities = {Item.class , StockCount_header.class , StockDetail.class}  , version = 3, exportSchema = false)
@TypeConverters({DateTypeConverter.class})

public  abstract class appDatabase extends RoomDatabase {

    //create database instance
    private  static appDatabase database ;

    private static String databaseName = "ma5zn_database";

    public synchronized  static  appDatabase getInstance(Context context){
        if( database == null){
            database = Room.databaseBuilder(context.getApplicationContext() ,appDatabase.class, databaseName)
                    .allowMainThreadQueries().fallbackToDestructiveMigration().build();

        }
        return database ;
    }
    public abstract ItemDao itemDao();
    public abstract StockDetailsDao stockDetailsDao();
    public abstract StockHeaderDao stockHeaderDao();




}