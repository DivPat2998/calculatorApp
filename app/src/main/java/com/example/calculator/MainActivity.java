package com.example.calculator;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends Fragment {
    public static final String TAG = "Calc";
    ArrayList<String> outputHistory = new ArrayList<>();
    ArrayAdapter adapter;
    double firstNum, secondNum;
    String operand;
    TextView output;
    String input = "";
    NumberFormat numberFormat;
    boolean isFirstNumTheResult = false, isOperand2 = false;
    NavigationView navigationView;
    DrawerLayout drawerLayout;

    View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_main, container, false);
        init();
        rootView.findViewById(R.id.bt0).setOnClickListener(numberClick);
        rootView.findViewById(R.id.bt1).setOnClickListener(numberClick);
        rootView.findViewById(R.id.bt2).setOnClickListener(numberClick);
        rootView.findViewById(R.id.bt3).setOnClickListener(numberClick);
        rootView.findViewById(R.id.bt4).setOnClickListener(numberClick);
        rootView.findViewById(R.id.bt5).setOnClickListener(numberClick);
        rootView.findViewById(R.id.bt6).setOnClickListener(numberClick);
        rootView.findViewById(R.id.bt7).setOnClickListener(numberClick);
        rootView.findViewById(R.id.bt8).setOnClickListener(numberClick);
        rootView.findViewById(R.id.bt9).setOnClickListener(numberClick);
        rootView.findViewById(R.id.btdot).setOnClickListener(numberClick);

        rootView.findViewById(R.id.add).setOnClickListener(operandClick);
        rootView.findViewById(R.id.subtract).setOnClickListener(operandClick);
        rootView.findViewById(R.id.multiply).setOnClickListener(operandClick);
        rootView.findViewById(R.id.divide).setOnClickListener(operandClick);

        rootView.findViewById(R.id.TAN).setOnClickListener(operandClick2);
        rootView.findViewById(R.id.SIN).setOnClickListener(operandClick2);
        rootView.findViewById(R.id.COS).setOnClickListener(operandClick2);
        rootView.findViewById(R.id.SQRT).setOnClickListener(operandClick2);
        rootView.findViewById(R.id.POW).setOnClickListener(operandClick2);
        return rootView;
    }


    View.OnClickListener numberClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            numberClick(v);
        }
    };

    View.OnClickListener operandClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            operation1(v);
        }
    };

    View.OnClickListener operandClick2 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            operation2(v);
        }
    };

    private void init() {
        output = rootView.findViewById(R.id.textView);
        drawerLayout = rootView.findViewById(R.id.drawer);
        navigationView = rootView.findViewById(R.id.navigationView);
        numberFormat = NumberFormat.getNumberInstance(Locale.ENGLISH);
        rootView.findViewById(R.id.del).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (input.length() > 2) {
                    input = input.substring(0, input.length() - 2);
                    output.setText(input);
                } else {
                    input = "";
                    output.setText(null);
                }
            }
        });

        rootView.findViewById(R.id.del).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                firstNum = 0;
                secondNum = 0;
                isFirstNumTheResult = false;
                input = "";
                operand = "";
                output.setText(null);
                outputHistory.clear();
                adapter.notifyDataSetChanged();
                return true;
            }

        });
        rootView.findViewById(R.id.equals).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOperand2) {
                    firstNum = Double.parseDouble(input.replace(",", ""));
                }
                secondNum = Double.parseDouble(input.replace(",", ""));

                double result = 0;
                switch (operand) {
                    case "+":
                        result = add(firstNum, secondNum);
                        break;

                    case "-":
                        result = minus(firstNum, secondNum);
                        break;

                    case "x":
                        result = product(firstNum, secondNum);
                        break;

                    case "÷":
                        result = quotient(firstNum, secondNum);
                        break;

                    case "√":
                        result = squareRoot(firstNum);
                        break;
                    case "^":
                        result = power(firstNum, secondNum);
                        break;
                    case "sin":
                        result = sin(firstNum);
                        break;
                    case "cos":
                        result = cos(firstNum);
                        break;
                    case "tan":
                        if (firstNum == 90) {
                            output.setText("∞");
                            return;
                        }
                        result = tan(firstNum);
                        break;
                }
                output.setText(result + "");
                outputHistory.add(numberFormat.format(secondNum) + " ");
                // outputHistory.add(result+" ");
                if (isOperand2) {
                    outputHistory.add(operand + " " + firstNum + " = " + numberFormat.format(result) + "");
                } else {
                    outputHistory.add(numberFormat.format(firstNum) + " " + operand + " " + numberFormat.format(secondNum) + " = " + numberFormat.format(result) + "");
                }
                adapter.notifyDataSetChanged();
                firstNum = result;
                isFirstNumTheResult = true;
            }
        });
        adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, outputHistory);
        ListView listView = rootView.findViewById(R.id.ListView);
        listView.setAdapter(adapter);
    }

    public void numberClick(View view) {
        if (!TextUtils.isEmpty(operand) && !isOperand2) {
            outputHistory.add(operand);
            adapter.notifyDataSetChanged();
        }

        Button number = (Button) view;
        input += number.getText();
        double numberD;
        if (number.getId() != R.id.btdot) {
            try {
                numberD = Double.parseDouble(input.replace(",", ""));
                input = numberFormat.format(numberD);
            } catch (NumberFormatException e) {
                Log.d(TAG, "Failed to parse number");
            }
        }
        if (isOperand2) {
            output.setText(operand + " " + input);
        } else {
            output.setText(input);
        }
    }

    public void operation2(View view) {
        input = "";
        Button operandB = (Button) view;
        operand = operandB.getText().toString();
        output.setText(operand);
        isOperand2 = true;
    }

    public void operation1(View view) {
        isOperand2 = false;
        if (input == null || TextUtils.isEmpty(input)) {
            return;
        }
        Button operandB = (Button) view;
        operand = operandB.getText().toString();
        output.setText(operand);
        if (isFirstNumTheResult) {
            secondNum = Double.parseDouble(input.replace(",", ""));
        } else {
            firstNum = Double.parseDouble(input.replace(",", ""));
            outputHistory.add(input);
            adapter.notifyDataSetChanged();
        }
        input = "";
    }

    private double add(double a, double b) {
        return a + b;
    }

    private double minus(double a, double b) {
        return a - b;
    }

    private double product(double a, double b) {
        return a * b;
    }

    private double quotient(double a, double b) {
        return a / b;
    }

    private double squareRoot(double a) {
        return Math.sqrt(a);
    }

    private double power(double a, double b) {

        return Math.pow(a, b);
    }

    private double sin(double a) {
        return Math.sin(Math.toRadians(a));
    }

    private double cos(double a) {
        return Math.cos(Math.toRadians(a));
    }

    private double tan(double a) {

        return Math.tan(Math.toRadians(a));
    }


    public void openDrawer(View view) {
        drawerLayout.openDrawer(Gravity.BOTTOM | Gravity.END);
    }
}
