package com.nehad.wininventory.UI.ScanActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import java.util.concurrent.TimeUnit;

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
        View  v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.scan_item, parent,false);
         final  ScanViewHolder  scanViewHolder  = new ScanViewHolder(v);
        return scanViewHolder ;

    }

    @Override
    public void onBindViewHolder(@NonNull ScanAdapter.ScanViewHolder holder, int position) {
        database = appDatabase.getInstance(activity);

        holder.setSheets(stockDetailList.get(holder.getAdapterPosition()));
        holder.sheetNameTV.setText(stockDetailList.get(holder.getAdapterPosition()).getBarcode());
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
//                linearLayout.setPadding(10 ,10 , 10 , 10);
                final EditText valueET = new EditText(v.getContext());

                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.leftMargin= 10;
                lp.rightMargin= 10;

                valueET.setLayoutParams(lp);

                valueET.setGravity(android.view.Gravity.TOP|android.view.Gravity.LEFT);
                valueET.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES|InputType.TYPE_TEXT_FLAG_MULTI_LINE);
                valueET.setLines(1);
                valueET.setMaxLines(1);

//                valueET.setText(lastDateValue);
                valueET.setHint(" Enter the value to want to add ");
                valueET.setInputType(InputType.TYPE_CLASS_NUMBER);


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
                                int seconds = (int) TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
                                  int updateDate = seconds;
                                String qty = String.valueOf(newValue);
                                database.stockHeaderDao().updateItem( barcode ,newValue  ,docNo ,updateDate);
                                holder.sheetDate.setText(qty);
                                stockDetailList.clear();
                                stockDetailList.addAll(database.stockHeaderDao().getStockDetialsByDate(docNo));
                                notifyDataSetChanged();
                                holder.sheetDate.setText(qty);
                                stockDetailList.clear();
                                stockDetailList.addAll(database.stockHeaderDao().getStockDetialsByDate(docNo));
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
                valueET.setInputType(InputType.TYPE_CLASS_NUMBER);


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
                        int seconds = (int) TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
                        int updateDate = seconds;
                        String qty = String.valueOf(newValue);
                        database.stockHeaderDao().updateItem( barcode ,newValue  ,docNo ,updateDate);
                          holder.sheetDate.setText(qty);
                          stockDetailList.clear();
                           stockDetailList.addAll(database.stockHeaderDao().getStockDetialsByDate(docNo));
                           notifyDataSetChanged();
                         holder.sheetDate.setText(qty);
                        stockDetailList.clear();
                        stockDetailList.addAll(database.stockHeaderDao().getStockDetialsByDate(docNo));
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


        holder.ivDeleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int postion = holder.getAdapterPosition();
                StockDetail stockDetail = stockDetailList.get(position);
                ViewGroup parnet = (ViewGroup)v.getParent();
//                parnet.removeView(v);


                final  TextView barcodeTv = new TextView(v.getContext());
                barcodeTv.setText(stockDetailList.get(position).getBarcode());
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(v.getContext());
                alertDialog.setTitle(" Do you want  to delete this item ").setMessage("").
                        setIcon(R.drawable.ic_baseline_add_alert_24)
                        .setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int postion = holder.getAdapterPosition();

                                StockDetail stockDetail = stockDetailList.get(position);
                                String barcode = stockDetail.getBarcode();
                                long docNo = stockDetail.getDocumentNumber();
                                database.stockHeaderDao().deleteItem( barcode   ,docNo);
                                stockDetailList.clear();
                                stockDetailList.addAll(database.stockHeaderDao().getAllItems(docNo));
                                notifyDataSetChanged();
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
            ivDeleteItem = itemView.findViewById(R.id.ivDeleteScan);

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
