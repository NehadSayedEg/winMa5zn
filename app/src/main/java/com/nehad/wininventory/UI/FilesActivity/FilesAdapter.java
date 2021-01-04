package com.nehad.wininventory.UI.FilesActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nehad.wininventory.Database.Model.StockCount_header;
import com.nehad.wininventory.R;

import java.util.List;

public class FilesAdapter  extends RecyclerView.Adapter<FilesAdapter.FilesViewHolder>{
    private List<StockCount_header> stockCountHeaderList ;

    public FilesAdapter(List<StockCount_header> stockCountHeaderList) {
        this.stockCountHeaderList = stockCountHeaderList;
    }

    @NonNull
    @Override
    public FilesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FilesViewHolder(

                LayoutInflater.from(parent.getContext()).inflate(R.layout.sheetname_item, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull FilesViewHolder holder, int position) {
        holder.setSheets(stockCountHeaderList.get(position));

    }

    @Override
    public int getItemCount() {
        return stockCountHeaderList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    static  class FilesViewHolder extends RecyclerView.ViewHolder{
        TextView sheetNameTV , sheetDate , sheetId ;


         FilesViewHolder(@NonNull View itemView) {
            super(itemView);
            sheetNameTV = itemView.findViewById(R.id.sheetName);
             sheetDate = itemView.findViewById(R.id.documentDate);
             sheetId = itemView.findViewById(R.id.documentId);

         }
         void setSheets(StockCount_header sheets){
             sheetNameTV.setText(sheets.getFileName());
             sheetDate.setText(sheets.getDocumentDate());
             //sheetId.setText(sheets.getDocumentNo());
             Log.v("sheet No" , sheets.getDocumentNo() +"sheetno");
         }
    }
}
