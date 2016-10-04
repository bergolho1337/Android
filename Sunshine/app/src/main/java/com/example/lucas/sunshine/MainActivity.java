package com.example.lucas.sunshine;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

// Since the OpenWeatherMap needs now an API Key to get incoming request for it's services.
// -----    My API acess key is: 3930513255bb55c0e999348bcc77174e
// To make an API call.
// -----    http://api.openweathermap.org/data/2.5/forecast/city?id={CITY_ID}&APPID={APIKEY}
// This will return the forecast for one day.
// -----    api.openweathermap.org/data/2.5/forecast?q=London,us&mode=xml
// Now this will return a 5-day forecast for London in return-type xml. (It can be xml,json,html)
// The URL request used on the Sunshine example page is given
//      - For 1 week of forecast
//      - The postal code is 94043, but i will use for "Juiz de Fora"
//      - The return type is to be set to JSON format (default)
//      - And the unit of the temperature should be given in metric (we will be able to convert later on the app)

// Solution: api.openweathermap.org/data/2.5/forecast?q=94043,us&mode=json&unit=metric&cnt=7

// *** The id and name of a city can be found in the file "/libs/city.list.json" *****



public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        // Mostra o layout do arquivo "activity_main.xml"
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null)
        {
            // Adiciona o Fragment relacionado a lista de previsoes do tempo
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container,new ForecastFragment())
                            .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }
}

