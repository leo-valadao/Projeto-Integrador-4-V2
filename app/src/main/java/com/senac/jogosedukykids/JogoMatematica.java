package com.senac.jogosedukykids;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class JogoMatematica extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    // Objetos:
    // Informações e Dificuldade:
    private TextView tV_numPontos;
    private TextView tV_numVidas;
    private Spinner s_dificuldade;

    // Imagem e Equação:
    private ImageView iV_meme;
    private TextView tV_numero1;
    private TextView tV_operador;
    private TextView tV_numero2;

    // Opções:
    private ImageView iV_btn_opcao1;
    private TextView tV_btn_opcao1;
    private ImageView iV_btn_opcao2;
    private TextView tV_btn_opcao2;
    private ImageView iV_btn_opcao3;
    private TextView tV_btn_opcao3;
    private ImageView iV_btn_opcao4;
    private TextView tV_btn_opcao4;

    // Música:
    private MediaPlayer mediaPlayer;

    // Variáveis:
    private Integer pontos = 0;
    private Integer botaoCorreto = 0;
    private String memeAtual = "netrual";
    private static final String[] dificuladades = {"Fácil", "Médio", "Difícil"};
    private static final DecimalFormat df = new DecimalFormat("0.00");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Remove a Barra de Título do Aplicativo:
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_jogo_matematica);

        // Inicializando os Objetos:
        this.tV_numPontos = findViewById(R.id.tV_numPontos);
        this.tV_numVidas = findViewById(R.id.tV_numVidas);
        this.s_dificuldade = findViewById(R.id.s_dificuldade);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, dificuladades);
        this.s_dificuldade.setAdapter(adapter);
        this.s_dificuldade.setSelection(0);
        this.s_dificuldade.setOnItemSelectedListener(this);

        this.iV_meme = findViewById(R.id.iV_meme);

        this.tV_numero1 = findViewById(R.id.tV_numero1);
        this.tV_operador = findViewById(R.id.tV_operador);
        this.tV_numero2 = findViewById(R.id.tV_numero2);

        this.iV_btn_opcao1 = findViewById(R.id.iV_btn_opcao1);
        this.tV_btn_opcao1 = findViewById(R.id.tV_btn_opcao1);
        this.iV_btn_opcao1.setOnClickListener(this);

        this.iV_btn_opcao2 = findViewById(R.id.iV_btn_opcao2);
        this.tV_btn_opcao2 = findViewById(R.id.tV_btn_opcao2);
        this.iV_btn_opcao2.setOnClickListener(this);
        
        this.iV_btn_opcao3 = findViewById(R.id.iV_btn_opcao3);
        this.tV_btn_opcao3 = findViewById(R.id.tV_btn_opcao3);
        this.iV_btn_opcao3.setOnClickListener(this);
        
        this.iV_btn_opcao4 = findViewById(R.id.iV_btn_opcao4);
        this.tV_btn_opcao4 = findViewById(R.id.tV_btn_opcao4);
        this.iV_btn_opcao4.setOnClickListener(this);
        
        df.setRoundingMode(RoundingMode.DOWN);

        // Coloca os Pontos Com o Valor Inicial da Variável "Pontos":
        this.tV_numPontos.setText(String.valueOf(this.pontos));

        // Inicializa o Desafio:
        iniciarDesafio();

        definirMeme();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        iniciarDesafio();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View view) {
        String dificuldade = this.s_dificuldade.getSelectedItem().toString();

        if (this.botaoCorreto == 1 && view == this.iV_btn_opcao1) {
            mudarPontuacao(dificuldade, true);
            iniciarDesafio();
        } else if (this.botaoCorreto == 2 && view == this.iV_btn_opcao2) {
            mudarPontuacao(dificuldade, true);
            iniciarDesafio();
        } else if (this.botaoCorreto == 3 && view == this.iV_btn_opcao3) {
            mudarPontuacao(dificuldade, true);
            iniciarDesafio();
        } else if (this.botaoCorreto == 4 && view == this.iV_btn_opcao4) {
            mudarPontuacao(dificuldade, true);
            iniciarDesafio();
        } else {
            mudarPontuacao(dificuldade, false);
        }
    }

    // Métodos Principais:

    public void iniciarDesafio() {
        // Reinicializa a Variável de Botão Correto:
        this.botaoCorreto = 0;

        // Coleta a Dificuldade em Uma Variável:
        String dificuldade = this.s_dificuldade.getSelectedItem().toString();

        // Inicializa as Variáveis de Minímo e Máximo:
        int minimo = 1;
        int maximo = 10;

        // Define o Valor das Variáveis de Minímo e Máximo:
        if (dificuldade.equals("Difícil")) {
            maximo = 100;
        }

        // Escolhe um Operador Aleatório de Acordo Com a Dificuldade:
        definirOperadorAleatorio(dificuldade);

        // Define o Valor Aleatório Para o 1º e 2º Numeros:
        definirNumerosEquacao(this.tV_operador.getText().toString(), minimo, maximo);

        // Define o Botão Correto:
        definirNumerosBotoes(minimo, maximo);
    }

    public void definirOperadorAleatorio(String dificuldade) {
        List<String> operadores = new ArrayList<>();

        if (dificuldade.equals("Difícil")) {
            operadores.add("+");
            operadores.add("-");
            operadores.add("*");
            operadores.add("÷");
        } else {
            operadores.add("+");
            operadores.add("-");
        }

        this.tV_operador.setText(operadores.get(gerarNumeroAleatorio(0,operadores.size())));
    }

    public void definirNumerosEquacao(String operador, Integer minimo, Integer maximo) {
        if (operador.equals("-") || operador.equals("÷")) {
            this.tV_numero1.setText(String.valueOf(gerarNumeroAleatorio(minimo, maximo)));
            this.tV_numero2.setText(String.valueOf(gerarNumeroAleatorio(minimo, Integer.valueOf(tV_numero1.getText().toString()))));
        } else {
            this.tV_numero1.setText(String.valueOf(gerarNumeroAleatorio(minimo, maximo)));
            this.tV_numero2.setText(String.valueOf(gerarNumeroAleatorio(minimo, maximo)));
        }
    }

    public void definirNumerosBotoes(Integer minimo, Integer maximo) {
        HashSet<Double> numerosOpcoes = new HashSet<>();

        Double numero1 = Double.valueOf(this.tV_numero1.getText().toString());
        Double numero2 = Double.valueOf(this.tV_numero2.getText().toString());
        Double resultado = calcular(numero1, this.tV_operador.getText().toString(), numero2);

        while (numerosOpcoes.size() != 3) {
            Double numeroAleatorio = Double.valueOf(gerarNumeroAleatorio(minimo, maximo));
            if (!numeroAleatorio.equals(resultado)) {
                if (this.tV_operador.getText().toString().equals("÷")) {
                    numerosOpcoes.add(numeroAleatorio/gerarNumeroAleatorio(1,11));
                } else if (this.tV_operador.getText().toString().equals("*")) {
                    numerosOpcoes.add(numeroAleatorio*gerarNumeroAleatorio(minimo,maximo));
                } else {
                    numerosOpcoes.add(numeroAleatorio);
                }
            }
        }

        List<Double> listaNumerosOpcoes = new ArrayList<>(numerosOpcoes);

        this.botaoCorreto = gerarNumeroAleatorio(1,5);

        switch (botaoCorreto) {
            case 1:
                this.botaoCorreto = 1;
                this.tV_btn_opcao1.setText(String.valueOf(df.format(calcular(numero1, this.tV_operador.getText().toString(), numero2))));
                this.tV_btn_opcao2.setText(String.valueOf(df.format(listaNumerosOpcoes.get(0))));
                this.tV_btn_opcao3.setText(String.valueOf(df.format(listaNumerosOpcoes.get(1))));
                this.tV_btn_opcao4.setText(String.valueOf(df.format(listaNumerosOpcoes.get(2))));
                break;
            case 2:
                this.botaoCorreto = 2;
                this.tV_btn_opcao1.setText(String.valueOf(df.format(listaNumerosOpcoes.get(0))));
                this.tV_btn_opcao2.setText(String.valueOf(df.format(calcular(numero1, this.tV_operador.getText().toString(), numero2))));
                this.tV_btn_opcao3.setText(String.valueOf(df.format(listaNumerosOpcoes.get(1))));
                this.tV_btn_opcao4.setText(String.valueOf(df.format(listaNumerosOpcoes.get(2))));
                break;
            case 3:
                this.botaoCorreto = 3;
                this.tV_btn_opcao1.setText(String.valueOf(df.format(listaNumerosOpcoes.get(0))));
                this.tV_btn_opcao2.setText(String.valueOf(df.format(listaNumerosOpcoes.get(1))));
                this.tV_btn_opcao3.setText(String.valueOf(df.format(calcular(numero1, this.tV_operador.getText().toString(), numero2))));
                this.tV_btn_opcao4.setText(String.valueOf(df.format(listaNumerosOpcoes.get(2))));
                break;
            case 4:
                this.botaoCorreto = 4;
                this.tV_btn_opcao1.setText(String.valueOf(df.format(listaNumerosOpcoes.get(0))));
                this.tV_btn_opcao2.setText(String.valueOf(df.format(listaNumerosOpcoes.get(1))));
                this.tV_btn_opcao3.setText(String.valueOf(df.format(listaNumerosOpcoes.get(2))));
                this.tV_btn_opcao4.setText(String.valueOf(df.format(calcular(numero1, this.tV_operador.getText().toString(), numero2))));
                break;
        }
    }

    public void mudarPontuacao(String dificuldade, Boolean acertou) {
        int pontosGanhos = 0;

        switch (dificuldade) {
            case "Difícil":
                pontosGanhos = 10;
                break;
            case "Médio":
                pontosGanhos = 5;
                break;
            case "Fácil":
                pontosGanhos = 1;
                break;
        }

        if (acertou) {
            this.pontos += pontosGanhos;
        } else {
            this.pontos -= pontosGanhos;
        }

        this.tV_numPontos.setText(String.valueOf(this.pontos));

        definirMeme();
    }

    public void definirMeme() {

        // Define o Meme e a Música
        if (this.pontos == 0 && !memeAtual.equals("neutral")) {
            memeAtual = "neutral";
            if (this.mediaPlayer != null) {
                this.mediaPlayer.stop();
            }
            this.iV_meme.setImageResource(R.drawable.neutral);
            this.mediaPlayer = MediaPlayer.create(this, R.raw.songneutral);
            this.mediaPlayer.start();
        } else if (estaEntre(0, this.pontos, 10) && !memeAtual.equals("good1")) {
            memeAtual = "good1";
            if (this.mediaPlayer != null) {
                this.mediaPlayer.stop();
            }
            this.iV_meme.setImageResource(R.drawable.good1);
            this.mediaPlayer = MediaPlayer.create(this, R.raw.songgood1);
            this.mediaPlayer.start();
        } else if (estaEntre(10, this.pontos, 20) && !memeAtual.equals("good2")) {
            memeAtual = "good2";
            if (this.mediaPlayer != null) {
                this.mediaPlayer.stop();
            }
            this.iV_meme.setImageResource(R.drawable.good2);
            this.mediaPlayer = MediaPlayer.create(this, R.raw.songgood2);
            this.mediaPlayer.start();
        } else if (estaEntre(20, this.pontos, 30) && !memeAtual.equals("good3")) {
            memeAtual = "good3";
            if (this.mediaPlayer != null) {
                this.mediaPlayer.stop();
            }
            this.iV_meme.setImageResource(R.drawable.good3);
            this.mediaPlayer = MediaPlayer.create(this, R.raw.songgood3);
            this.mediaPlayer.start();
        } else if (estaEntre(30, this.pontos, 40) && !memeAtual.equals("good4")) {
            memeAtual = "good4";
            if (this.mediaPlayer != null) {
                this.mediaPlayer.stop();
            }
            this.iV_meme.setImageResource(R.drawable.good4);
            this.mediaPlayer = MediaPlayer.create(this, R.raw.songgood4);
            this.mediaPlayer.start();
        } else if (estaEntre(40, this.pontos, 50) && !memeAtual.equals("good5")) {
            memeAtual = "good5";
            if (this.mediaPlayer != null) {
                this.mediaPlayer.stop();
            }
            this.iV_meme.setImageResource(R.drawable.good5);
            this.mediaPlayer = MediaPlayer.create(this, R.raw.songgood5);
            this.mediaPlayer.start();
        } else if (estaEntre(50, this.pontos, 60) && !memeAtual.equals("good6")) {
            memeAtual = "good6";
            if (this.mediaPlayer != null) {
                this.mediaPlayer.stop();
            }
            this.iV_meme.setImageResource(R.drawable.good6);
            this.mediaPlayer = MediaPlayer.create(this, R.raw.songgood6);
            this.mediaPlayer.start();
        } else if (this.pontos > 60 && !memeAtual.equals("good7")) {
            memeAtual = "good7";
            if (this.mediaPlayer != null) {
                this.mediaPlayer.stop();
            }
            iV_meme.setImageResource(R.drawable.good7);
            this.mediaPlayer = MediaPlayer.create(this, R.raw.songgood7);
            this.mediaPlayer.start();
        }
    }

    // Métodos Utilitários:
    public Integer gerarNumeroAleatorio(Integer minimo, Integer maximo) {
        return (int) ((Math.random() * (maximo - minimo)) + minimo);
    }

    public Double calcular(Double numero1, String operador, Double numero2) {
        double resultado = 0.0;

        switch (operador) {
            case "+":
                resultado = numero1 + numero2;
                break;
            case "-":
                resultado = numero1 - numero2;
                break;
            case "*":
                resultado = numero1 * numero2;
                break;
            case "÷":
                resultado = numero1 / numero2;
                break;
        }

        return resultado;
    }

    public static boolean estaEntre(int menor, int x, int maior) {
        return menor < x && x <= maior;
    }
}