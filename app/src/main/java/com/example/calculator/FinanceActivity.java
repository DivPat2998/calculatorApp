package com.example.calculator;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


public class FinanceActivity extends Fragment {
    View rootView;
    EditText baseCurrency;
    TextView convertedCurrrency;





    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.financialcalc,container,false);

        baseCurrency = rootView.findViewById(R.id.editText);
         convertedCurrrency = rootView.findViewById(R.id.Result);

        return rootView;
    }
}
