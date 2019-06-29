package com.android.store.mercapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.store.mercapp.Entidades.Productos;
import com.android.store.mercapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductosAdapter extends RecyclerView.Adapter<ProductosAdapter.ViewHolderDatosProductos> implements View.OnClickListener {

    public ArrayList<Productos> ListProductos;
    private View.OnClickListener listenerp;


    public ProductosAdapter(ArrayList<Productos> listProductos) {
        ListProductos = listProductos;
    }



    @NonNull
    @Override
    public ProductosAdapter.ViewHolderDatosProductos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_producto,parent,false);
        itemview.setOnClickListener(this);
        return  new ViewHolderDatosProductos(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductosAdapter.ViewHolderDatosProductos holder, int position) {
        holder.txtNombreP.setText(ListProductos.get(position).getNombre());
        holder.txtPrecioP.setText(String.valueOf(ListProductos.get(position).getPrecio()));
        if (ListProductos.get(position).getIdImageProducto()!=null){

            Picasso.get()
                    .load(ListProductos.get(position).getIdImageProducto().replace("http://", "https://"))
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_foreground)
                    .into(holder.imageProducto);

        }
    }

    @Override
    public int getItemCount() {
        return ListProductos.size();
    }

    public void setOnClickListenerP(View.OnClickListener listener){
        this.listenerp = listener;
    }

    @Override
    public void onClick(View view) {
        if (listenerp !=null){
            listenerp.onClick(view);
        }
    }


    public class ViewHolderDatosProductos extends RecyclerView.ViewHolder {
        TextView txtNombreP,txtPrecioP;
        ImageView imageProducto;

        public ViewHolderDatosProductos(@NonNull View itemView) {
            super(itemView);
            txtNombreP = (TextView) itemView.findViewById(R.id.textNombreProductos);
            txtPrecioP = (TextView) itemView.findViewById(R.id.textPrecioProductos);
            imageProducto = itemView.findViewById(R.id.imageViewProducto);
        }
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
