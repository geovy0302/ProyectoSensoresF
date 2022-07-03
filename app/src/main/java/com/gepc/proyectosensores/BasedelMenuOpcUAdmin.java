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


public class BasedelMenuOpcUAdmin extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;

    @Override
    public void setContentView(View view) {
        drawerLayout = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_basedel_menu_uadmin, null);
        FrameLayout contenedorOpciones= drawerLayout.findViewById(R.id.actividadContenedorUADMIN);
        contenedorOpciones.addView(view);
        super.setContentView(drawerLayout);

        Toolbar toolbar = drawerLayout.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NavigationView navegacionentreopciones = drawerLayout.findViewById(R.id.navegacion_vistazo);
        navegacionentreopciones.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar, R.string.menu_drawer_open, R.string.menu_drawer_close );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout. closeDrawer(GravityCompat.START);
        switch (item.getItemId()){
            case R.id.home_inicio:
                startActivity(new Intent(this, BienvenidaUserAdmin.class));
                overridePendingTransition(0, 0);
                finish();
                break;

            case R.id.Pantalla1:
                startActivity(new Intent(this, RegistroUsuario_UA.class));
                overridePendingTransition(0, 0);
                finish();
                break;

            case R.id.Pantalla2:
                startActivity(new Intent(this, MenudeInforSenso_UA.class));
                overridePendingTransition(0, 0);
                finish();
                break;

            case R.id.Pantalla3:
                  startActivity(new Intent(this, DatosPorSensor_UA.class));
                  overridePendingTransition(0, 0);
                  finish();
                break;

            case R.id.Pantalla4:
                startActivity(new Intent(this, MenudeAdministracionTP_CP_UA.class));
                overridePendingTransition(0, 0);
                finish();
                break;

            case R.id.Pantalla5:
                startActivity(new Intent(this, MenuPrincipalAdmin.class));
                overridePendingTransition(0, 0);
                finish();
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