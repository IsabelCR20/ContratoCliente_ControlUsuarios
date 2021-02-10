package com.isa.contratocliente;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Vector;

public class Registro extends AppCompatActivity {
    private EditText txtNombre;
    private EditText txtEmail;
    private EditText txtTelefono;
    private EditText txtPass;
    private Spinner spnPais;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        txtNombre = findViewById(R.id.txtNombreU);
        txtEmail = findViewById(R.id.txtEmailU);
        txtTelefono = findViewById(R.id.txtTelefonoU);
        txtPass = findViewById(R.id.txtPassU);
        spnPais = findViewById(R.id.spnPaisU);

        Button btnGuardar = findViewById(R.id.btnGuardar);
        Button btnCancelar = findViewById(R.id.btnCancelar);


        if(getIntent().getExtras() != null){
            if(getIntent().getExtras().containsKey("editar")){
                TextView lblTitulo = findViewById(R.id.lblTituloRegistro);
                lblTitulo.setText("Editar");
                ArrayList<String> datos = (ArrayList<String>) getIntent().getSerializableExtra("editar");
                Log.d("cosa", datos.get(0) +"  " + datos.get(1)+"  "+datos.get(2));
                Log.d("cosa", datos.get(3) );
                Log.d("cosa", datos.get(4) );
                txtNombre.setText(datos.get(0));
                txtTelefono.setText(datos.get(1));
                txtEmail.setText(datos.get(2));
                id = datos.get(4);
                btnGuardar.setOnClickListener(btnGuardarEdicion_Click);
            }
        } else {
            btnGuardar.setOnClickListener(btnGuardar_Click);
        }

        //

        btnCancelar.setOnClickListener(btnCancelar_Click);
    }

    View.OnClickListener btnGuardar_Click = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            ContentValues valores = new ContentValues();
            valores.put(ContratoUsuarios.Usuarios.NOMBRE, txtNombre.getText().toString());
            valores.put(ContratoUsuarios.Usuarios.CONTRASENIA, txtPass.getText().toString());
            valores.put(ContratoUsuarios.Usuarios.TELEFONO, txtTelefono.getText().toString());
            valores.put(ContratoUsuarios.Usuarios.EMAIL, txtEmail.getText().toString());
            valores.put(ContratoUsuarios.Usuarios.PAIS, spnPais.getSelectedItem().toString());
            Uri inserto = ContratoUsuarios.Usuarios.CONTENT_URI;
            Uri retorno = getContentResolver().insert(inserto, valores);
            //Log.d("cosa", );
            Registro.this.finish();
        }

    };

    View.OnClickListener btnGuardarEdicion_Click = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            ContentValues valores = new ContentValues();
            valores.put(ContratoUsuarios.Usuarios._ID, id);
            valores.put(ContratoUsuarios.Usuarios.NOMBRE, txtNombre.getText().toString());
            valores.put(ContratoUsuarios.Usuarios.CONTRASENIA, txtPass.getText().toString());
            valores.put(ContratoUsuarios.Usuarios.TELEFONO, txtTelefono.getText().toString());
            valores.put(ContratoUsuarios.Usuarios.EMAIL, txtEmail.getText().toString());
            valores.put(ContratoUsuarios.Usuarios.PAIS, spnPais.getSelectedItem().toString());
            Uri edicion = Uri.withAppendedPath(ContratoUsuarios.Usuarios.CONTENT_URI, id);
            Log.d("cosa", edicion.toString());
            getContentResolver().update(edicion, valores, null,null);

            Registro.this.finish();
        }
    };

    View.OnClickListener btnCancelar_Click = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };
}