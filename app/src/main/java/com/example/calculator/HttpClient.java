package com.example.calculator;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class HttpClient {
    static String link = "https://api.exchangeratesapi.io/latest";

    public static void currencies() {
        try {
            java.net.URL url = new URL(link);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(connection.getInputStream());

            while (in.read() != -1) {
                char c = (char) in.read();
                Log.d("Finance",c + "");

            }
            connection.disconnect();

        } catch (IOException e) {
            e.printStackTrace();

        }

    }
}
