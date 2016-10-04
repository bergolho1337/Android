package com.lagm.typesmaps;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap map;          // Referencia para o objeto que controla o mapa
    private Button button;          // Referencia para o botao do layout
    PopupMenu popup;                // Referencia para o menu

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Captura a referencia para o botao
        button = (Button)findViewById(R.id.button);

        // Captura o Fragment do mapa e notifica quando ele estiver pronto.
        // (Usar SupportMapFragment para API level < 11)
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        //map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        //map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        //map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        //map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        map = googleMap;
        map.setMapType(GoogleMap.MAP_TYPE_NONE);

        // Cria um Listener para o botao
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                showPopup();
            }
        });
    }

    public void showPopup () {
        // Se eh a primeira vez que o menu eh acessado alocar memoria para ele e preenche-lo com 'res/menu/popup.xml'
        if (popup == null) {
            popup = new PopupMenu(MapsActivity.this,button);
            popup.getMenuInflater().inflate(R.menu.popup,popup.getMenu());
        }

        // Criar um Listener de clique no Menu
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                int id = menuItem.getItemId();
                if (id == R.id.action_normal) {
                    map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                    button.setText("Normal");
                }
                else if (id == R.id.action_hybrid) {
                    map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                    button.setText("Hybrid");
                }
                else if (id == R.id.action_satellite) {
                    map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                    button.setText("Satellite");
                }
                else if (id == R.id.action_terrain) {
                    map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                    button.setText("Terrain");
                }
                return true;
            }
        });
        // Mostra o PopupMenu
        popup.show();

    }
}
