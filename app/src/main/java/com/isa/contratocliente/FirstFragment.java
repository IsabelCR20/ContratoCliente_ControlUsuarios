package com.isa.contratocliente;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.net.URI;
import java.util.Vector;

public class FirstFragment extends Fragment {
    RecyclerView rvUsuarios;
    Vector<Vector<String>> datos;
    UsuariosAdapter adaptador;
    int posAct = 0;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    public void onViewCreated(@NonNull final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        datos = new Vector<>();
        Cursor cursor = getActivity().getContentResolver().query(ContratoUsuarios.Usuarios.CONTENT_URI, null, null,null,null);
        while(cursor!=null && cursor.moveToNext()){
            Vector<String> tmp = new Vector<>();
            tmp.add(cursor.getString(1));
            tmp.add(cursor.getString(3));
            tmp.add(cursor.getString(4));
            tmp.add(cursor.getString(5));
            tmp.add(cursor.getInt(0)+"");
            this.datos.add(tmp);
        }
        adaptador = new UsuariosAdapter(getContext(), datos);
        adaptador.setOnLongItemClickListener(new UsuariosAdapter.onLongItemClickListener() {
            @Override
            public void ItemLongClicked(View v, int position) {
                posAct = position;
                registerForContextMenu(v);
                v.showContextMenu();
                Log.d("cosa", "SHow");
            }
        });
        //
        rvUsuarios = view.findViewById(R.id.rvInfoUsuarios);
        rvUsuarios.setLayoutManager(new LinearLayoutManager(getContext()));
        rvUsuarios.setAdapter(adaptador);
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        Log.d("cosa", "HOLA");
        MenuInflater inflater = new MenuInflater(getActivity());
        inflater.inflate(R.menu.menu_desp, menu);
        for (int i = 0; i < menu.size(); ++i) {
            MenuItem item = menu.getItem(i);
            item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    onContextItemSelected(item);
                    return true;
                }
            });
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        Vector<String> seleccionado = datos.elementAt(posAct);
        Log.d("cosa", "Seleccionaste: " + posAct + "  Pero su ID es: " + seleccionado.elementAt(4));
        switch (item.getItemId()){
            case R.id.mnuEditar:
                Intent editarReg = new Intent(getContext(), Registro.class);
                editarReg.putExtra("editar", seleccionado);
                startActivity(editarReg);
                break;
            case R.id.mnuEliminar:
                Uri borrar = Uri.withAppendedPath(ContratoUsuarios.Usuarios.CONTENT_URI, seleccionado.elementAt(4));
                getActivity().getContentResolver().delete(borrar, null, null);
                this.onResume();
                break;
        }

        return super.onContextItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        datos = new Vector<>();
        Cursor cursor = getActivity().getContentResolver().query(ContratoUsuarios.Usuarios.CONTENT_URI, null, null,null,null);
        while(cursor!=null && cursor.moveToNext()){
            Vector<String> tmp = new Vector<>();
            tmp.add(cursor.getString(1));
            tmp.add(cursor.getString(3));
            tmp.add(cursor.getString(4));
            tmp.add(cursor.getString(5));
            tmp.add(cursor.getInt(0)+"");
            this.datos.add(tmp);
        }
        adaptador.info = datos;
        rvUsuarios.setAdapter(adaptador);
        rvUsuarios.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}