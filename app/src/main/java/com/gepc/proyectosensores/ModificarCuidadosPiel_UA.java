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
import com.gepc.proyectosensores.databinding.ActivityModificarCuidadosPielUaBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ModificarCuidadosPiel_UA extends BasedelMenuOpcUAdmin {
    ActivityModificarCuidadosPielUaBinding activityModificarCuidadosPielUaBinding;
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
    String idCuidadoPiel, nombreCuidadoPiel;
    Button modificar, retornar;
    ProgressDialog pdDialog;
    static final String URL_MODIFICAR = "http://gpssandcloud.com/RAYOSV_APIS/ApisUsuarioAdmin/ListadeCuidadoPiel/Modificar.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_cuidados_piel_ua);

        activityModificarCuidadosPielUaBinding = ActivityModificarCuidadosPielUaBinding.inflate(getLayoutInflater());
        setContentView( activityModificarCuidadosPielUaBinding.getRoot());

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mPreferences=getSharedPreferences(sharedprofFile,MODE_PRIVATE);
        Signedinusername = (TextView)findViewById(R.id.Nombre_Usuario);
        objGlobalAux= (ClaseGlobal) getApplicationContext();
        nombre = objGlobalAux.getNombreUsuario();
        Signedinusername.setText(nombre);

        idlocali = (TextView) findViewById(R.id.Id_locali);
        nombreLocali = (EditText) findViewById(R.id.Nombre_locali);

        idlocali = (TextView) findViewById(R.id.Id_locali);
        nombreLocali = (EditText) findViewById(R.id.Nombre_locali);
        retornar= (Button) findViewById(R.id.Regresar);
        modificar= (Button) findViewById(R.id.Modificar);
        Intent intent = getIntent();
        position= intent.getExtras().getInt("position");

        idlocali.setText(""+ RegistroDeCuidadosPiel_UA.listaTotal.get(position).getView_IdCuidadoPiel());
        nombreLocali.setText(""+ RegistroDeCuidadosPiel_UA.listaTotal.get(position).getView_Nombre_CuidadoPiel());

        retornar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intenregresar_menu = new Intent(ModificarCuidadosPiel_UA.this, com.gepc.proyectosensores.RegistroDeCuidadosPiel_UA.class);
                startActivity(intenregresar_menu);
                finish();
            }
        });

        modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                idCuidadoPiel= idlocali.getText().toString();
                nombreCuidadoPiel= nombreLocali.getText().toString();

                pdDialog= new ProgressDialog(ModificarCuidadosPiel_UA.this);
                pdDialog.setTitle(" Actulizando los datos de este cuidado de la piel...");
                pdDialog.setCancelable(false);
                Modificar();
            }
        });
    }

    private void Modificar()
    {
        pdDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_MODIFICAR,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("tagconvertstr","[ "+response+" ]");
                        try{
                            JSONObject jsonObject = new JSONObject(response.toString());
                            String success = jsonObject.getString("success");
                            if(success.equals("success 2")){
                                pdDialog.dismiss();
                                Toast.makeText(getApplicationContext(),"Los datos han sido modificacdos exitosamente",Toast.LENGTH_LONG).show();
                                Intent intenregresar_menu = new Intent(ModificarCuidadosPiel_UA.this, com.gepc.proyectosensores.RegistroDeCuidadosPiel_UA.class);
                                startActivity(intenregresar_menu);
                                finish();
                            }
                            if(success.equals("success 3")){
                                Toast.makeText(getApplicationContext(),"La modificaci??n no llev?? a cabo porque hubo un error",Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),"Error en Modificar el cuidado de la piel  "+e,Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Error en realizar la acci??n de modificaci??n  "+error,Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<>();
                params.put("idCuidadoPiel",idCuidadoPiel);
                params.put("nombre_Descripcion_Cuidado",nombreCuidadoPiel);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}