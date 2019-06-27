package com.android.store.mercapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.android.store.mercapp.Interfaces.CommunicationInterface;
import com.google.firebase.firestore.FirebaseFirestore;


public class ExampleDialog  extends AppCompatDialogFragment {
    private EditText inputNombreS,inputDireccion;
    private Boolean estado = false;
    private Switch swestado;
    private String estados;
    private DialogListener listener;



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {




        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);
        swestado = view.findViewById(R.id.switch2);
        swestado.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    estado = true;
                }
            }
        });





        builder.setView(view).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Añadir", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String nombres = inputNombreS.getText().toString();
                        String direccions = inputDireccion.getText().toString();


                        if (estado){
                            estados = "Abierto";
                        }else {
                            estados = "Cerrado";
                        }
                        final FirebaseFirestore db = FirebaseFirestore.getInstance();
                        String id = db.collection("Tiendas").document().getId();

                        listener.RegisterStore(nombres,direccions,estados,id);



                        estado=false;


                    }
                });

        inputNombreS = view.findViewById(R.id.inputNombreS);
        inputDireccion = view.findViewById(R.id.inputDireccionS);


        return builder.create();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (DialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement ExampleDialogListener");
        }

    }

    public interface DialogListener {
        void RegisterStore(String Nombre, String Direccion, String Estado, String Id);
    }
}
