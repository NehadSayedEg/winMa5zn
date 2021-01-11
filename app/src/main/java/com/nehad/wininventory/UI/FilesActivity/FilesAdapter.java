package com.nehad.wininventory.UI.FilesActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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

import java.io.Serializable;
import java.util.List;

public class FilesAdapter  extends RecyclerView.Adapter<FilesAdapter.FilesViewHolder>{
     List<StockCount_header> stockCountHeaderList ;
    private int selectedItem = 0;


    public FilesAdapter(List<StockCount_header> stockCountHeaderList) {
        this.stockCountHeaderList = stockCountHeaderList;
        notifyDataSetChanged();
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
        holder.sheetNameTV.setText(stockCountHeaderList.get(position).getFileName());
        final String sheetData = holder.sheetNameTV.getText().toString();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(v.getContext(),ScanActivity.class);
//                intent.putExtra("user", user); //where user is an instance of User object
//                startActivity(intent);
//                Intent intent = new Intent("sheetData");
//                intent.putExtra("sheet", sheetData);
//                int PreviousSelectedItem = selectedItem;
//                selectedItem = position;
//
//                // Set the static value in the MainActivity
//                // This can be accessed from all other classes
//                FilesActivity.selectedItem = position;
//
//                intent.putExtra("selected", selectedItem);
//                holder.tvIcon.setBackgroundColor(Color.parseColor("#30000000"));
//                notifyItemChanged(PreviousSelectedItem);
//                notifyDataSetChanged();


              Log.v( ": you clicked", position +"postion");


                Intent intent = new Intent(v.getContext(), ScanActivity.class);
//                intent.putExtra("lesson_details", stockCountHeaderList.get(position).toString());
                Bundle args = new Bundle();
                args.putSerializable("lesson_details", stockCountHeaderList.get(position));
                intent.putExtras(args);
                v.getContext().startActivity(intent);

                Log.v("ssss",stockCountHeaderList.get(position).toString() );
//                v.getContext().startActivity(intent, options.toBundle());
//                v.getContext().startActivity(intent, );
                notifyDataSetChanged();

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
