package com.example.findbrew;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ResultActivity extends AppCompatActivity {

    private ArrayList<Brew> brews;
    private RecyclerView recyclerView_brew;
    private TextView textView_result_count;

    private TextInputLayout textInputLayout_filter_beer;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        textInputLayout_filter_beer = findViewById(R.id.textInputLayout_filter_beer);
        textView_result_count = findViewById(R.id.textView_result_count);
        // look up the recycler view in the activity xml
        recyclerView_brew = findViewById(R.id.recyclerView_brew);
        brews = new ArrayList<>();

        Intent intent = getIntent();
        JSONArray brewsArray;

        // for each JSONObject, create a Brew object and add to the list
        try {
            brewsArray = new JSONArray(intent.getStringExtra("brew"));

            for (int i = 0; i < brewsArray.length(); i++){
                JSONObject brewObject = brewsArray.getJSONObject(i);
                String food_pairings = "";
                JSONArray pairings_JSON = brewObject.getJSONArray("food_pairing");
                food_pairings = food_pairings.concat(pairings_JSON.getString(0));
                for (int j = 1; j < pairings_JSON.length(); j++){
                    food_pairings = food_pairings.concat(", " + pairings_JSON.getString(j));
                }
                Brew brew = new Brew(brewObject.getString("name"),
                        brewObject.getString("image_url"),
                        brewObject.getString("description"),
                        brewObject.getString("abv"),
                        brewObject.getString("first_brewed"),
                        food_pairings,
                        brewObject.getString("brewers_tips"));
                // add it to the arraylist
                brews.add(brew);
            }

            // create brew adapter to pass in the data
            BrewAdapter adapter = new BrewAdapter(brews);
            recyclerView_brew.setAdapter(adapter);
            recyclerView_brew.setLayoutManager(new LinearLayoutManager(this));
            recyclerView_brew.setHasFixedSize(true);
            RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(
                    this, DividerItemDecoration.VERTICAL);
            recyclerView_brew.addItemDecoration(itemDecoration);

            // set the count message
            int resultCount = adapter.getItemCount();
            String count_text = "We found " + resultCount + " results.";
            textView_result_count.setText(count_text);

            // brews filter on input text change
            textInputLayout_filter_beer.getEditText().addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
                @Override
                public void afterTextChanged(Editable editable) {
                    ArrayList<Brew> temp = new ArrayList<>();
                    for (Brew b: brews){
                        // case insensitive comparison
                        if (b.getName().toLowerCase().contains(editable.toString().toLowerCase())){
                            temp.add(b);
                        }
                    }
                    adapter.updateBrews(temp);

                    // re-set the count message
                    int resultCount = adapter.getItemCount();
                    String count_text = "We found " + resultCount + " results.";
                    textView_result_count.setText(count_text);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
