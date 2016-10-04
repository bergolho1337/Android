package com.hfad.starbuzz;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by lucas on 08/08/16.
 */
public class DrinkActivity extends AppCompatActivity {

    // Constante de identificacao do numero da bebida que o usuario clicou na DrinkCategoryActivity
    public static final String EXTRA_DRINKNO = "drinkNo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);

        // Capturar o ID da bebida que o usuario clicou na DrinkCategoryActivity
        int drinkNo = (Integer)getIntent().getExtras().get(EXTRA_DRINKNO);

        // Capturar o objeto Drink na posicao do ID, esse objeto possui as informacoes necessarias para popular as View do layout
        Drink drink = Drink.drinks[drinkNo];

        // Popular a foto do Drink
        ImageView photo = (ImageView)findViewById(R.id.photo);
        photo.setImageResource(drink.getImageResourceId());
        photo.setContentDescription(drink.getDescription());    // Torna o app mais acessivel para cegos ...

        // Popular o nome do Drink
        TextView nameView = (TextView)findViewById(R.id.name);
        nameView.setText(drink.getName());

        // Popular a descricao do Drink
        TextView descriptionView = (TextView)findViewById(R.id.description);
        descriptionView.setText(drink.getDescription());
    }
}
