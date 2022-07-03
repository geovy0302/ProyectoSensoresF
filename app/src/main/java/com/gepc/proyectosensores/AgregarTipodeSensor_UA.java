package com.gepc.proyectosensores;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gepc.proyectosensores.adaptadores.ListaUsuariosAdaptor;
import com.gepc.proyectosensores.databinding.ActivityAgregarTipodeSensorUaBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AgregarTipodeSensor_UA extends BasedelMenuOpcUAdmin {
    ActivityAgregarTipodeSensorUaBinding activityAgregarTipodeSensorUaBinding;
    SharedPreferences mPreferences;
    String sharedprofFile="com.gepc.proyectosensores";
    SharedPreferences.Editor preferencesEditor;
    String nombre, app_a_visitar, indice_tip;
    TextView Signedinusername, Contenedor_Tips;
    TextView idlocali;
    EditText nombreLocali;
    int position;
    ClaseGlobal objGlobalAux;
    ListaUsuariosAdaptor AdapterNecesario;
    String idTipoSensor, nombreTipoSensor;
    Button Añadirlocali, retornar;
    ProgressDialog pdDialog;
    static final String URL_Agregar = "http://gpssandcloud.com/RAYOSV_APIS/ApisUsuarioAdmin/ListadeTiposSensores/registration.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_tipode_sensor_ua);
        activityAgregarTipodeSensorUaBinding = ActivityAgregarTipodeSensorUaBinding.inflate(getLayoutInflater());
        setContentView( activityAgregarTipodeSensorUaBinding.getRoot());

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mPreferences=getSharedPreferences(sharedprofFile,MODE_PRIVATE);
        Signedinusername = (TextView)findViewById(R.id.Nombre_Usuario);
        objGlobalAux= (ClaseGlobal) getApplicationContext();
        nombre = objGlobalAux.getNombreUsuario();
        Signedinusername.setText(nombre);


        nombreLocali = (EditText) findViewById(R.id.Nombre_locali);
        retornar= (Button) findViewById(R.id.RegresarMenu);
        Añadirlocali= (Button) findViewById(R.id.Añadirlocali);


        retornar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intenregresar_menu = new Intent(AgregarTipodeSensor_UA.this, com.gepc.proyectosensores.RegistrodeTipodeSensores_UA.class);
                startActivity(intenregresar_menu);
                finish();
            }
        });

        Añadirlocali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                nombreTipoSensor= nombreLocali.getText().toString();
                pdDialog= new ProgressDialog(AgregarTipodeSensor_UA.this);
                pdDialog.setTitle(" Regitrando los datos de este Nuevo tipo de Sensor...");
                pdDialog.setCancelable(false);
                if(nombreTipoSensor.isEmpty())
                {
                    pdDialog.dismiss();
                    Toast.makeText(AgregarTipodeSensor_UA.this," Campos Vacíos, Por favor completelos ",Toast.LENGTH_SHORT).show();
                }else{
                    Registrar();
                }

            }
        });
    }

    private void Registrar()
    {
        pdDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_Agregar,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("tagconvertstr","[ "+response+" ]");
                        try{
                            JSONObject jsonObject = new JSONObject(response.toString());
                            String success = jsonObject.getString("success");
                            if(success.equals("success")){
                                pdDialog.dismiss();
                                Toast.makeText(getApplicationContext(),"Lo sentimos, ya este Tipo de Sensor existe",Toast.LENGTH_LONG).show();
                            }
                            if(success.equals("success 2")){
                                pdDialog.dismiss();
                                Toast.makeText(getApplicationContext(),"Los datos del Tipo de Sensores han sido ingresados exitosamente",Toast.LENGTH_LONG).show();
                                Intent intenregresar_menu = new Intent(AgregarTipodeSensor_UA.this, com.gepc.proyectosensores.AgregarTipodeSensor_UA.class);
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
                            Toast.makeText(getApplicationContext(),"Error en agregar este tipo de Sensores "+e,Toast.LENGTH_LONG).show();
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
                params.put("nombre_Descripcion_TipoS",nombreTipoSensor);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}