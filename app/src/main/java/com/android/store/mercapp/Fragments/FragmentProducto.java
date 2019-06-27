package com.android.store.mercapp.Fragments;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.store.mercapp.Adapters.ProductosAdapter;
import com.android.store.mercapp.Entidades.Productos;
import com.android.store.mercapp.Interfaces.CommunicationInterface;
import com.android.store.mercapp.ProductDialog;
import com.android.store.mercapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentProducto.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentProducto#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentProducto extends  Fragment   {
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
    String idstore;

    StorageReference storageRef;

    private OnFragmentInteractionListener mListener;
    private View vista;
    public RecyclerView recyclerP;
    public ArrayList<Productos> productosArrayList;

    public FragmentProducto() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String idTienda = getArguments().getString("idTienda");
        System.out.println("=====================");
        System.out.println(idTienda);
        System.out.println("=====================");
        ConsultarProductosEnTiempoReal();
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
    }


  /*  public void recibirId(String IdRecibido){
        idstore=IdRecibido;
        Toast.makeText(getActivity(), " el id es " +idstore, Toast.LENGTH_SHORT).show();
    }*/

    private void ConsultarProductosEnTiempoReal() {


        FirebaseFirestore dtbs = FirebaseFirestore.getInstance();
        final FirebaseFirestore db = FirebaseFirestore.getInstance();





        CollectionReference subref = dtbs
                .collection("Tiendas")
                .document("BJmoRkUuuJl2WLy9t31f").collection("Productos");

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

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }




    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
