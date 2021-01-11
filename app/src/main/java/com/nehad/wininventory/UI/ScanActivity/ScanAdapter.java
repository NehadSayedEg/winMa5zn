package com.nehad.wininventory.UI.ScanActivity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.nehad.wininventory.Database.Model.StockDetail;
import com.nehad.wininventory.Database.appDatabase;
import com.nehad.wininventory.R;

import java.text.DecimalFormat;
import java.util.List;

public class ScanAdapter   extends RecyclerView.Adapter<ScanAdapter.ScanViewHolder>{
    List<StockDetail> stockDetailList ;
    private int selectedItem = 0;
    private appDatabase database;
    private Activity activity ;
    Context context ;



    public ScanAdapter(List<StockDetail> stockDetailList ,Activity activity ,Context context ) {
        this.stockDetailList = stockDetailList;
        this.activity = activity;
        this.context = context;
        notifyDataSetChanged();

    }



    @NonNull
    @Override
    public ScanAdapter.ScanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ScanAdapter.ScanViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.scan_item, parent,false));


    }

    @Override
    public void onBindViewHolder(@NonNull ScanAdapter.ScanViewHolder holder, int position) {
        database = appDatabase.getInstance(activity);

        holder.setSheets(stockDetailList.get(position));
        holder.sheetNameTV.setText(stockDetailList.get(position).getBarcode());
        final String sheetData = holder.sheetNameTV.getText().toString();

        holder.ivInc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int postion = holder.getAdapterPosition();
                StockDetail stockDetail = stockDetailList.get(position);
                ViewGroup parnet = (ViewGroup)v.getParent();
//                parnet.removeView(v);

                LinearLayout linearLayout = new LinearLayout(v.getContext());
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                final EditText valueET = new EditText(v.getContext());
                valueET.setHint(" Enter the value to want to subtract ");
                valueET.setInputType(DecimalFormat.INTEGER_FIELD);

                final  TextView barcodeTv = new TextView(v.getContext());
                barcodeTv.setText(stockDetailList.get(position).getBarcode());
                linearLayout.addView(barcodeTv);
                linearLayout.addView(valueET);


                AlertDialog.Builder alertDialog = new AlertDialog.Builder(v.getContext());
                alertDialog.setTitle(" please add the  subtract value ").setMessage("").
                        setIcon(R.drawable.ic_baseline_add_alert_24).setView(linearLayout)
                        .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int postion = holder.getAdapterPosition();

                                StockDetail stockDetail = stockDetailList.get(position);
                                String barcode = stockDetail.getBarcode();
                                long docNo = stockDetail.getDocumentNumber();
                                float updateQty =  Float.valueOf(valueET.getText().toString());
                                float newValue =  stockDetail.getQty() + updateQty ;
                                String qty = String.valueOf(newValue);
                                database.stockHeaderDao().updateItem( barcode ,newValue  ,docNo);
                                holder.sheetDate.setText(qty);
                                stockDetailList.clear();
                                stockDetailList.addAll(database.stockHeaderDao().getAllItems(docNo));
                                notifyDataSetChanged();
                                holder.sheetDate.setText(qty);
                                stockDetailList.clear();
                                stockDetailList.addAll(database.stockHeaderDao().getAllItems(docNo));
                                notifyDataSetChanged();


                            }
                        }).setNegativeButton(" cancel ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.setCancelable(true);
                    }
                }).show();


            }
        });


        holder.ivDec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int postion = holder.getAdapterPosition();
                StockDetail stockDetail = stockDetailList.get(position);
                ViewGroup parnet = (ViewGroup)v.getParent();
//                parnet.removeView(v);

                LinearLayout linearLayout = new LinearLayout(v.getContext());
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                final EditText valueET = new EditText(v.getContext());
                valueET.setHint(" Enter the value to want to subtract ");
                valueET.setInputType(DecimalFormat.INTEGER_FIELD);

                final  TextView barcodeTv = new TextView(v.getContext());
                barcodeTv.setText(stockDetailList.get(position).getBarcode());
                linearLayout.addView(barcodeTv);
                linearLayout.addView(valueET);


                AlertDialog.Builder alertDialog = new AlertDialog.Builder(v.getContext());
                alertDialog.setTitle(" please add the  subtract value ").setMessage("").
                      setIcon(R.drawable.ic_baseline_add_alert_24).setView(linearLayout)
                      .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int postion = holder.getAdapterPosition();

                        StockDetail stockDetail = stockDetailList.get(position);
                        String barcode = stockDetail.getBarcode();
                        long docNo = stockDetail.getDocumentNumber();
                        float updateQty =  Float.valueOf(valueET.getText().toString());
                        float newValue =  stockDetail.getQty() - updateQty ;
                        String qty = String.valueOf(newValue);
                        database.stockHeaderDao().updateItem( barcode ,newValue  ,docNo);
                          holder.sheetDate.setText(qty);
                          stockDetailList.clear();
                           stockDetailList.addAll(database.stockHeaderDao().getAllItems(docNo));
                           notifyDataSetChanged();
                         holder.sheetDate.setText(qty);
                        stockDetailList.clear();
                        stockDetailList.addAll(database.stockHeaderDao().getAllItems(docNo));
                        notifyDataSetChanged();


                    }
                }).setNegativeButton(" cancel ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.setCancelable(true);
                    }
                }).show();


            }
        });



    }


    @Override
    public int getItemCount() {
        return stockDetailList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    static  class ScanViewHolder extends RecyclerView.ViewHolder{
        TextView sheetNameTV , sheetDate , sheetId ;
        ImageView ivDec ,ivInc ,ivDeleteItem;


        ScanViewHolder(@NonNull View itemView) {
            super(itemView);
            sheetNameTV = itemView.findViewById(R.id.tvBarcode);
            sheetDate = itemView.findViewById(R.id.tvQty);
            sheetId = itemView.findViewById(R.id.documentId);
            ivDec = itemView.findViewById(R.id.ivDec);
            ivInc = itemView.findViewById(R.id.ivInc);
            ivDeleteItem = itemView.findViewById(R.id.ivDeleteItem);

        }
        void setSheets(StockDetail sheets){
            sheetNameTV.setText(sheets.getBarcode());
            String itemQty =String.valueOf(sheets.getQty());
            sheetDate.setText(itemQty);
            String docId =String.valueOf(sheets.getDocumentNumber());
            Log.v("sheet No" , sheets.getDocumentNumber() +"sheetno");
        }
    }
}
