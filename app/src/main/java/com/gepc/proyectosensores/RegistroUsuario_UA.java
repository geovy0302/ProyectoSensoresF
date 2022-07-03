package com.gepc.proyectosensores;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gepc.proyectosensores.adaptadores.ListaUsuariosAdaptor;
import com.gepc.proyectosensores.databinding.ActivityRegistroUsuarioUaBinding;
import com.gepc.proyectosensores.usuarioadmin.Usuarios;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegistroUsuario_UA extends BasedelMenuOpcUAdmin {

    ActivityRegistroUsuarioUaBinding activityRegistroUsuarioUaBinding;
    public static List<Usuarios> listaTotal = new ArrayList<>();
    SharedPreferences mPreferences;
    String sharedprofFile="com.gepc.proyectosensores";
    SharedPreferences.Editor preferencesEditor;
    String nombre, app_a_visitar, indice_tip;
    TextView Signedinusername, Contenedor_Tips;
    ClaseGlobal objGlobalAux;
    RecyclerView ListaUsers;
    ListaUsuariosAdaptor AdapterNecesario;
    Button añadir, buscar;

    static final String URL_USERS = "http://gpssandcloud.com/RAYOSV_APIS/ApisUsuarioAdmin/listadeUsuarios/listadoDeUsuario.php";
    static final String URL_Eliminar= "http://gpssandcloud.com/RAYOSV_APIS/ApisUsuarioAdmin/listadeUsuarios/Eliminar.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario_ua);

        activityRegistroUsuarioUaBinding = ActivityRegistroUsuarioUaBinding.inflate(getLayoutInflater());
        setContentView( activityRegistroUsuarioUaBinding.getRoot());

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mPreferences=getSharedPreferences(sharedprofFile,MODE_PRIVATE);
        Signedinusername = (TextView)findViewById(R.id.Nombre_Usuario);
        objGlobalAux= (ClaseGlobal) getApplicationContext();
        nombre = objGlobalAux.getNombreUsuario();
        Signedinusername.setText(nombre);

        añadir = (Button)findViewById(R.id.Retonarmenu);
        buscar = (Button)findViewById(R.id.Regresar);

        ListaUsers = findViewById(R.id.listaDatosLoca);//llamado al RecycleView
        ListaUsers.setHasFixedSize(true);
        ListaUsers.setLayoutManager( new LinearLayoutManager(this));

        Listado_de_Usuarios();

        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intenbuscaruser = new Intent(RegistroUsuario_UA.this, com.gepc.proyectosensores.BuscarUser.class);
                startActivity(intenbuscaruser);
                finish();
            }
        });

        añadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intenbuscaruser = new Intent(RegistroUsuario_UA.this, com.gepc.proyectosensores.AgregarUsersAD_OP.class);
                startActivity(intenbuscaruser);
                finish();
            }
        });
    }

    private void Listado_de_Usuarios() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_USERS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        listaTotal.clear();
                        Log.e("tagconvertstr", "[" + response + "]");
                        try {
                            JSONArray array = new JSONArray(response);
                            for ( int indice = 0; indice < array.length(); indice++){
                                JSONObject usuariosG = (JSONObject) array.get(indice);
                                listaTotal.add(new Usuarios(usuariosG.getInt("Id_usuarios"),
                                        usuariosG.getString("NombreC_U"),
                                        usuariosG.getString("cedula_Usuario"),
                                        usuariosG.getString("Telefono"),
                                        usuariosG.getString("Edad"),
                                        usuariosG.getString("contrasena"),
                                        usuariosG.getString("Descripcion_TU")));
                            }
                            AdapterNecesario = new ListaUsuariosAdaptor(getApplicationContext(), listaTotal, new ListaUsuariosAdaptor.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {
                                    String TipoUsers= listaTotal.get(position).getView_TipoUser();
                                    if(TipoUsers.equals("General")){
                                        AlertDialog.Builder builderre= new AlertDialog.Builder(view.getContext());
                                        ProgressDialog progressDialogo = new ProgressDialog(view.getContext());
                                        String[] dialogItems ={"Ver datos de este Usuarios"};
                                        builderre.setTitle(listaTotal.get(position).getView_IdNombre());
                                        builderre.setItems(dialogItems, new DialogInterface.OnClickListener() {

                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                switch (which) {
                                                    case 0:
                                                            startActivity(new Intent(getApplicationContext(), MostrarDatosUsers.class).
                                                                    putExtra("position", position));
                                                            finish();
                                                        break;
                                                }
                                            }
                                        });
                                        builderre.create().show();
                                    }else{
                                        AlertDialog.Builder builder= new AlertDialog.Builder(view.getContext());
                                        ProgressDialog progressDialog = new ProgressDialog(view.getContext());
                                        String[] dialogItem ={"Ver datos de este Usuario", "Eliminar este Usuario", "Modificar información de Usuario"};
                                        builder.setTitle(listaTotal.get(position).getView_IdNombre());
                                        builder.setItems(dialogItem, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int opcion) {
                                                switch (opcion){
                                                    case 0:
                                                            startActivity(new Intent(getApplicationContext(), MostrarDatosUsers.class).
                                                                    putExtra("position", position));
                                                            finish();
                                                        break;
                                                    case 1:
                                                         int Auxiliar;
                                                         String IdAuxiliar;
                                                         Auxiliar= listaTotal.get(position).getView_IdUsuario();
                                                         IdAuxiliar= Integer.toString(Auxiliar);
                                                         EliminarDatos(IdAuxiliar);
                                                        break;
                                                    case 2:
                                                        startActivity(new Intent(getApplicationContext(), ModificarDatosUsers.class).
                                                               putExtra("position", position));
                                                        finish();
                                                        break;
                                                }
                                            }
                                        });
                                        builder.create().show();
                                    }
                                }
                            });
                            ListaUsers.setAdapter(AdapterNecesario);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Error en el proceso " + e, Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error en buscar  el registro Usuario  " + error, Toast.LENGTH_LONG).show();
            }
        });
        Volley.newRequestQueue(getApplicationContext()).add(stringRequest);
    }

    private void EliminarDatos (final String idUser){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_Eliminar,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("tagconvertstr","[ "+response+" ]");
                        try{
                            JSONObject jsonObject = new JSONObject(response.toString());
                            String successAux = jsonObject.getString("success");
                            if(successAux.equals("logrado")){
                                Toast.makeText(getApplicationContext()," El usuario ha sido eliminado correctamente",Toast.LENGTH_LONG).show();
                                startActivity(new Intent(getApplicationContext(),RegistroUsuario_UA.class));
                                finish();
                            }else{
                                Toast.makeText(getApplicationContext()," El usuario no ha sido eliminado ",Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),"Error en Eliminar este Usuario "+e,Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Error en realizar la acción de eliminación "+error,Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<>();
                params.put("idUsuario",idUser);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}