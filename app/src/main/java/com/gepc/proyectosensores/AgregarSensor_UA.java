package com.gepc.proyectosensores;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gepc.proyectosensores.databinding.ActivityAgregarSensorUaBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AgregarSensor_UA extends BasedelMenuOpcUAdmin {
    ActivityAgregarSensorUaBinding activityAgregarSensorUaBinding;
    SharedPreferences mPreferences;
    String sharedprofFile="com.gepc.proyectosensores";
    SharedPreferences.Editor preferencesEditor;
    String nombre, app_a_visitar, indice_tip,id_User,login_User, Localizacion_esogida, tipoDeSensorEscogido, telefonouser,Edaduser, Password_User ;
    TextView Signedinusername, Contenedor_Tips;
    ClaseGlobal objGlobalAux;
    int position;
    ProgressDialog pdDialog;
    TextView idUser,Edad_user, PasswordUser;
    EditText loginUser,telefono_user,nombreUser;
    Button retornar, modificar;
    Spinner localizacionesS, TipodeSensoresS;
    ArrayList<ClaseGlobalLocalizaciones> arrayLocaliazciones = new ArrayList<ClaseGlobalLocalizaciones>();
    ArrayList<TiposDeSensoresUni> arrayTipoSensores = new ArrayList<TiposDeSensoresUni>();

    static final String URL_BUSCARLOCAL = "http://gpssandcloud.com/RAYOSV_APIS/ApisUsuarioAdmin/listadodeLocalizaciones.php";
    static final String URL_BUSCARTIPOSENSOR = "http://gpssandcloud.com/RAYOSV_APIS/ApisUsuarioAdmin/listadodeTipoDeSensores.php";
    static final String URL_AGREGAR = "http://gpssandcloud.com/RAYOSV_APIS/ApisUsuarioAdmin/ListadeSensores/registration.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_sensor_ua);

        activityAgregarSensorUaBinding = ActivityAgregarSensorUaBinding.inflate(getLayoutInflater());
        setContentView( activityAgregarSensorUaBinding.getRoot());

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mPreferences=getSharedPreferences(sharedprofFile,MODE_PRIVATE);
        Signedinusername = (TextView)findViewById(R.id.Nombre_Usuario);
        objGlobalAux= (ClaseGlobal) getApplicationContext();
        nombre = objGlobalAux.getNombreUsuario();
        Signedinusername.setText(nombre);


        nombreUser = (EditText) findViewById(R.id.Nombre_locali);
        loginUser = (EditText) findViewById(R.id.Login_user);
        telefono_user = (EditText) findViewById(R.id.Telefono_user);


        localizacionesS = (Spinner) findViewById(R.id.SpinnerLocali);
        TipodeSensoresS = (Spinner) findViewById(R.id.spinnerTipoSensor);

        retornar= (Button) findViewById(R.id.Regresar);
        modificar= (Button) findViewById(R.id.Modificar);

        localizacionesBuscar();
        localizacionesS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Localizacion_esogida = parent.getSelectedItem().toString();
                //Toast.makeText(parent.getContext(), "Ha seleccionado: "+ Localizacion_esogida , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        tiposSensoreBuscar();
        TipodeSensoresS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tipoDeSensorEscogido = parent.getSelectedItem().toString();
                //Toast.makeText(parent.getContext(), "Ha seleccionado: "+ tipoDeSensorEscogido , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        retornar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intenregresar_menu = new Intent(AgregarSensor_UA.this, com.gepc.proyectosensores.RegistroSensores_UA.class);
                startActivity(intenregresar_menu);
                finish();
            }
        });

        modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id_User= nombreUser.getText().toString();
                login_User= loginUser.getText().toString();
                telefonouser= telefono_user.getText().toString();
                pdDialog= new ProgressDialog(AgregarSensor_UA.this);
                pdDialog.setTitle(" Registrando los datos de este Sensor...");
                pdDialog.setCancelable(false);

                if(id_User.isEmpty()||login_User.isEmpty()||telefonouser.isEmpty())
                {
                    pdDialog.dismiss();
                    Toast.makeText(AgregarSensor_UA.this," Campos Vacíos, Por favor completelos ",Toast.LENGTH_SHORT).show();
                }else{
                    Registrar();
                }

            }
        });
    }

    public void localizacionesBuscar(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_BUSCARLOCAL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //listaTotal.clear();
                        arrayLocaliazciones.clear();
                        Log.e("tagconvertstr", "[" + response + "]");
                        try {
                            JSONArray array = new JSONArray(response);
                            for ( int indice = 0; indice < array.length(); indice++){
                                ClaseGlobalLocalizaciones localizacion = new ClaseGlobalLocalizaciones();
                                localizacion.setDescripcion(array.getJSONObject(indice).getString("Descripcion_Lregion"));
                                arrayLocaliazciones.add(localizacion);
                            }
                            ArrayAdapter<ClaseGlobalLocalizaciones> adapterLocakizaciones = new ArrayAdapter<ClaseGlobalLocalizaciones>(AgregarSensor_UA.this,android.R.layout.simple_dropdown_item_1line,arrayLocaliazciones);
                            localizacionesS.setAdapter(adapterLocakizaciones);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Error en el proceso " + e, Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error cargar las locaizaciones  " + error, Toast.LENGTH_LONG).show();
            }

        });
        Volley.newRequestQueue(getApplicationContext()).add(stringRequest);
    }

    public void tiposSensoreBuscar(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_BUSCARTIPOSENSOR,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        arrayTipoSensores.clear();
                        Log.e("tagconvertstr", "[" + response + "]");
                        try {
                            JSONArray array = new JSONArray(response);
                            for ( int indice = 0; indice < array.length(); indice++){
                                TiposDeSensoresUni TipoSensoress = new TiposDeSensoresUni();
                                TipoSensoress.setDescripcion(array.getJSONObject(indice).getString("Descripcion_TS"));
                                arrayTipoSensores.add(TipoSensoress);
                            }
                            ArrayAdapter<TiposDeSensoresUni> adapterTipoSensores = new ArrayAdapter<TiposDeSensoresUni>(AgregarSensor_UA.this,android.R.layout.simple_dropdown_item_1line,arrayTipoSensores);
                            TipodeSensoresS.setAdapter(adapterTipoSensores);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Error en el proceso " + e, Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error en cargar los tipos de Sensores  " + error, Toast.LENGTH_LONG).show();
            }

        });
        Volley.newRequestQueue(getApplicationContext()).add(stringRequest);
    }

    private void Registrar()
    {
        pdDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_AGREGAR,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("tagconvertstr","[ "+response+" ]");
                        try{
                            JSONObject jsonObject = new JSONObject(response.toString());
                            String success = jsonObject.getString("success");
                            if(success.equals("success")){
                                pdDialog.dismiss();
                                Toast.makeText(getApplicationContext(),"Lo sentimos, ya este Sensor existe",Toast.LENGTH_LONG).show();
                            }
                            if(success.equals("success 2")){
                                pdDialog.dismiss();
                                Toast.makeText(getApplicationContext(),"Los datos del Nuevo Sensor han sido ingresados exitosamente",Toast.LENGTH_LONG).show();
                                Intent intenregresar_menu = new Intent(AgregarSensor_UA.this, com.gepc.proyectosensores.AgregarSensor_UA.class);
                                startActivity(intenregresar_menu);
                                finish();
                            }
                            if(success.equals("success 3")){
                                pdDialog.dismiss();
                                Toast.makeText(getApplicationContext(),"La Insersción no llevó a cabo porque hubo un error",Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            pdDialog.dismiss();
                            Toast.makeText(getApplicationContext(),"Error en agregar este Sensor"+e,Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pdDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Error en realizar la acción de de inserción  "+error,Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<>();
                params.put("Nombre_Sensor",id_User);
                params.put("Coordena_latidud",login_User);
                params.put("Coordena_Longitud",telefonouser);
                params.put("localizacion",Localizacion_esogida);
                params.put("TipoSensor",tipoDeSensorEscogido);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}