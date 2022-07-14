package com.example.amstlab3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class IngresarDatos extends AppCompatActivity {
    TextView prueba, datos;
    Button btnRequest;
    public RequestQueue mQueue;
    public RequestQueue mRequestQueue;
    public StringRequest mStringRequest;
    private static final String TAG = IngresarDatos.class.getName();
    private String token = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingresar_datos);
        mQueue = Volley.newRequestQueue(this);
        prueba = (TextView) findViewById(R.id.textView5);
        btnRequest = (Button) findViewById(R.id.button);
        final EditText temp = (EditText) findViewById(R.id.valorTemp);
        final EditText hum = (EditText) findViewById(R.id.valorHumedad);
        final EditText pes = (EditText) findViewById(R.id.valorPeso);
        Bundle extras =  getIntent().getExtras();
        this.token = extras.getString("token");
        btnRequest.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v){
                                              System.out.println("aqui");

                                              String str_temp = temp.getText().toString();
                                              String str_hum = hum.getText().toString();
                                              String str_pes = pes.getText().toString();
                                              System.out.println(str_temp);
                                              SubirDatos(str_temp, str_hum, str_pes);
                                          }
                                      }
        );
    }


    public void SubirDatos(String temp, String hum, String pes){
        Map<String, String> params = new HashMap();
        params.put("temperatura", temp);
        params.put("humedad", hum);
        params.put("peso", pes);
        JSONObject parametros = new JSONObject(params);
        String login_url = "https://amst-labx.herokuapp.com/api/sensores";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, login_url, parametros, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response);
                prueba.setText(response.toString());
                Toast.makeText(getApplicationContext(),"Response : Datos ingresados con éxito", Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void
            onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Response :" + error.toString(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "JWT " + token);
                System.out.println(token);
                return params;
            }
        };
        mQueue.add(request);
    }
}