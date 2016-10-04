package com.hfad.starbuzz;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by lucas on 05/08/16.
 */
// ListActivity eh um tipo de Activity que so contem uma ListView e ja possui um itemOnClickListener configurado
// Basta somente implementar o metodo onItemClick() para definir as acoes quando cada item da ListView for selecionada.
// Outra questao eh que uma ListActivity jah configura um layout, que so eh composto pela ListView
public class DrinkCategoryActivity extends ListActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Criar um ArrayAdapter para popular a ListView dessa Activity
        // ArrayAdapter -> precisa do tipo de dados que o array vai carregar, o contexto (normalmente a Activity atual).
        // Um arquivo de layout resource que diz como os itens da listView serao mostrados
        // E o array de dados.
        ArrayAdapter<Drink> listAdapter = new ArrayAdapter<Drink>(this,android.R.layout.simple_list_item_1,Drink.drinks);

        // Encaixa o Adapter no ListView
        ListView listView = getListView();
        listView.setAdapter(listAdapter);

        // Por detras dos panos o ArrayAdapter pega os elementos e converte para String usando o metodo toString() do tipo
        // de dados do array.


    }

    // Como DrinkCategoryActivity eh uma ListActivity basta implementar o metodo onListItemClick()
    protected void onListItemClick(ListView listView,
                                   View itemView,
                                   int position,
                                   long id) {
        Intent intent = new Intent(DrinkCategoryActivity.this,DrinkActivity.class);
        // Coloca o ID da bebida como valor a ser passado entre as Activity pela Intent (indice no array de Drinks)
        // A chave eh uma constante definida na classe DrinkActivity
        intent.putExtra(DrinkActivity.EXTRA_DRINKNO,(int)id);
        startActivity(intent);
    }
}
