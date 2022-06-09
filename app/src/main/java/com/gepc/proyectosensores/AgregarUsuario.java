package com.gepc.proyectosensores;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AgregarUsuario extends AppCompatActivity {
    EditText Name,cedula,Edad, Pass ;
    Button rregister;
    String sname,susername,spassword, sedad;
    ProgressDialog pdDialog;
    String URL_REGISTER = "http://192.168.88.24/ApiProyecto/ApisUsuarioGeneral/registration.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_usuario);
        Button regresar_menu = (Button)findViewById(R.id.regresar);
        regresar_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intenregresar_menu = new Intent(AgregarUsuario.this, com.gepc.proyectosensores.loginNormal.class);
                startActivity(intenregresar_menu);
            }
        });

        Name=(EditText)findViewById(R.id.editName);
        cedula=(EditText)findViewById(R.id.usuario_cedula);
        Edad= (EditText)findViewById(R.id.edad);
        Pass=(EditText)findViewById(R.id.editPass);
        rregister=(Button) findViewById(R.id.anadir);
        rregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sname=Name.getText().toString();
                sedad=Edad.getText().toString();
                susername=cedula.getText().toString();
                spassword=Pass.getText().toString();
                pdDialog= new ProgressDialog(AgregarUsuario.this);
                pdDialog.setTitle(" Registrando Usuario...");
                pdDialog.setCancelable(false);
                if(sname.isEmpty()||susername.isEmpty()||spassword.isEmpty()||sedad.isEmpty())
                {
                    pdDialog.dismiss();
                    Toast.makeText(AgregarUsuario.this," Campos Vacíos, Por favor completelos ",Toast.LENGTH_SHORT).show();
                }
                else{
                    Register();
                }
            }
        });
    }
    private void Register()
    {
        pdDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("tagconvertstr","[ "+response+" ]");
                        try{
                            JSONObject jsonObject = new JSONObject(response.toString());
                            String message = jsonObject.getString("message");
                            String success = jsonObject.getString("success");
                            if(success.equals("success")){
                                pdDialog.dismiss();
                                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                            }
                            if(success.equals("success 2")){
                                pdDialog.dismiss();
                                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                            }
                            if(success.equals("success 3")){
                                pdDialog.dismiss();
                                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            pdDialog.dismiss();
                            Toast.makeText(getApplicationContext(),"Error en registrar el Usuario "+e,Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pdDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Error en realizar la acción de ingresión "+error,Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<>();
                params.put("Nombre_Com",sname);
                params.put("cedula_Usuario",susername);
                params.put("Edad",sedad);
                params.put("contrasena",spassword);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}