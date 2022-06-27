package com.gepc.proyectosensores;

import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;


public class BasedelMenuOpcUGeneral extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    ClaseGlobal objGlobalAux;
    String tipo_User;

    @Override
    public void setContentView(View view) {
        drawerLayout = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_basedel_menu_ugeneral, null);
        FrameLayout contenedorOpciones= drawerLayout.findViewById(R.id.actividadContenedorUGeneral);
        contenedorOpciones.addView(view);
        super.setContentView(drawerLayout);

        Toolbar toolbar = drawerLayout.findViewById(R.id.toolbarin);
        setSupportActionBar(toolbar);

        NavigationView navegacionentreopciones = drawerLayout.findViewById(R.id.navegacion_vista_UGeneral);
        navegacionentreopciones.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar, R.string.menu_drawer_open, R.string.menu_drawer_close );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout. closeDrawer(GravityCompat.START);
        objGlobalAux= (ClaseGlobal) getApplicationContext();
        tipo_User= objGlobalAux.getTipo_usuario();
        switch (item.getItemId()){
            case R.id.home_inicio:
                startActivity(new Intent(this, BienvenidaUserGeneral.class));
                overridePendingTransition(0, 0);
                finish();
                break;

            case R.id.Pantalla1:
                startActivity(new Intent(this, EscogerLocalizacion_UGeneral.class));
                overridePendingTransition(0, 0);
                finish();
                break;

            case R.id.Pantalla2:
                startActivity(new Intent(this, InformacionAPP_UGene.class));
                overridePendingTransition(0, 0);
                finish();
                break;

            case R.id.Pantalla3:
                startActivity(new Intent(this, RedesSocialesUGeneral.class));
                overridePendingTransition(0, 0);
                finish();
                break;

            case R.id.Pantalla4:
                if(tipo_User.equals("1")){
                    startActivity(new Intent(this, loginNormal.class));
                    overridePendingTransition(0, 0);
                    finish();
                }
                if(tipo_User.equals("2")){
                    startActivity(new Intent(this, MenuPrincipalAdmin.class));
                    overridePendingTransition(0, 0);
                    finish();
                }
                if(tipo_User.equals("3")){
                    startActivity(new Intent(this, MenuPrincipalOperadores.class));
                    overridePendingTransition(0, 0);
                    finish();
                }
                break;
        }
        return false;

    }

    protected void titulodetodaslasactividades (String tiuloActividad){
        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle(tiuloActividad);
        }
    }
}