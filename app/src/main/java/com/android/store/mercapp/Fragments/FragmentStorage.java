package com.android.store.mercapp.Fragments;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.store.mercapp.Adapters.StorageAdapter;
import com.android.store.mercapp.Entidades.Productos;
import com.android.store.mercapp.Entidades.Storage;
import com.android.store.mercapp.Interfaces.CommunicationInterface;
import com.android.store.mercapp.R;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import javax.annotation.Nullable;

import static com.facebook.login.widget.ProfilePictureView.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentStorage.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentStorage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentStorage extends Fragment implements FragmentProducto.OnFragmentInteractionListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FragmentListener listener;
    FirebaseStorage storage;






    // Create a storage reference from our app
    StorageReference storageRef;
    View vista;
    private OnFragmentInteractionListener mListener;
    RecyclerView storageview;
    RecyclerView productosviewespejo;
    // actividad para obtener el contexto
    Activity activity;
    // creacion de variable de tipo CommunicationInterface
    CommunicationInterface ProductInterface;
    ArrayList<Productos>ListaProductosEspejo;
    ArrayList<Storage> ListaStorage;
    FragmentProducto detalleStore ;


    public FragmentStorage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentStorage.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentStorage newInstance(String param1, String param2) {
        FragmentStorage fragment = new FragmentStorage();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        ConsultarStoresEnTiempoReal();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista = inflater.inflate(R.layout.fragment_add_storage, container, false);
        Activity activity = getActivity();

        return vista;

    }


    public void Renderlist(){


        storageview = (RecyclerView) vista.findViewById(R.id.ContenedorStorage);
        storageview.setHasFixedSize(true);
        LinearLayoutManager layout = new LinearLayoutManager(getContext());
        layout.setOrientation(LinearLayoutManager.HORIZONTAL);
        StorageAdapter Adapter = new StorageAdapter(ListaStorage);
        storageview.setLayoutManager(new LinearLayoutManager(getContext()));
        storageview.setAdapter(Adapter);

        Adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                       String sendId=ListaStorage.get(storageview.getChildAdapterPosition(view)).getId();
                       //Toast.makeText(getContext(), "Usted selecciono la tienda con id "+ sendId, Toast.LENGTH_SHORT).show();
                       //listener.SendIdStore(sendId);
                       Fragment vistaproducto = new FragmentProducto();
                       Bundle bundle = new Bundle();
                       bundle.putString("idTienda", sendId);
                       vistaproducto.setArguments(bundle);
                       getFragmentManager().beginTransaction().replace(R.id.Contenedor,vistaproducto, "PRODUCT_FRAGMENT").commit();
            }
        });


    }



    private void  ConsultarStoresEnTiempoReal(){
        FirebaseFirestore dtbs = FirebaseFirestore.getInstance();
        dtbs.collection("Tiendas").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e);
                    return;
                }
                ListaStorage = new ArrayList<>();
                ListaStorage.clear();
                for (QueryDocumentSnapshot documentSnapshot : snapshots){
                    Storage storage = documentSnapshot.toObject(Storage.class);
                    ListaStorage.add(storage);
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (FragmentListener) context;
        }catch (ClassCastException e){
               
        }

      /*  if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

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

    public interface FragmentListener{
        void SendIdStore(String idstore);
    }
}
