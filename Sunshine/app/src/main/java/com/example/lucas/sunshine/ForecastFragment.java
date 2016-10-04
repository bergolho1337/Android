package com.example.lucas.sunshine;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by lucas on 12/06/16.
 */
public class ForecastFragment extends Fragment
{
    ArrayAdapter<String> mForecastAdapter;

    public ForecastFragment() { }

    // It is the first function to be lauched in the Fragment
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add this line in order the Fragment can handle menu events
        // It will callback the functions onCreateOptionsMenu() and onOptionsItemSelected()
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        String[] data = {
                "Mon 6/23 - Sunny - 31/17",
                "Tue 6/24 - Foggy - 21/8",
                "Wed 6/25 - Cloudy - 22/17",
                "Thurs 6/26 - Rainy - 18/11",
                "Fri 6/27 - Foggy - 21/10",
                "Sat 6/28 - TRAPPED IN WEATHERSTATION - 23/18",
                "Sun 6/29 - Sunny - 20/7"
        };
        List<String> weekForecast = new ArrayList<String>(Arrays.asList(data));
        // Now that we have some dummy forecast data, create an ArrayAdapter.
        // The ArrayAdapter will take data from a source (like our dummy forecast) and
        // use it to populate the ListView it's attached to.
        mForecastAdapter = new ArrayAdapter<String>(getActivity(),R.layout.list_item_forecast,R.id.list_item_forecast_textview,weekForecast);
        View rootView = inflater.inflate(R.layout.fragment_main,container,false);
        // Get a reference to the ListView, and attach this adapter to it.
        ListView listView = (ListView) rootView.findViewById(R.id.listview_forecast);
        listView.setAdapter(mForecastAdapter);
        // Adding a Toast object whenever we click on an item of the ListView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // The Toast will show the forecast and will disapper after a period of time
                String forecast = mForecastAdapter.getItem(position);
                //Toast.makeText(getActivity(),forecast,Toast.LENGTH_SHORT).show();

                // We replace the Toast with an Intent to lauch the DetailActivity
                Intent detailActivity = new Intent(getActivity(),DetailActivity.class)
                        .putExtra(Intent.EXTRA_TEXT,forecast);
                startActivity(detailActivity);

            }
        });

        return rootView;
    }

    // Cria o menu
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.forecast_fragment,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle item selected
        switch (item.getItemId()) {
            case R.id.action_settings:
                //System.out.println("Clicando em Settings");
                return true;
            case R.id.action_refresh:
                //System.out.println("Clicando em Refresh");
                // Manda executar a AsyncTask
                FetchWeatherTask asynctask = new FetchWeatherTask();
                asynctask.execute("94043");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // AsyncTask:
    // in  = URL com o endereco da requisicao
    // out = JSON com o retorno da requisicao em String
    public class FetchWeatherTask extends AsyncTask<String,Void,String[]>
    {
        private final String LOG_TAG = FetchWeatherTask.class.getSimpleName();

        @Override
        protected String[] doInBackground(String... params)
        {
            // Parameters used
            String appid = "3930513255bb55c0e999348bcc77174e";
            String mode = "json";
            String unit = "metric";
            int numDays = 7;

            // Handle the url request for the API
            // To make sure this work we have to add a permission for internet connection in the "AndroidManifest.xml"
            HttpURLConnection urlConnection = null;
            // Read the return of the request in JSON format
            BufferedReader reader = null;

            // Store the returned JSON as a string
            String forecastJSONStr = null;

            try
            {
                final String FORECAST_BASE_URL="http://api.openweathermap.org/data/2.5/forecast/daily?";
                final String ID_PARAM="q";
                final String FORMAT_PARAM="mode";
                final String UNIT_PARAM="units";
                final String DAYS_PARAM="cnt";
                final String APPID_PARAM="APPID";

                Uri uriBuilder = Uri.parse(FORECAST_BASE_URL).buildUpon()
                        .appendQueryParameter(ID_PARAM,params[0])
                        .appendQueryParameter(FORMAT_PARAM,mode)
                        .appendQueryParameter(UNIT_PARAM,unit)
                        .appendQueryParameter(DAYS_PARAM,Integer.toString(numDays))
                        .appendQueryParameter(APPID_PARAM,appid).build();
                // Construct the URL for the API Forecast
                URL url = new URL(uriBuilder.toString());
                Log.i(LOG_TAG,"URL = " + uriBuilder.toString());

                // Create the request to OpenWeather and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Get the input stream into String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null)
                    return null;

                // Agora podemos ler o retorno da API com o Buffered Reader
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                // Enquanto tiver linhas para ler ...
                while ((line = reader.readLine()) != null)
                {
                    // Adicionando uma quebra de linha para debug a cada linha lida
                    buffer.append(line+"\n");
                }
                if (buffer.length() == 0)
                    return null;

                // Atribui a nossa variavel o valor do retorno do JSON
                forecastJSONStr = buffer.toString();

                //System.out.println("JSON string = " + forecastJSONStr);
                Log.i(LOG_TAG,"Forecast JSON String = "+forecastJSONStr);

                // Get the data from the JSON String
                try
                {
                    String[] forecast = getWeatherDataFromJson(forecastJSONStr,numDays);
                    return forecast;
                } catch (JSONException e)
                {
                    Log.e(LOG_TAG, "Error parsing JSON", e);
                    return null;
                }

            } catch (IOException e)
            {
                Log.e(LOG_TAG, "Error", e);
                return null;

            } finally
            {
                // Fechar a conexao
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                // Fechar o stream
                try
                {
                    reader.close();
                } catch (final IOException e)
                {
                    Log.e(LOG_TAG, "Error closing the stream", e);
                }
            }
        }

        @Override
        protected void onPostExecute(String[] strings) {
            if (strings != null) {
                // Update week forecast with the new data
                mForecastAdapter.clear();
                for (String dayForescastStr : strings) {
                    mForecastAdapter.add(dayForescastStr);
                }
            }
        }

        /* The date/time conversion code is going to be moved outside the asynctask later,
                 * so for convenience we're breaking it out into its own method now.
                 */
        private String getReadableDateString(long time){
            // Because the API returns a unix timestamp (measured in seconds),
            // it must be converted to milliseconds in order to be converted to valid date.
            SimpleDateFormat shortenedDateFormat = new SimpleDateFormat("EEE MMM dd");
            return shortenedDateFormat.format(time);
        }

        /**
         * Prepare the weather high/lows for presentation.
         */
        private String formatHighLows(double high, double low) {
            // For presentation, assume the user doesn't care about tenths of a degree.
            long roundedHigh = Math.round(high);
            long roundedLow = Math.round(low);

            String highLowStr = roundedHigh + "/" + roundedLow;
            return highLowStr;
        }

        /**
         * Take the String representing the complete forecast in JSON Format and
         * pull out the data we need to construct the Strings needed for the wireframes.
         *
         * Fortunately parsing is easy:  constructor takes the JSON string and converts it
         * into an Object hierarchy for us.
         */
        private String[] getWeatherDataFromJson(String forecastJsonStr, int numDays)
                throws JSONException {

            // These are the names of the JSON objects that need to be extracted.
            final String OWM_LIST = "list";
            final String OWM_WEATHER = "weather";
            final String OWM_TEMPERATURE = "temp";
            final String OWM_MAX = "max";
            final String OWM_MIN = "min";
            final String OWM_DESCRIPTION = "main";

            JSONObject forecastJson = new JSONObject(forecastJsonStr);
            JSONArray weatherArray = forecastJson.getJSONArray(OWM_LIST);

            // OWM returns daily forecasts based upon the local time of the city that is being
            // asked for, which means that we need to know the GMT offset to translate this data
            // properly.

            // Since this data is also sent in-order and the first day is always the
            // current day, we're going to take advantage of that to get a nice
            // normalized UTC date for all of our weather.

            Time dayTime = new Time();
            dayTime.setToNow();

            // we start at the day returned by local time. Otherwise this is a mess.
            int julianStartDay = Time.getJulianDay(System.currentTimeMillis(), dayTime.gmtoff);

            // now we work exclusively in UTC
            dayTime = new Time();

            String[] resultStrs = new String[numDays];
            for(int i = 0; i < weatherArray.length(); i++) {
                // For now, using the format "Day, description, hi/low"
                String day;
                String description;
                String highAndLow;

                // Get the JSON object representing the day
                JSONObject dayForecast = weatherArray.getJSONObject(i);

                // The date/time is returned as a long.  We need to convert that
                // into something human-readable, since most people won't read "1400356800" as
                // "this saturday".
                long dateTime;
                // Cheating to convert this to UTC time, which is what we want anyhow
                dateTime = dayTime.setJulianDay(julianStartDay+i);
                day = getReadableDateString(dateTime);

                // description is in a child array called "weather", which is 1 element long.
                JSONObject weatherObject = dayForecast.getJSONArray(OWM_WEATHER).getJSONObject(0);
                description = weatherObject.getString(OWM_DESCRIPTION);

                // Temperatures are in a child object called "temp".  Try not to name variables
                // "temp" when working with temperature.  It confuses everybody.
                JSONObject temperatureObject = dayForecast.getJSONObject(OWM_TEMPERATURE);
                double high = temperatureObject.getDouble(OWM_MAX);
                double low = temperatureObject.getDouble(OWM_MIN);

                highAndLow = formatHighLows(high, low);
                resultStrs[i] = day + " - " + description + " - " + highAndLow;
            }

            for (String s : resultStrs) {
                Log.i(LOG_TAG, "Forecast entry: " + s);
            }
            return resultStrs;

        }
    }
}
