package com.hfad.workout;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

// Como existe uma ligacao entre a MainActivity e o WorkoutListFragment por uma interface eh necessario a MainActivity implementar os
// metodos da interface
public class MainActivity extends AppCompatActivity implements WorkoutListFragment.WorkoutListListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // Recebemos o ID do item clicado pelo usuario
    @Override
    public void itemClicked(long id) {

        // Checar se o layout possui o FrameLayout, se sim entao eh tablet, senao eh celular comum
        View fragmentContainer = findViewById(R.id.fragment_container);
        if (fragmentContainer != null) {
            WorkoutDetailFragment details = new WorkoutDetailFragment();
            // Inicializa uma transicao de Fragment
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            // Seta o ID que usuario clicou na variavel 'workoutid'
            details.setWorkout(id);
            // Troca o Fragment e adiciona ele no final da pilha de alteracoes
            ft.replace(R.id.fragment_container,details);
            ft.addToBackStack(null);
            // Configura como sera a transicao. No caso vai ter uma animacao Fade-in-out
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            // Comita a mudanca
            ft.commit();
        }
        // Se eh celular criar uma Intent que salva o id do workout clicado no extra e desvia para DetailActivity
        else {
            Intent intent = new Intent(this,DetailActivity.class);
            intent.putExtra(DetailActivity.EXTRA_WORKOUT_ID,(int)id);
            startActivity(intent);
        }
    }
}
