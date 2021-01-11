package com.nehad.wininventory.Database.Model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class HeaderWithDetails {

    @Embedded
    public StockCount_header stockCount_header;
    @Relation(
            parentColumn = "document_number",
            entityColumn = "document_number" , entity = StockDetail.class
    )
    public List<StockDetail> stockDetail;

    public HeaderWithDetails(StockCount_header stockCount_header, List<StockDetail> stockDetail) {
        this.stockCount_header = stockCount_header;
        this.stockDetail = stockDetail;
    }

    public StockCount_header getStockCount_header() {
        return stockCount_header;
    }

    public void setStockCount_header(StockCount_header stockCount_header) {
        this.stockCount_header = stockCount_header;
    }

    public List<StockDetail> getStockDetail() {
        return stockDetail;
    }

    public void setStockDetail(List<StockDetail> stockDetail) {
        this.stockDetail = stockDetail;
    }
}
