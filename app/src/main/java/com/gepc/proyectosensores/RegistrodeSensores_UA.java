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
import com.gepc.proyectosensores.adaptadores.ListaLocalizacioneAdaptor;
import com.gepc.proyectosensores.databinding.ActivityRegistrodeSensoresUaBinding;
import com.gepc.proyectosensores.usuarioadmin.LocalizacionesUA;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegistrodeSensores_UA extends BasedelMenuOpcUAdmin {
    ActivityRegistrodeSensoresUaBinding activityRegistrodeSensoresUaBinding;
    public static List<LocalizacionesUA> listaTotal = new ArrayList<>();
    SharedPreferences mPreferences;
    String sharedprofFile="com.gepc.proyectosensores";
    SharedPreferences.Editor preferencesEditor;
    String nombre, app_a_visitar, indice_tip;
    TextView Signedinusername, Contenedor_Tips;
    ClaseGlobal objGlobalAux;
    RecyclerView ListaLocalizaciones;
    ListaLocalizacioneAdaptor AdapterNecesario;
    Button añadir, retornar;
    static final String URL_localizaciones = "http://gpssandcloud.com/RAYOSV_APIS/ApisUsuarioAdmin/ListadeLocalizaciones/listadodeLocalizaciones.php";
    static final String URL_Eliminar= "http://gpssandcloud.com/RAYOSV_APIS/ApisUsuarioAdmin/ListadeLocalizaciones/Eliminar.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrode_sensores_ua);

        activityRegistrodeSensoresUaBinding = ActivityRegistrodeSensoresUaBinding.inflate(getLayoutInflater());
        setContentView( activityRegistrodeSensoresUaBinding.getRoot());

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mPreferences=getSharedPreferences(sharedprofFile,MODE_PRIVATE);
        Signedinusername = (TextView)findViewById(R.id.Nombre_Usuario);
        objGlobalAux= (ClaseGlobal) getApplicationContext();
        nombre = objGlobalAux.getNombreUsuario();
        Signedinusername.setText(nombre);

        añadir = (Button)findViewById(R.id.AñadirLocali);
        retornar = (Button)findViewById(R.id.Retonarmenu);

        ListaLocalizaciones = findViewById(R.id.listaDatosLoca);//llamado al RecycleView
        ListaLocalizaciones.setHasFixedSize(true);
        ListaLocalizaciones.setLayoutManager( new LinearLayoutManager(this));

        Listado_de_Usuarios();

        añadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intenbuscaruser = new Intent(RegistrodeSensores_UA.this, com.gepc.proyectosensores.AgregarLocalizacion_UA.class);
                startActivity(intenbuscaruser);
                finish();
            }
        });

        retornar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intenbuscaruser = new Intent(RegistrodeSensores_UA.this, com.gepc.proyectosensores.MenudeInforSenso_UA.class);
                startActivity(intenbuscaruser);
                finish();
            }
        });
    }
    private void Listado_de_Usuarios() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_localizaciones,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        listaTotal.clear();
                        Log.e("tagconvertstr", "[" + response + "]");
                        try {
                            JSONArray array = new JSONArray(response);
                            for ( int indice = 0; indice < array.length(); indice++){
                                JSONObject usuariosG = (JSONObject) array.get(indice);
                                listaTotal.add(new LocalizacionesUA(usuariosG.getInt("Id_Lregion"),
                                        usuariosG.getString("Descripcion_Lregion")));
                            }
                            AdapterNecesario = new ListaLocalizacioneAdaptor(getApplicationContext(), listaTotal, new ListaLocalizacioneAdaptor.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {
                                    AlertDialog.Builder builder= new AlertDialog.Builder(view.getContext());
                                    ProgressDialog progressDialog = new ProgressDialog(view.getContext());
                                    String[] dialogItem ={"Eliminar esta Localización", "Modificar información de Localización"};
                                    builder.setTitle(listaTotal.get(position).getView_Nombre_Localizacion());
                                    builder.setItems(dialogItem, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int opcion) {
                                            switch (opcion){
                                                    case 0:
                                                        int Auxiliar;
                                                        String IdAuxiliar;
                                                        Auxiliar= listaTotal.get(position).getView_IdLocalizacion();
                                                        IdAuxiliar= Integer.toString(Auxiliar);
                                                        EliminarDatos(IdAuxiliar);
                                                    break;
                                                    case 1:
                                                        startActivity(new Intent(getApplicationContext(), ModificarLocalizacion_UA.class).
                                                                putExtra("position", position));
                                                        finish();
                                                        break;
                                                }
                                            }
                                        });
                                        builder.create().show();
                                }
                            });
                            ListaLocalizaciones.setAdapter(AdapterNecesario);
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

    private void EliminarDatos (final String idLocali){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_Eliminar,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("tagconvertstr","[ "+response+" ]");
                        try{
                            JSONObject jsonObject = new JSONObject(response.toString());
                            String successAux = jsonObject.getString("success");
                            if(successAux.equals("logrado")){
                                Toast.makeText(getApplicationContext()," La localización  ha sido eliminado correctamente",Toast.LENGTH_LONG).show();
                                startActivity(new Intent(getApplicationContext(),RegistrodeSensores_UA.class));
                                finish();
                            }else{
                                Toast.makeText(getApplicationContext()," La localización  no ha sido eliminado ",Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),"Error en Eliminar esta localización  "+e,Toast.LENGTH_LONG).show();
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
                params.put("idUsuario",idLocali);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}