package com.senac.jogosedukykids;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn_jogoMatematica;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_jogoMatematica = findViewById(R.id.btn_jogoMatematica);
    }

    @Override
    public void onClick(View view) {
        if (view == btn_jogoMatematica) {
            Intent telaJogoMatematica = new Intent(this, JogoMatematica.class);
            startActivity(telaJogoMatematica);
        }
    }
}