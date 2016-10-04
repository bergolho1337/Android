package com.hfad.workout;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * A simple {@link Fragment} subclass.
 */
// Assim como existe a ListActivity que eh uma Activity especialista em trabalhar com ListView, tambem existe
// a ListFragment que um Fragment especialista em trabalhar com ListView.
// Com isso, nao eh necessario criar um layout e tambem nao eh necessario criar um Listener para eventos de clique.
public class WorkoutListFragment extends ListFragment {

    // Criamos uma interface para realizar o minimo de comunicacao entre o Fragment e a Activity
    // No caso soh queremos que o Fragment sinalize a Activity de quando o usuario clicou em algum item do ListView
    static interface WorkoutListListener {
        void itemClicked(long id);
    };

    // Variavel da interface que vai ficar escutando o ListView e disparar a funcao itemClicked quando o usuario clicar em uma opcao
    private WorkoutListListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Popular o array com os nomes dos Workout para serem mostrados no ListView
        String [] names = new String[Workout.workouts.length];
        for (int i = 0; i < names.length; i++) {
            names[i] = Workout.workouts[i].getName();
        }
        // Linkar o array em um ArrayAdapter para ser acoplado no ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                inflater.getContext(),                // Usamos o LayoutInflator para capturar a View que contem o Fragment
                android.R.layout.simple_list_item_1,  // Layout dos elementos do LsitView
                names);                               // Vetor de Strings com os elementos a serem populados
        setListAdapter(adapter);

        return super.onCreateView(inflater,container,savedInstanceState);
    }

    // Essa funcao eh sempre chamada quando o Fragment eh acoplado a uma Activity pelo Android
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.listener = (WorkoutListListener)activity;  // Atribui a ligacao do Listener com a Activity
    }

    // Como estamos em uma ListView temos acesso ao metodo onListItemClick(), vamos dentro dessa funcao desviar para o metodo da nossa
    // interface itemClicked() passando o ID do item clicado.
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        if (listener != null) {
            listener.itemClicked(id);
        }
    }
}
