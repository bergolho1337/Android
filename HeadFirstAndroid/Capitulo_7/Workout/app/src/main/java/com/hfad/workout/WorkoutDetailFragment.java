package com.hfad.workout;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class WorkoutDetailFragment extends Fragment {

    // ID do Workout que o usuario escolheu. Esta variavel eh setada pela Activity e sera utilizada depois pelo Fragment
    // para mostrar o texto correto da descricao do Workout
    private long workoutid;

    // Este metodo eh sempre chamado quando o Android precisa do layout do Fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Se tiver algo salvo no Bundle antes do usuario rotacionar a tela
        if (savedInstanceState != null) {
            workoutid = savedInstanceState.getLong("workoutId");
        }

        // Inflate the layout for this fragment (equivale ao setContentView() para Activity)
        // O container eh passado pela Activity que usa o Fragment
        // O Fragment eh adicionado no ViewGroup da Activity
        return inflater.inflate(R.layout.fragment_workout_detail, container, false);
    }

    // Implementar o metodo onStart() que sera chamado quando o Fragment estiver para ficar visivel para o usuario
    @Override
    public void onStart() {
        super.onStart();
        // Pegar referencia para View que contem o Fragment, visto que a classe Fragment nao consegue acessar dados de
        // outras View, como os TextView por exemplo
        View view = getView();
        if (view != null) {
            TextView nameView = (TextView) view.findViewById(R.id.name);
            // Qual eh o Workout que o usuario clicou ?
            Workout workout = Workout.workouts[(int)workoutid];
            nameView.setText(workout.getName());
            TextView descriptionView = (TextView) view.findViewById(R.id.description);
            descriptionView.setText(workout.getDescription());
        }
    }

    public void setWorkout (long id) {
        this.workoutid = id;
    }

    // Salvar o valor de 'workoutid' no Bundle para evitar perder o valor dessa variavel caso o usuario rotacione o celular

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // (chave, valor)
        savedInstanceState.putLong("workoutId",workoutid);
    }
}
