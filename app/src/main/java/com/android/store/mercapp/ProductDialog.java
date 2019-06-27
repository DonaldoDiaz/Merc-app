package com.android.store.mercapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class ProductDialog extends AppCompatDialogFragment {
    private EditText NombreProducto,PrecioProducto;
    private DialogListenerP listenerP;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog_productos, null);

        builder.setView(view).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).setPositiveButton("Añadir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String nombreP = NombreProducto.getText().toString();
                int PrecioP = Integer.parseInt(PrecioProducto.getText().toString()) ;

                listenerP.RegisterProducts(nombreP,PrecioP);

            }
        });

        NombreProducto.findViewById(R.id.NombreProducto);
        PrecioProducto.findViewById(R.id.inputPrice);


        return builder.create();

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listenerP = (DialogListenerP) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement ExampleDialogListener");
        }

    }

    public interface DialogListenerP{
        void RegisterProducts(String nombreProducto, int PrecioProducto);
    }


}
