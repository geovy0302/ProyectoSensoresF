package com.gepc.proyectosensores;

import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import java.util.List;

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

    static final String URL_USERS = "http://gpssandcloud.com/RAYOSV_APIS/ApisUsuarioAdmin/listadoDeUsuario.php";

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

        añadir = (Button)findViewById(R.id.Añadir);
        buscar = (Button)findViewById(R.id.Buscar_user);

        ListaUsers = findViewById(R.id.listaUsuarios);//llamado al RecycleView
        ListaUsers.setHasFixedSize(true);
        ListaUsers.setLayoutManager( new LinearLayoutManager(this));

        Listado_de_Usuarios();
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
                                    //Toast.makeText(getApplicationContext(), "Este valor de la posición "+ String.valueOf(position), Toast.LENGTH_LONG).show();
                                    AlertDialog.Builder builder= new AlertDialog.Builder(view.getContext());
                                    ProgressDialog progressDialog = new ProgressDialog(view.getContext());
                                    String[] dialogItem ={"Ver datos Usuarios", "Eliminar Usuario", "Modificar información Usuario"};
                                    builder.setTitle(listaTotal.get(position).getView_IdNombre());
                                    builder.setItems(dialogItem, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int opcion) {
                                            switch (opcion){
                                                case 0:
                                                    //Toast.makeText(getApplicationContext(), " escogiste mostrar datos ", Toast.LENGTH_LONG).show();
                                                    /*startActivity(new Intent(getApplicationContext(), mostrarDatos.class).
                                                            putExtra("position", position));
                                                    finish();*/
                                                    break;
                                                case 1:
                                                    //Toast.makeText(getApplicationContext(), " escogiste eliminar datos ", Toast.LENGTH_LONG).show();
                                                    /*int Auxiliar;
                                                    String IdAuxiliar;
                                                    Auxiliar= listaTotal.get(position).getView_IdUsuario();
                                                    IdAuxiliar= Integer.toString(Auxiliar);
                                                    EliminarDatos(IdAuxiliar);*/
                                                    break;
                                                case 2:
                                                    //Toast.makeText(getApplicationContext(), " escogiste modificar datos ", Toast.LENGTH_LONG).show();
                                                   /* startActivity(new Intent(getApplicationContext(), ModificarDatos.class).
                                                            putExtra("position", position));
                                                    finish();*/
                                                    break;
                                            }
                                        }
                                    });
                                    builder.create().show();
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
}