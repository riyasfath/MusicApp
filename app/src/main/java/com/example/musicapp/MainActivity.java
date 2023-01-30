package com.example.musicapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PackageManagerCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    Button btn1;
    ListView listSD;
    ArrayList<String> listData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn1 = (Button) findViewById(R.id.btn1);
        listSD = (ListView) findViewById(R.id.listSD);
        listData = new ArrayList<>();

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String state = Environment.getExternalStorageState();

                Toast.makeText(getApplicationContext(),state,Toast.LENGTH_SHORT).show();

                if(Environment.MEDIA_MOUNTED.equals(state))
                {
                    if(Build.VERSION.SDK_INT >=23){
                        if(checkPermission()){
                            String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/";

                            File dir = new File(path);
                            if(dir.exists()){
                                File list [] = dir.listFiles();
                                for (int i=0;i<list.length;i++){
                                    listData.add(list[i].getName());
                                }
                                ArrayAdapter arrayAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1,listData);
                                listSD.setAdapter(arrayAdapter);
                            }
                        }
                        else
                        {
                            requestPermissions();

                        }
                    }
                }
            }
        });
    }

    private void requestPermissions() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)){
            Toast.makeText(getApplicationContext(),"Enable permisson externally",Toast.LENGTH_SHORT).show();
        }
        else{
            String permissionList [] ={
                    Manifest.permission.READ_EXTERNAL_STORAGE
            };
            ActivityCompat.requestPermissions(MainActivity.this,permissionList,100);
        }

    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if(result== PackageManager.PERMISSION_GRANTED){
            return true;
        }else
            return false;
    }
}