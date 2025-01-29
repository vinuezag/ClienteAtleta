package com.vfe.clienteatleta;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

public class post extends AppCompatActivity {

    private EditText txtDistancia, txtCorredores;
    private Button btnInit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        txtDistancia = findViewById(R.id.txtDistancia);
        txtCorredores = findViewById(R.id.txtCorredores);
        btnInit = findViewById(R.id.btnInit);

        btnInit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarDatos();
            }
        });
    }

    private void enviarDatos() {
        String url = "https://2w0ds9m0df.execute-api.us-east-2.amazonaws.com/Prueba/";

        // Obtener los valores ingresados por el usuario
        String distanciaStr = txtDistancia.getText().toString().trim();
        String corredoresStr = txtCorredores.getText().toString().trim();

        if (distanciaStr.isEmpty() || corredoresStr.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            // Convertir los valores a enteros
            int distancia = Integer.parseInt(distanciaStr);
            int corredores = Integer.parseInt(corredoresStr);

            // Crear el objeto JSON con los datos
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("d", distancia);
            jsonBody.put("c", corredores);
            jsonBody.put("m", "POST");

            // Crear la solicitud POST
            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST,
                    url,
                    jsonBody,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                // Acceder al campo 'body' de la respuesta
                                String body = response.getString("body");
                                JSONObject bodyJson = new JSONObject(body); // Convertir el body a JSONObject

                                // Extraer el mensaje
                                String mensaje = bodyJson.getString("mensaje");

                                // Mostrar el mensaje en un Toast
                                Toast.makeText(post.this, mensaje, Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(post.this, "Error al procesar la respuesta del servidor", Toast.LENGTH_SHORT).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(post.this, "Error al enviar los datos: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
            );

            // Agregar la solicitud a la cola de Volley
            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(request);

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Por favor, ingresa números válidos para distancia y corredores", Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al crear el JSON: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
