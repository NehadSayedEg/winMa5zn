package com.nehad.wininventory.Database.Model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class HeaderWithDetails {

    @Embedded
    public StockCount_header stockCount_header;
    @Relation(
            parentColumn = "document_number",
            entityColumn = "barcode"
    )
    public List<StockDetail> stockDetailList;

    public HeaderWithDetails(StockCount_header stockCount_header, List<StockDetail> stockDetailList) {
        this.stockCount_header = stockCount_header;
        this.stockDetailList = stockDetailList;
    }
}
