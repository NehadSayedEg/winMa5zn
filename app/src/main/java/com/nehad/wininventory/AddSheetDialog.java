package com.nehad.wininventory;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class AddSheetDialog extends AppCompatDialogFragment {
   private    EditText sheetnameEt;
   private   addSheetListener addSheetListener ;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder   builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_sheet_dialog ,null);
         builder.setView(view).setTitle("Please Add Sheet Name").setNegativeButton("Cancel",
                 new DialogInterface.OnClickListener() {

             @Override
             public void onClick(DialogInterface dialog, int i) {


             }
         }).setPositiveButton("Add Sheet ", new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialog, int i) {
                 if(!sheetnameEt.equals("")){
                     String sheetName =  sheetnameEt.getText().toString();
                     addSheetListener.applyText(sheetName);

                 }
                 else {
                     Toast.makeText( getActivity() , "please Enter sheet name First" , Toast.LENGTH_LONG).show();

                 }


             }
         });
        sheetnameEt = view.findViewById(R.id.sheetName_et);

         return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        try{
            addSheetListener = (addSheetListener)context;


        } catch (ClassCastException e) {
            throw  new  ClassCastException(context.toString() + "must implement  ExampleList");
        }
        super.onAttach(context);
    }

    public interface addSheetListener{
        void applyText( String sheetName);


    }
}
