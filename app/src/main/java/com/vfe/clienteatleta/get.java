package com.vfe.clienteatleta;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class get extends AppCompatActivity {

    private TextView competidoresTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get);

        competidoresTextView = findViewById(R.id.competidores);

        // Realizar la solicitud GET al servidor al iniciar la actividad
        obtenerResultados();
    }

    private void obtenerResultados() {
        String url = "https://2w0ds9m0df.execute-api.us-east-2.amazonaws.com/Prueba/";

        // Crear el objeto JSON con los parámetros necesarios
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("d", 0);
            jsonBody.put("c", 0);
            jsonBody.put("m", "GET");

            // Crear la solicitud GET
            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST, // Aunque parece un GET, la API de Lambda usa POST
                    url,
                    jsonBody,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                // Acceder al campo 'body' de la respuesta
                                String body = response.getString("body");
                                JSONObject bodyJson = new JSONObject(body); // Convertir el body a JSONObject

                                // Extraer el mensaje y los resultados
                                String mensaje = bodyJson.getString("mensaje");
                                JSONArray resultados = bodyJson.getJSONArray("resultados");

                                // Crear el mensaje de los resultados ordenados
                                StringBuilder resultadoTexto = new StringBuilder(mensaje + "\n");

                                for (int i = 0; i < resultados.length(); i++) {
                                    JSONObject corredor = resultados.getJSONObject(i);
                                    int corredorId = corredor.getInt("corredor");
                                    String tiempo = corredor.getString("tiempo");

                                    // Añadir el resultado al StringBuilder
                                    resultadoTexto.append("Corredor ").append(corredorId)
                                            .append(": ").append(tiempo).append(" segundos\n");
                                }

                                // Mostrar los resultados en el TextView
                                competidoresTextView.setText(resultadoTexto.toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(get.this, "Error al procesar la respuesta del servidor", Toast.LENGTH_SHORT).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(get.this, "Error al obtener los resultados: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
            );

            // Agregar la solicitud a la cola de Volley
            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(request);

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al crear el JSON: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}