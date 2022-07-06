package com.dallogfheir.slownik_scrabble;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button searchBtn = findViewById(R.id.findBtn);
        EditText input = findViewById(R.id.input);
        ImageView scrabbleLetter = findViewById(R.id.scrabbleLetter);

        scrabbleLetter.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                String text = input.getText().toString().toLowerCase();

                if (!text.equals("")) {
                    try {
                        Uri uri = Uri.parse("https://sjp.pl/" + URLEncoder.encode(text, "UTF-8"));
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    } catch (UnsupportedEncodingException ignored) {}
                }

                return true;
            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressBar progressBar = findViewById(R.id.progressBar);

                scrabbleLetter.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        makeRequest();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.setVisibility(View.INVISIBLE);
                                scrabbleLetter.setVisibility(View.VISIBLE);
                            }
                        });
                    }
                }).start();
            }
        });

        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = s.toString();
                searchBtn.setEnabled(!text.equals(""));
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        InputFilter onlyLettersFilter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    if (!Character.isAlphabetic(source.charAt(i))) {
                        return "";
                    }
                }

                return null;
            }
        };
        input.setFilters(new InputFilter[] {new InputFilter.AllCaps(), onlyLettersFilter});
    }

    private void makeRequest() {
        EditText input = findViewById(R.id.input);
        String text = input.getText().toString();

        try {
            URL url = new URL("http://www.pfs.org.pl/files/php/osps_funkcje3.php");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            HashMap<String, String> parameters = new HashMap<String, String>();
            parameters.put("s", "spr");
            parameters.put("slowo_arbiter2", text);

            conn.setDoOutput(true);
            DataOutputStream out = new DataOutputStream(conn.getOutputStream());
            ArrayList<String> paramsStrings = new ArrayList<String>();
            for (HashMap.Entry<String, String> entry : parameters.entrySet()) {
                String paramString = URLEncoder.encode(entry.getKey(), "UTF-8") + "=" + URLEncoder.encode(entry.getValue(), "UTF-8");
                paramsStrings.add(paramString);
            }
            String result = String.join("&", paramsStrings);

            out.writeBytes(result);
            out.flush();
            out.close();

            int status = conn.getResponseCode();
            if (status == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine = in.readLine();

                ImageView scrabbleLetter = findViewById(R.id.scrabbleLetter);
                if (inputLine.equals("1")) {
                    scrabbleLetter.setColorFilter(Color.argb(255, 42, 199, 68));
                } else {
                    scrabbleLetter.setColorFilter(Color.argb(255, 199, 42, 42));
                }
            } else {
                this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showError("Wystąpił błąd. Spróbuj ponownie później.");
                    }
                });
            }

            conn.disconnect();
        } catch (IOException e) {
            this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    showError("Wystąpił błąd. Sprawdź połączenie z internetem.");
                }
            });
        }
    }

    private void showError(String msg) {
        new AlertDialog.Builder(this).
                setTitle("Błąd").
                setMessage(msg).
                setIcon(android.R.drawable.ic_dialog_info).
                setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).
                show();
    }
}