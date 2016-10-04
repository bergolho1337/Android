package com.hfad.starbuzz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class TopLevelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_level);

        // Criar um Listener de cliques para os objetos da ListView (tem que fazer isso, pois a ListView nao eh um Button)
        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            // Deve-se implementar esse metodo
            @Override
            public void onItemClick(AdapterView<?> listView, View itemView, int position, long id) {
                // Se selecionar o item Drinks mudar para a Activity DrinkCategoryActivity
                if (position == 0) {
                    Intent intent = new Intent(TopLevelActivity.this,DrinkCategoryActivity.class);
                    startActivity(intent);
                }
            }
        };

        // Setar o Listener no ListView, senao os itens do ListView nao vao responder a cliques
        ListView listView = (ListView) findViewById(R.id.list_options);
        listView.setOnItemClickListener(itemClickListener);
    }
}
