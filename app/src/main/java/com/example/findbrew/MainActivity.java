package com.example.findbrew;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;


// icons from www.iconpacks.net
public class MainActivity extends AppCompatActivity {

    private Button button_start;
    private ImageView imageView_main;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_start = findViewById(R.id.button_start);
        button_start.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
            startActivity(intent);
        });

        imageView_main = findViewById(R.id.imageView_main);
        Picasso.get().load("file:///android_asset/beer-icon.png").into(imageView_main);
    }
}