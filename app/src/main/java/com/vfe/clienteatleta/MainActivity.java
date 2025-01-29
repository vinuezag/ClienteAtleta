package com.vfe.clienteatleta;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private Button btnPost, btnPut, btnGet, btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPost = findViewById(R.id.btnPost);
        btnGet = findViewById(R.id.btnGet);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.btnPost) {
                    startActivity(new Intent(MainActivity.this, post.class));
                }  else if (v.getId() == R.id.btnGet) {
                    startActivity(new Intent(MainActivity.this, get.class));
                }
            }

        };

        btnPost.setOnClickListener(listener);
        btnPut.setOnClickListener(listener);
        btnGet.setOnClickListener(listener);
        btnDelete.setOnClickListener(listener);

    }
}