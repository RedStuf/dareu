package com.phonezilla.dareu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.parse.Parse;
import com.phonezilla.dareu.schermen.MainActivity;


public class Beginscherm extends Activity {

    Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_begin);
        Parse.initialize(this, "jO1gEQOmHYCbpI9S05t2v4jfgAhnWglBTx4Tma8m", "nylyg1NjpI5NcW4bOz74xNebQbEEDF9OctbTj5qI");
        getActionBar().hide();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(context,MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 5000);
        
        
    }
}
