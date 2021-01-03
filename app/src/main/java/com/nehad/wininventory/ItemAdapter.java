package com.nehad.wininventory;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nehad.wininventory.Database.Model.Item;
import com.nehad.wininventory.Database.appDatabase;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItenViewHolder> {
    private List<Item> itemList ;
    private Activity activity ;
    Context context ;
    private appDatabase database;

    public ItemAdapter(List<Item> itemList, Activity activity) {
        this.itemList = itemList;
        this.activity = activity;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(activity);
        View view = inflater.inflate(R.layout.item, parent,  false);
        return new ItemAdapter.ItenViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItenViewHolder holder, int position) {

        Item item = itemList.get(position);
        database = appDatabase.getInstance(activity);


        holder.item_name_tv.setText(item.getBarcode());


        String qty = String.valueOf(item.getAmount());
        holder.qty_tv.setText(qty);


        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int postion = holder.getAdapterPosition();
                Item item = itemList.get(position);
                 String barcode =    item.getBarcode();
               float updateAmount =  itemList.get(position).getAmount() + 1 ;
                database.itemDao().updateItem( barcode ,updateAmount  );
                String qty = String.valueOf(updateAmount);
                holder.qty_tv.setText(qty);
                itemList.clear();
                itemList.addAll(database.itemDao().getAllItems());
                notifyDataSetChanged();
            }
        });

        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int postion = holder.getAdapterPosition();
                Item item = itemList.get(position);
                String barcode =    item.getBarcode();

                float updateAmount =  itemList.get(position).getAmount()  -1 ;
                database.itemDao().updateItem(barcode ,updateAmount);
                String qty = String.valueOf(updateAmount);
                holder.qty_tv.setText(qty);
                itemList.clear();
                itemList.addAll(database.itemDao().getAllItems());
                notifyDataSetChanged();
            }
        });


        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Item item = itemList.get(holder.getAdapterPosition());
                database.itemDao().deleteItem(item);
                int postion = holder.getAdapterPosition();
                itemList.remove(position);
                notifyItemRemoved(position);

                notifyItemRangeChanged(position , itemList.size());



            }
        });


    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void setItemList(List<Item>itemsList){
        this.itemList = itemsList;
        notifyDataSetChanged();
        Log.e("item Sze list" , itemsList.size() +"size");
    }


    public class ItenViewHolder extends RecyclerView.ViewHolder {
        TextView item_name_tv , qty_tv;
        ImageView  delete  , minus   ,  plus ;
        public ItenViewHolder(@NonNull View itemView) {

            super(itemView);


            item_name_tv = itemView.findViewById(R.id.tvName);
            qty_tv =itemView.findViewById(R.id.tvAmount);
            delete = itemView.findViewById(R.id.ivDelete);
            minus = itemView.findViewById(R.id.ivMinus);
            plus = itemView.findViewById(R.id.ivPlus);







        }
    }
}
