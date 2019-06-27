package com.android.store.mercapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.store.mercapp.Entidades.Productos;
import com.android.store.mercapp.R;

import java.util.ArrayList;

public class ProductosAdapter extends RecyclerView.Adapter<ProductosAdapter.ViewHolderDatosProductos>  {

    public ArrayList<Productos> ListProductos;


    public ProductosAdapter(ArrayList<Productos> listProductos) {
        ListProductos = listProductos;
    }



    @NonNull
    @Override
    public ProductosAdapter.ViewHolderDatosProductos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_producto,parent,false);
        return  new ViewHolderDatosProductos(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductosAdapter.ViewHolderDatosProductos holder, int position) {
        holder.txtNombreP.setText(ListProductos.get(position).getNombre());
        holder.txtPrecioP.setText(String.valueOf(ListProductos.get(position).getPrecio()));
    }

    @Override
    public int getItemCount() {
        return ListProductos.size();
    }




    public class ViewHolderDatosProductos extends RecyclerView.ViewHolder {
        TextView txtNombreP,txtPrecioP;

        public ViewHolderDatosProductos(@NonNull View itemView) {
            super(itemView);
            txtNombreP = (TextView) itemView.findViewById(R.id.textNombreProductos);
            txtPrecioP = (TextView) itemView.findViewById(R.id.textPrecioProductos);
        }
    }
}
