package com.android.store.mercapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.android.store.mercapp.Fragments.FragmentProducto;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import static android.app.Activity.RESULT_OK;

public class ProductDialog extends AppCompatDialogFragment   {
    private EditText NombreProducto,PrecioProducto;
    private DialogListenerP listenerP;
    private ImageButton btnSubirImagenP;
    private static final int GALLERY_INTENT_PRODUCTO =1;
    private StorageReference mStorageProducto;
    String linkfotoP,TiendaId;
    private ImageView photoproduct;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {


        mStorageProducto = FirebaseStorage.getInstance().getReference();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog_productos, null);
        btnSubirImagenP = view.findViewById(R.id.BtnUploadImageProducto);
         photoproduct = view.findViewById(R.id.imagePreviewProductos);
        NombreProducto = view.findViewById(R.id.NombreProducto);
        PrecioProducto = view.findViewById(R.id.inputPrice);

        btnSubirImagenP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,GALLERY_INTENT_PRODUCTO);
            }
        });

        builder.setView(view).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).setPositiveButton("Añadir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                String nombreP = NombreProducto.getText().toString();
                int PrecioP = Integer.parseInt(PrecioProducto.getText().toString()) ;
                final FirebaseFirestore db = FirebaseFirestore.getInstance();
                String id = db.collection("Tiendas").document().collection("Prodcutos").getId();
                listenerP.RegisterProducts(nombreP,PrecioP,id,linkfotoP);

            }
        });




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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_INTENT_PRODUCTO && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri image = data.getData();


            final StorageReference filePathp = mStorageProducto.child("Fotos_Productos").child(image.getLastPathSegment());
            filePathp.putFile(image).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    filePathp.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            linkfotoP= task.getResult().toString();
                            Toast.makeText(getActivity(), "link de la  foto " +linkfotoP, Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            });

            Picasso.get()
                    .load(image)
                    .placeholder(R.drawable.tienda)
                    .error(R.drawable.ic_launcher_foreground)
                    .into(photoproduct);

        }
    }




    public interface DialogListenerP{
        void RegisterProducts(String nombreProducto, int PrecioProducto, String idproducto, String idImage);
    }


}
