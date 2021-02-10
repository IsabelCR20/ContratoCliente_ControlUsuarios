package com.isa.contratocliente;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Vector;

public class UsuariosAdapter extends RecyclerView.Adapter<UsuariosAdapter.ViewHolder> {
    private Context contexto;
    private LayoutInflater inflador;
    public Vector<Vector<String>> info;

    private onLongItemClickListener MIonLongClickItemListener;

    public void setOnLongItemClickListener(onLongItemClickListener onLongItemClickListener) {
        MIonLongClickItemListener = onLongItemClickListener;
    }
    public interface onLongItemClickListener {
        void ItemLongClicked(   View v, int position);
    }

    public UsuariosAdapter(Context contexto, Vector<Vector<String>> info){
        this.contexto = contexto;
        this.info = info;
        inflador = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = inflador.inflate(R.layout.usuario_ad, null);
        return new UsuariosAdapter.ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.txtNombre.setText(info.get(position).get(0));
        holder.txtTelefono.setText(info.get(position).get(1));
        holder.txtEmail.setText(info.get(position).get(2));
        holder.txtPais.setText(info.get(position).get(3));
        // Evento de click largo
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (MIonLongClickItemListener != null) {
                    Log.d("cosa", "no null");
                    MIonLongClickItemListener.ItemLongClicked(view, position);
                }
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return info.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView txtNombre;
        public TextView txtTelefono;
        public TextView txtEmail;
        public TextView txtPais;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNombre = itemView.findViewById(R.id.txtNombreU);
            txtEmail = itemView.findViewById(R.id.txtEmail);
            txtTelefono = itemView.findViewById(R.id.txtTelefono);
            txtPais = itemView.findViewById(R.id.txtPais);
        }
    }
}
