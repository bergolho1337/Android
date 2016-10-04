package com.hfad.workout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by lucas on 09/08/16.
 */
public class DetailActivity extends AppCompatActivity {

    // Constante que define a chave do 'id' do workout que o usuario selecionou
    public static final String EXTRA_WORKOUT_ID = "id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        // Captura o Fragment que esta dentro do layout dessa Activity
        WorkoutDetailFragment detail_frag = (WorkoutDetailFragment)getSupportFragmentManager().findFragmentById(R.id.detail_frag);
        // Captura o ID do workout clicado pelo usuario
        int workoutid = (int)getIntent().getExtras().get(EXTRA_WORKOUT_ID);
        // Atribui o ID do workout no Fragment
        detail_frag.setWorkout(workoutid);
    }
}
