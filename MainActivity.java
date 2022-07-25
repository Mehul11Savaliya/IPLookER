package com.ms.iplooker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback
{
 ImageButton search;
 EditText E;
 TextView T;
 RequestQueue queue;
double lat=0,lang=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        search = findViewById(R.id.search);
        E = findViewById(R.id.URL);
        T = findViewById(R.id.data);

        queue = Volley.newRequestQueue(MainActivity.this);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url ="http://ipwho.is/"+E.getText().toString().trim();
                JsonObjectRequest rqst = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            T.setText("");
                            String data[] = new String[]{
                                    "\nip : "+response.getString("ip"),
                                    "\ntype : "+response.getString("type"),
                                    "\ncontinent : "+response.getString("continent"),
                                    "\ncontinent_code : "+response.getString("continent_code"),
                                    "\ncountry : "+response.getString("country"),
                                    "\ncountry_code : "+response.getString("country_code"),
                                    "\nregion : "+response.getString("region"),
                                    "\nregion_code : "+response.getString("region_code"),
                                    "\ncity : "+response.getString("city"),
                                    "\nlatitude : "+response.getString("latitude"),
                                    "\nlongitude : "+response.getString("longitude"),
                                    "\nis_eu : "+response.getString("is_eu"),
                                    "\npostal : "+response.getString("postal"),
                                    "\ncalling_code : "+response.getString("calling_code"),
                                    "\ncapital : "+response.getString("capital"),
                                    "\nborders : "+response.getString("borders")


                            };
                            lat = Double.parseDouble(response.getString("latitude"));
                            lang = Double.parseDouble(response.getString("longitude"));
                            //"\nSequrity : "+response.getString("security"),
                            //                                    "\ncurrency : "+response.getString(" currency"),
                            //                                    "\ntimezones : "+response.getString("timezone")
                            if(response.getString("success").equalsIgnoreCase("true")){
                            for (String s : data){
                                T.append(s);
                                                           }
                           }
                            else{
                                Toast.makeText(MainActivity.this, "Error in fetching IP Details!", Toast.LENGTH_SHORT).show();
                            }
                                                  }
                        catch (Exception e){
                            Toast.makeText(MainActivity.this, "Invalid IP!", Toast.LENGTH_SHORT).show();
                            Log.d("tag",e.getMessage());
                        }

                }}, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("tag",error.getMessage());
                        Toast.makeText(MainActivity.this, "Something went wrong \nCheck internet connection.", Toast.LENGTH_SHORT).show();
                    }
                });
             queue.add(rqst);
            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(lat,lang))
                .title("loc"));
    }
}
