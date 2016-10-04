// Primeiro exemplo de app com Mapa do livro.

package com.lagm.myfirstmapapp;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;

/**
// Para ter acesso ao mapa do GoogleMaps eh necessario fazer a comunicacao com o servidor do Google, isto eh assincrono
// O objeto GoogleMaps pode ser obtido diretamente pelo MapFragment, mas pode acontecer de o retorno ser nulo,
// levando o app a crashar.
// A solucao para isso eh implementar metodo que contorne esse problema, isto eh feito atraves do getMapAsync().
// Para usar esse metodo eh necessario implementar as funcoes da interface OnMapReadyCallback()
// A funcao onMapReady() deve ser implementada nesse sentido.
 */
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        /** Usando o Fragment da primeira forma no arquivo activity_maps.xml */
        // Captura o Fragment do mapa no .xml do layout
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);

        // Chama o metodo de callback para verificar quando o mapa estiver pronto para o usuario passando o contexto atual
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
        // Quando mapa estiver pronto mostramos um texto popup dizendo "Map ready" para usuario
        Toast.makeText(getApplicationContext(),"Map ready!",Toast.LENGTH_LONG).show();
        // Tambem ativamos um botao para capturar a localizacao atual do usuario pelo GPS
        googleMap.setMyLocationEnabled(true);
    }
}
