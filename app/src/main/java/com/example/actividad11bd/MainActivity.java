package com.example.actividad11bd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText etCliente;
    EditText etNombre;
    EditText etDireccion;
    EditText etTelefono;
    EditText etCorreo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etCliente = (EditText) findViewById(R.id.idCliente);
        etNombre = (EditText) findViewById(R.id.idNombre);
        etDireccion = (EditText) findViewById(R.id.idDireccion);
        etTelefono = (EditText) findViewById(R.id.idNumero);
        etCorreo = (EditText) findViewById(R.id.idCorreo);


    }

    public void Agregar(View view){
        BDCliente admin = new BDCliente(this, "administracion", null, 1);
        SQLiteDatabase DataBase = admin.getWritableDatabase();

        String idCliente = etCliente.getText().toString();
        String nombre = etNombre.getText().toString();
        String direccion = etDireccion.getText().toString();
        String telefono = etTelefono.getText().toString();
        String correo = etCorreo.getText().toString();

        if(!idCliente.isEmpty() && !nombre.isEmpty() && !direccion.isEmpty() && !telefono.isEmpty() && !correo.isEmpty()){
            ContentValues values = new ContentValues();
            values.put("id", idCliente);
            values.put("nombre", nombre);
            values.put("direccion", direccion);
            values.put("telefono", telefono);
            values.put("correo", correo);

            DataBase.insert("clientes", null, values);

            DataBase.close();
            etCliente.setText("");
            etNombre.setText("");
            etDireccion.setText("");
            etTelefono.setText("");
            etCorreo.setText("");

            Toast.makeText(this,"Registro satisfactorio!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Debes llenar los campos vacios!", Toast.LENGTH_SHORT).show();
        }
    }

    public void BuscarId(View view){
        BDCliente admin = new BDCliente(this, "administracion", null, 1);
        SQLiteDatabase DataBase = admin.getWritableDatabase();

        String idCliente = etCliente.getText().toString();
        if(!idCliente.isEmpty()){
            Cursor fila = DataBase.rawQuery("select nombre, direccion, telefono, correo from clientes where codigo = " + idCliente, null);

            if(fila.moveToFirst()){
                etNombre.setText(fila.getString(0));
                etDireccion.setText(fila.getString(1));
                etTelefono.setText(fila.getString(2));
                etCorreo.setText(fila.getString(3));
                DataBase.close();
            } else{
                Toast.makeText(this, "No existe el id", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Debes introducir un id", Toast.LENGTH_SHORT).show();
        }
    }

    public void BuscarNombre(View view){
        BDCliente admin = new BDCliente(this, "administracion", null, 1);
        SQLiteDatabase DataBase = admin.getWritableDatabase();

        String nombre = etNombre.getText().toString();
        if(!nombre.isEmpty()){
            Cursor fila = DataBase.rawQuery("select idCliente, direccion, telefono, correo from clientes where nombre = " + nombre, null);

            if(fila.moveToFirst()){
                etCliente.setText(fila.getString(0));
                etDireccion.setText(fila.getString(1));
                etTelefono.setText(fila.getString(2));
                etCorreo.setText(fila.getString(3));
                DataBase.close();
            }else{
                Toast.makeText(this, "No existe el nombre", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Debes introducir un nombre", Toast.LENGTH_SHORT).show();
        }
    }

    public void Eliminar(View view){
        BDCliente admin = new BDCliente(this, "administracion", null, 1);
        SQLiteDatabase DataBase = admin.getWritableDatabase();

        String id = etCliente.getText().toString();
        if(!id.isEmpty()){
            int cantidad = DataBase.delete("clientes", "idCliente=" + id, null);
            DataBase.close();

            etCliente.setText("");
            etNombre.setText("");
            etDireccion.setText("");
            etTelefono.setText("");
            etCorreo.setText("");

            if(cantidad == 1){
               Toast.makeText(this, "Eliminado exitosamente!", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Id no existe", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this, "Debes introducir un id", Toast.LENGTH_SHORT).show();
        }
    }
}