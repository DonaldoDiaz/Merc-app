package com.android.store.mercapp.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.store.mercapp.Entidades.Productos;
import com.android.store.mercapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentProductDetail.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentProductDetail#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentProductDetail extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView NombreDP,PrecioDP,CantidadDP,DescripcionDP;
    private ImageView PhotoDP;
    private ImageButton btnPlus, btnMin;
    private static int Count;
    private OnFragmentInteractionListener mListener;
    View view;


    public FragmentProductDetail() {
        // Required empty public constructor
    }


    public static FragmentProductDetail newInstance(String param1, String param2) {
        FragmentProductDetail fragment = new FragmentProductDetail();
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



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_product_detail, container, false);
        NombreDP = vista.findViewById(R.id.DetailNombreP);
        PrecioDP = vista.findViewById(R.id.PrecioDetail);
        CantidadDP = vista.findViewById(R.id.CountDetail);
        DescripcionDP = vista.findViewById(R.id.DescripcionDetail);
        PhotoDP = vista.findViewById(R.id.ImageDetail);
        btnPlus = vista.findViewById(R.id.BtnPlusDetail);
        btnMin = vista.findViewById(R.id.BtnMinusDetail);


        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Count--;
                if (Count>=0){
                    CantidadDP.setText(String.valueOf(Count));
                }else {
                    Count=0;
                }

            }
        });
        btnMin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Count++;
                CantidadDP.setText(String.valueOf(Count));
            }
        });
        Bundle objetoProductos = getArguments();
        Productos productos;
        if (objetoProductos != null){
            productos = (Productos) objetoProductos.getSerializable("objeto");
            NombreDP.setText(productos.getNombre());
            DescripcionDP.setText("Producto de buena calidad");
            PrecioDP.setText(String.valueOf(productos.getPrecio()));

            //PhotoDP.setImageURI(Uri.parse(productos.getIdImageProducto()));
            Picasso.get().load(productos.getIdImageProducto()
                    .replace("http://", "https://"))
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_foreground)
                    .into(PhotoDP);
        }
        return vista;
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
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
