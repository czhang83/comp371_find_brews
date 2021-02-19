package com.example.findbrew;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;
import cz.msebera.android.httpclient.Header;

public class SearchActivity extends AppCompatActivity {

    private static AsyncHttpClient client = new AsyncHttpClient();

    private TextInputLayout textInputLayout_name;
    private TextInputLayout textInputLayout_from;
    private TextInputLayout textInputLayout_to;
    private Switch switch_high_point;
    private Button button_show_result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        button_show_result = findViewById(R.id.button_show_result);
        textInputLayout_name = findViewById(R.id.textInputLayout_beer_name);
        textInputLayout_from = findViewById(R.id.textInputLayout_from);
        textInputLayout_to = findViewById(R.id.textInputLayout_to);
        switch_high_point = findViewById(R.id.switch_high_point);

        button_show_result.setOnClickListener(v -> launchNextActivity(v));
    }

    // get info from API
    public void launchNextActivity(View view){
        // get user input
        String name = textInputLayout_name.getEditText().getText().toString();
        String from = textInputLayout_from.getEditText().getText().toString();
        String to = textInputLayout_to.getEditText().getText().toString();
        boolean high_point = switch_high_point.isChecked();
        // check date format, date can be empty
        if (datesAreValid(from, to) || (datesAreValid(from) && to.equals("")) ||
                (datesAreValid(to) && from.equals("")) || ((from.equals("") && to.equals("")))){
            // generate api url according to input
            from = from.replaceAll("/", "-");
            to = to.replaceAll("/", "-");
            String api_url = "https://api.punkapi.com/v2/beers?";
            // can't have & right after ?
            if (high_point){ //abv_gt=3.9 to include 4 and above
                api_url = api_url.concat("abv_gt=3.9");
            } else {
                api_url = api_url.concat("abv_lt=4.0");
            }
            if (!name.equals("")){
                api_url = api_url.concat("&beer_name=" + name);
            }
            if (!from.equals("")){
                api_url = api_url.concat("&brewed_after=" + from);
            }
            if (!to.equals("")){
                api_url = api_url.concat("&brewed_before=" + to);
            }

            Log.d("api url", api_url);
            // send a get request to the api url
            client.get(api_url, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    // when get a 200 status code
                    Log.d("api response", new String(responseBody));

                    Intent intent = new Intent(SearchActivity.this, ResultActivity.class);
                    // send the response in string form
                    intent.putExtra("brew", new String(responseBody));
                    startActivity(intent);
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    // when get 400
                    //Log.e("api error", new String(responseBody));
                    Toast toast = Toast.makeText(SearchActivity.this, R.string.api_fail, Toast.LENGTH_SHORT);
                    toast.show();
                }
            });
        } else {
            Toast toast = Toast.makeText(this, R.string.invalid_date, Toast.LENGTH_SHORT);
            toast.show();
        }

    }

    // single digit m/yyyy would be valid - api accept too
    // check from is before to
    private Boolean datesAreValid(String from, String to){
        DateFormat formatter = new SimpleDateFormat("MM/yyyy");
        formatter.setLenient(false);
        try {
            Date from_date = formatter.parse(from);
            Date to_date = formatter.parse(to);
            if (to_date.before(from_date)){
                return false;
            }
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    // single digit m/yyyy would be valid
    private Boolean datesAreValid(String from){
        DateFormat formatter = new SimpleDateFormat("MM/yyyy");
        formatter.setLenient(false);
        try {
            Date from_date = formatter.parse(from);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }
}
