package com.android.store.mercapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.store.mercapp.Entidades.Storage;
import com.android.store.mercapp.R;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class StorageAdapter extends RecyclerView.Adapter<StorageAdapter.ViewHolderDatos> implements View.OnClickListener{
    public ArrayList<Storage> ListStorage;
    private View.OnClickListener listener;

    public StorageAdapter(ArrayList<Storage> listStorage) {
        ListStorage = listStorage;
    }


    @NonNull
    @Override
    public StorageAdapter.ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_store,parent,false);
        itemview.setOnClickListener(this);
        return new ViewHolderDatos(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull StorageAdapter.ViewHolderDatos holder, int position) {
        holder.NombreS.setText(ListStorage.get(position).getNombre());
        holder.DireccionS.setText(ListStorage.get(position).getDireccion());
        holder.EstadoS.setText(ListStorage.get(position).getEstado());
        if (ListStorage.get(position).getEstado().equals("Cerrado")){
            holder.circle.setImageResource(R.drawable.ic_circlered);
        }
        if (ListStorage.get(position).getUrlimage()!=null){
            Picasso.get()
                    .load(ListStorage.get(position).getUrlimage().replace("http://", "https://"))
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_foreground)
                    .into(holder.imagetienda);

        }

    }

    @Override
    public int getItemCount() {
        return ListStorage.size();
    }

    public void setOnClickListener(View.OnClickListener listener){

        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if (listener !=null){
            listener.onClick(view);
        }
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {
        TextView NombreS,DireccionS,EstadoS;
        ImageView circle,imagetienda;

        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            NombreS = itemView.findViewById(R.id.txtStorageNombre);
            DireccionS = itemView.findViewById(R.id.txtStorageDireccion);
            EstadoS = itemView.findViewById(R.id.txtStorageEstado);
            circle = itemView.findViewById(R.id.imgCircle);
            imagetienda = itemView.findViewById(R.id.imgFotoStorage);

        }
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
