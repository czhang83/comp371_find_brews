package com.example.findbrew;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ProductActivity extends AppCompatActivity {
    private TextView textView_product_name;
    private TextView textView_product_abv;
    private TextView textView_product_first_brewed;
    private TextView textView_product_description;
    private TextView textView_product_pairing;
    private TextView textView_product_tips;

    private ImageView imageView_product_beer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        if(getIntent().getExtras() != null) {
            Brew brew = getIntent().getParcelableExtra("brew");

            textView_product_name = findViewById(R.id.textView_product_name);
            textView_product_abv = findViewById(R.id.textView_product_abv);
            textView_product_first_brewed = findViewById(R.id.textView_product_first_brewed);
            textView_product_description = findViewById(R.id.textView_product_description);
            textView_product_pairing = findViewById(R.id.textView_product_pairing);
            textView_product_tips = findViewById(R.id.textView_product_tips);
            imageView_product_beer = findViewById(R.id.imageView_product_beer);

            textView_product_name.setText(brew.getName());
            textView_product_abv.setText(String.format("ABV: %s %%", brew.getAbv()));
            textView_product_first_brewed.setText(String.format("First brewed: %s", brew.getFirst_brewed()));
            textView_product_description.setText(String.format("Description: %s", brew.getDescription()));
            textView_product_pairing.setText(String.format("Food pairings: %s", brew.getFood_pairings()));
            textView_product_tips.setText(String.format("Brewster's tips: %s", brew.getBrewster_tips()));
            Picasso.get().load(brew.getImageUrl()).into(imageView_product_beer);

        }


    }
}


