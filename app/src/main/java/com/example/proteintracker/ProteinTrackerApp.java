package com.example.proteintracker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class ProteinTrackerApp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protein_tracker_app);

        Button btnSetting = (Button) findViewById(R.id.btnSetting);
        btnSetting.setOnClickListener(myBtnSettingClick);

        Button resetButton = (Button) findViewById(R.id.btnReset);
        resetButton.setOnClickListener(resetButtonListener);

        Button resetButton2 = (Button) findViewById(R.id.btnReset2);
        resetButton2.setOnClickListener(resetButtonListener2);

        Button customButton = (Button) findViewById(R.id.btnCustomDialog);
        customButton.setOnClickListener(customButtonListener);

        SharedPreferences prefs = ProteinTrackerApp.this.getSharedPreferences("prefs_file", MODE_PRIVATE);
        String statusLogin = prefs.getString("isLogin", null);
        Button btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(myBtnLoginClick);

        if (statusLogin != null) {
            btnLogin.setText("Logout");
        } else {
            btnLogin.setText("Login");
        }
    }

    ProgressDialog progressDialog;

    private View.OnClickListener myBtnLoginClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            SharedPreferences prefs = ProteinTrackerApp.this.getSharedPreferences("prefs_file", MODE_PRIVATE);

            String statusLogin = prefs.getString("isLogin", null);
            SharedPreferences.Editor edit = prefs.edit();

            Button btnLogin = (Button) findViewById(R.id.btnLogin);
            if (statusLogin != null) {
                edit.putString("isLogin", null);
                btnLogin.setText("Login");
            } else {
                edit.putString("isLogin", "Admin");
                btnLogin.setText("Logout");
            }
            edit.commit();
        }
    };

    private View.OnClickListener myBtnSettingClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(ProteinTrackerApp.this, SettingProteinTracker.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener resetButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(ProteinTrackerApp.this);

            builder.setMessage("Apakah anda yakin untuk mereset nilai protein?")
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(ProteinTrackerApp.this, "Tidak jadi reset", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(ProteinTrackerApp.this, "Melakukan RESET !!", Toast.LENGTH_SHORT).show();
                        }
                    });

            AlertDialog dialog = builder.create();
            dialog.show();
        }
    };

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            progressDialog.dismiss();
        }
    };

    private View.OnClickListener resetButtonListener2 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            progressDialog = new ProgressDialog(ProteinTrackerApp.this);
            progressDialog.setMessage("Melakukan sesuatu ...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

            Thread thread = new Thread(new Runnable() {
                public void run() {
                    try {
                        Thread.sleep(3000);
                        handler.sendEmptyMessage(0);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
            progressDialog.show();
        }
    };

    private View.OnClickListener customButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final Dialog dialog = new Dialog(ProteinTrackerApp.this);
            dialog.setContentView(R.layout.custom_dialog);
            dialog.setTitle("Custom Dialog");

            Button btnDialog = (Button) dialog.findViewById(R.id.btnTombol);
            btnDialog.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    dialog.dismiss();
                }
            });

            dialog.show();
        }
    };
}