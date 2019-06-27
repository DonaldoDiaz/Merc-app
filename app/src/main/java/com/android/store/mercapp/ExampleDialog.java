package com.android.store.mercapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.android.store.mercapp.Entidades.Storage;
import com.android.store.mercapp.Fragments.FragmentStorage;
import com.android.store.mercapp.Interfaces.CommunicationInterface;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;


public class ExampleDialog  extends AppCompatDialogFragment  {
    private EditText inputNombreS,inputDireccion;
    private Boolean estado = false;
    private Switch swestado;
    private ImageButton btnSubirImagen;
    private String estados;
    private DialogListener listener;
    private StorageReference mStorage;
    private static final int GALLERY_INTENT =1;
    String StoreImage;
    Bitmap rbitmap;
    private Bitmap image;
    private ImageView photostore;
    String linkfoto;




    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


            mStorage = FirebaseStorage.getInstance().getReference();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);
        swestado = view.findViewById(R.id.switch2);
        btnSubirImagen = view.findViewById(R.id.BtnUploadImage);
        photostore = view.findViewById(R.id.imgFotoStorage);
        btnSubirImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,GALLERY_INTENT);
            }
        });
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

                        listener.RegisterStore(nombres,direccions,estados,id,linkfoto);



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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();

            final StorageReference filePath = mStorage.child("Fotos_Tiendas").child(uri.getLastPathSegment());
            filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    filePath.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            linkfoto= task.getResult().toString();
                            Toast.makeText(getActivity(), "link de la  foto " +linkfoto, Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            });
           /* try {
                //Getting the Bitmap from Gallery
              *//*  Bitmap bitmap = MediaStore.Images.Media.getBitmap(getConte, filePath);
                rbitmap = getResizedBitmap(bitmap,250);//Setting the Bitmap to ImageView
                userImage = getStringImage(rbitmap);
                imageViewUserImage.setImageBitmap(rbitmap);*//*
            } catch (IOException e) {
                e.printStackTrace();
            }*/
        }
    }

    public interface DialogListener {
        void RegisterStore(String Nombre, String Direccion, String Estado, String Id, String Url);
    }
}
