package com.hfad.beeradvicer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

// Toda Activity precisa implementar o metodo onCreate().
// Este metodo realiza tarefas de configuracao basica da Activity, como definir qual layout a Activity vai usar
// Isso eh feito com o metodo setContentView()
public class FindBeerActivity extends AppCompatActivity {

    // Criar um objeto do tipo BeerExpert
    private BeerExpert beerExpert = new BeerExpert();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_beer);
    }

    // Implementar o metodo onClickFindBeer()
    public void onClickFindBeer (View view) {

        // Capturar a View de cada componente do .xml com o metodo findViewById(<id_do_componente>)
        // A variavel R eh de uma classe especial que eh gerada pela IDE e que simplesmente fica monitorando os
        // recursos usados no app.
        TextView brands = (TextView)findViewById(R.id.brands);
        Spinner color = (Spinner)findViewById(R.id.color);

        // Pegar qual eh o tipo de cerveja que o usuario selecionou e converter para String
        String beerType = String.valueOf(color.getSelectedItem());

        // Captura a lista de cervejas daquele tipo
        List<String> beerList = beerExpert.getBrands(beerType);

        // Montar uma String com os dados da lista retornada
        StringBuilder brandsFormatted = new StringBuilder();
        // Mostrar cada String em uma linha
        for (String beer : beerList) {
            brandsFormatted.append(beer).append('\n');
        }
        // Mostrar as cervejas no textView embaixo do Button
        brands.setText(brandsFormatted);
    }
}
