package com.hfad.stopwatch;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class StopwatchActivity extends AppCompatActivity {

    private int seconds = 0;            // Numero de segundos que o relogio se encontra ativo
    private boolean running = false;    // Estado do relogio. (true = ativo || false = parado)
    private boolean wasRunning;         // Estado anterior do relogio antes de um onStop()

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);
        // Verifica se jah existe alguma execucao antiga da Activity, de tal modo que recuperamos os valores anteriores
        if (savedInstanceState != null)
        {
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("was_running");
        }
        // Comecar o loop do relogio
        runTimer();
    }

    // Funcao para parar a contagem do tempo se a Activity estiver minimizada
    @Override
    protected void onPause() {
        super.onPause();
        // Salva o estado do relogio quando onPause() eh chamado
        wasRunning = running;
        running = false;
    }

    // Funcao para continuar a contagem do relogio
    @Override
    protected void onResume() {
        super.onResume();
        // Se o relogio estava rodando antes fazer ele voltar a rodar de novo
        if (wasRunning)
            running = true;
    }

    // Funcao usada para salvar o estado atual das variaveis antes da Activity ser destruida
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("seconds",seconds);
        savedInstanceState.putBoolean("running",running);
        savedInstanceState.putBoolean("was_running",wasRunning);
    }

    public void onClickStart (View view) {
        running = true;
    }

    public void onClickStop (View view) {
        running = false;
    }

    public void onClickReset (View view) {
        running = false;
        seconds = 0;
    }

    // Funcao que atualiza o estado do relogio a cada segundo.
    // Essa funcao sempre deve ficar rodando no app.
    // Porem nao pode bloquear o fluxo de execucao da MainActivity
    // A ideia eh utilizar uma Thread, porem so a MainActivity pode controlar objetos desse tipo
    // Logo vamos utilizar um Handler, que eh uma classe que usada para realizar tarefas agendadas de tempos em tempos
    private void runTimer () {

        // Declarar essas variaveis como 'final' deixa elas visiveis para os metodos de classes interiores
        final TextView timeView = (TextView) findViewById(R.id.time_view);

        // Criar um objeto Handler para ficar sempre executando a funcao run do objeto Runnable
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                // Transforma o numero de segundos em horas, minutos e segundos
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;
                // Formata uma string no estilo de um relogio com os valores de tempo e coloca no TextView
                String time = String.format("%d:%02d:%02d",hours,minutes,secs);
                timeView.setText(time);

                // Se o relogio esta ativo incrementa o contador de segundos
                if (running)
                    seconds++;

                // Posta o codigo para ser rodado sempre apos 1s
                // Como esse comando esta dentro do comando run(), entao sempre ficara executando
                handler.postDelayed(this,1000);
            }
        });




    }


}
