// Demonstracao do uso do Lite Mode do Google Maps
// Eh usado quando se quer mostrar menos informacao no mapa e limitar as acoes do usuario

package com.lagm.litemodetest;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.GoogleMap;

public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
    }
}
