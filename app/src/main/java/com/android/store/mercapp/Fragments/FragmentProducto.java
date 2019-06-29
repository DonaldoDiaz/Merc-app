package com.android.store.mercapp.Fragments;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.store.mercapp.Adapters.ProductosAdapter;
import com.android.store.mercapp.Entidades.Productos;
import com.android.store.mercapp.Interfaces.CommunicationInterface;
import com.android.store.mercapp.MainActivity;
import com.android.store.mercapp.ProductDialog;
import com.android.store.mercapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


import java.util.ArrayList;

import javax.annotation.Nullable;

import static com.facebook.FacebookSdk.getApplicationContext;


public class FragmentProducto extends  Fragment  {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FloatingActionButton fabAddProducts;
    FirebaseStorage storage;
    Productos productos;
    String idTienda;


    StorageReference storageRef;

    private OnFragmentInteractionListener mListener;
    private View vista;
    public RecyclerView recyclerP;
    public ArrayList<Productos> productosArrayList;
    MainActivity activity;
    Activity activityp;
    View view;
    CommunicationInterface anInterface;
    public FragmentProducto() {
    }

    public static FragmentProducto newInstance(String param1, String param2){
        FragmentProducto fragmentProducto = new FragmentProducto();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragmentProducto.setArguments(args);
        return fragmentProducto;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments()!=null){
            idTienda = getArguments().getString("idTienda");
            System.out.println("=====================");
            System.out.println(idTienda);
            System.out.println("=====================");
            ConsultarProductosEnTiempoReal(idTienda);

            //llamo a la funcion setIdtiendaAproducto() y le paso el parametro idTienda
            activity = new MainActivity();
            activity.setIdtiendaAproducto(idTienda);

        }


    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista = inflater.inflate(R.layout.fragment_detalle_store, container, false);

        return vista;
    }




    private void Renderlist() {
        recyclerP = (RecyclerView) vista.findViewById(R.id.RecyclerProductos);
        recyclerP.setHasFixedSize(true);
        LinearLayoutManager layout = new LinearLayoutManager(getContext());
        layout.setOrientation(LinearLayoutManager.HORIZONTAL);
        ProductosAdapter Adapter = new ProductosAdapter(productosArrayList);
        recyclerP.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerP.setAdapter(Adapter);
        Adapter.setOnClickListenerP(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                anInterface.sendData(productosArrayList.get(recyclerP.getChildAdapterPosition(view)));

            }
        });
    }



    private void ConsultarProductosEnTiempoReal(String id) {


        FirebaseFirestore dtbs = FirebaseFirestore.getInstance();
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        //System.out.println("id==>" +id);
        CollectionReference subref = dtbs
                .collection("Tiendas")
                .document(id).collection("Productos");

        subref.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirebaseFirestoreException e) {
                productosArrayList = new ArrayList<>();
                productosArrayList.clear();
                for (QueryDocumentSnapshot documentSnapshot : snapshots){
                    Productos productos = documentSnapshot.toObject(Productos.class);
                    productosArrayList.add(productos);

                }

                Renderlist();

            }
        });

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof Activity){
            this.activityp = (Activity) context;
            anInterface = (CommunicationInterface) this.activityp;
        }
    }



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
