package com.example.calculator;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;


public class FinanceActivity extends Fragment {
    View rootView;
    EditText baseCurrency;
    TextView convertedCurrrency;
    Spinner baseSp;
    Spinner base2;
    String base = "USD";
    String symbol = "USD";
    double multiplier = 1;
    FloatingActionButton btn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.financialcalc, container, false);

        baseCurrency = rootView.findViewById(R.id.editText);
        convertedCurrrency = rootView.findViewById(R.id.Result);
        baseCurrency.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)) {
                    convertedCurrrency.setText(null);
                    return;
                }

                double number = Double.parseDouble(s.toString());
                double result = number * multiplier;


                DecimalFormat format = new DecimalFormat("###,###,###.####");
                convertedCurrrency.setText(format.format(result));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });
        btn = rootView.findViewById(R.id.floatingActionButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String swap = base;
                base = symbol;
                symbol = swap;

                money();
                setSpinText(baseSp, base);
                setSpinText(base2, symbol);

            }
        });


        baseSp = rootView.findViewById(R.id.spinner2);
        baseSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                base = parent.getItemAtPosition(position).toString();
                money();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });


        base2 = rootView.findViewById(R.id.spinner);
        base2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                symbol = parent.getItemAtPosition(position).toString();
                money();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        return rootView;
    }


    public void money() {

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url = "https://api.exchangeratesapi.io/latest?base=" + base + "&symbols=" + symbol;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.d("Finance", response);

                        JSONObject conversion = null;
                        try {
                            conversion = new JSONObject(response);
                            JSONObject rates = conversion.getJSONObject("rates");
                            multiplier = rates.getDouble(symbol);

                            String input = baseCurrency.getText().toString();
                            if (TextUtils.isEmpty(input)) {
                                return;

                            }
                            double number = Double.parseDouble(input);
                            double result = number * multiplier;


                            DecimalFormat format = new DecimalFormat("###,###,###.####");
                            convertedCurrrency.setText(format.format(result));


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Finance", new String(error.networkResponse.data) + "");

            }
        });


// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    public void setSpinText(Spinner spin, String text) {
        for (int i = 0; i < spin.getAdapter().getCount(); i++) {
            if (spin.getAdapter().getItem(i).toString().contains(text)) {
                spin.setSelection(i);
            }
        }
    }
}
