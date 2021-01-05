package com.nehad.wininventory.Database.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.room.Database;

import com.nehad.wininventory.Database.Dao.StockHeaderDao;
import com.nehad.wininventory.Database.Model.HeaderWithDetails;
import com.nehad.wininventory.Database.Model.StockDetail;
import com.nehad.wininventory.Database.appDatabase;

public class StockRepository {


    private StockHeaderDao stockHeaderDao;

    public StockRepository(Application application) {
        appDatabase database = appDatabase.getInstance(application);
        stockHeaderDao = database.stockHeaderDao();
    }

    public void insert(HeaderWithDetails headerWithDetails) {
        new insertAsync(stockHeaderDao).execute(headerWithDetails);
    }

    private static class insertAsync extends AsyncTask<HeaderWithDetails, Void, Void> {
        private StockHeaderDao stockHeaderDaoSync;

        insertAsync(StockHeaderDao stockHeaderDao) {
            stockHeaderDaoSync = stockHeaderDao;
        }

        @Override
        protected Void doInBackground(HeaderWithDetails... headerWithDetails) {


            long identifier = stockHeaderDaoSync.insertSheet(headerWithDetails[0].stockCount_header);

            for (StockDetail stockDetail : headerWithDetails[0].stockDetailList) {
                stockDetail.setDocumentNumber(identifier);
            }
            stockHeaderDaoSync.insertStockDetails(headerWithDetails[0].stockDetailList);
            return null;

        }
    }
}