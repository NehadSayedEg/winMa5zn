package com.nehad.wininventory.UI.FilesActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nehad.wininventory.Database.Model.StockCount_header;
import com.nehad.wininventory.R;
import com.nehad.wininventory.UI.ScanActivity.ScanActivity;

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

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Log.v( ": you clicked", position +"postion");


                Intent intent = new Intent(v.getContext(), ScanActivity.class);
                v.getContext().startActivity(intent);
                intent.putExtra("lesson_details", stockCountHeaderList.get(position));
//                v.getContext().startActivity(intent, options.toBundle());
                v.getContext().startActivity(intent);

            }
        });

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
             String docId =String.valueOf(sheets.getDocumentNo());
             sheetId.setText(docId);
             Log.v("sheet No" , sheets.getDocumentNo() +"sheetno");
         }
    }
}
