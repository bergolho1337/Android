package com.hfad.workout;


import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * O Stopwatch agora passou a ser um Fragment ao inves de uma Activity, dessa forma ele pode ser reutilizado.
 */
public class StopwatchFragment extends Fragment {

    // Numero de segundos mostrado no visor
    private int seconds = 0;

    // Variaveis booleanas que controlam o estado do relogio
    private boolean running;            // Relogio esta sendo executado ?
    private boolean wasRunning;         // Relogio estava em execucao antes de ser parado ?


    public void OnCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Restora o estado das variaveis caso o aparelho seja virado
        if (savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
            if (wasRunning) {
                running = true;
            }
        }
    }



    public StopwatchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Captura o layout que o fragment se encontra
        View layout = inflater.inflate(R.layout.fragment_stopwatch, container, false);
        // Comeca o a executar o relogio passando uma referencia para o layout
        runTimer(layout);
        return layout;
    }

    @Override
    public void onPause () {
        // Se o Fragment pausou grava se o relogio estava rodando e salva o valor em 'wasRunning'
        super.onPause();
        wasRunning = running;
        running = false;
    }

    @Override
    public void onResume () {
        // Reativando o relogio
        super.onResume();
        if (wasRunning) {
            running = true;
        }
    }

    @Override
    public void onSaveInstanceState (Bundle savedInstanceState) {
        savedInstanceState.putInt("seconds",seconds);
        savedInstanceState.putBoolean("running",running);
        savedInstanceState.putBoolean("wasRunning",wasRunning);
    }

    // Usuario clicou em Start
    public void OnClickStart (View view) {
        running  = true;
    }

    // Usuario clicou em Stop
    public void OnClickStop (View view) {
        running  = false;
    }

    // Usuario clicou em Reset
    public void OnClickReset (View view) {
        running  = false;
        seconds = 0;
    }

    private void runTimer (View view) {
        final TextView timeView = (TextView)view.findViewById(R.id.time_view);
        // Colocar o codigo em um Handler significa que podemos rodar o codigo em uma thread separada em background
        final Handler handler = new Handler();
        handler.post(new Runnable() {

            // De tempos em tempos executar essa funcao
            @Override
            public void run() {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600)/60;
                int secs = seconds % 60;
                String time = String.format("%d:%02d:%02d",hours,minutes,secs);
                timeView.setText(time);
                // Se o relogio esta funcionando incrementar o numero de segundos
                if (running)
                    seconds++;
                // Executa a funcao a cada 1 segundo
                handler.postDelayed(this,1000);

            }
        });
    }
}
