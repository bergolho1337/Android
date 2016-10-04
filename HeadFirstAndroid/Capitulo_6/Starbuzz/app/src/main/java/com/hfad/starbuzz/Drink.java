package com.hfad.starbuzz;

/**
 * Created by lucas on 05/08/16.
 */
public class Drink {
    // Cada bebida possui um nome, uma descricao e uma referencia para o ID da imagem
    private String name;
    private String description;
    private int imageResourceId;

    // Temos um array de Drinks dentro da classe, que sao as bebidas disponiveis. Temos somente 3 neste exemplo.
    public static final Drink[] drinks = {
            new Drink("Latte","A couple of espresso shots with steamed milk",R.mipmap.latte),
            new Drink("Cappuccino","Espresso, hot milk and a steamed milk foam",R.mipmap.cappuccino),
            new Drink("Filter","Highest quality beans roasted and brewed fresh",R.mipmap.filter)
    };

    // Construtor de um Drink
    public Drink (String name, String description, int imageResourceId) {
        this.name = name;
        this.description = description;
        this.imageResourceId = imageResourceId;
    }

    public String getName () {
        return name;
    }

    public String getDescription () {
        return description;
    }

    public int getImageResourceId () {
        return imageResourceId;
    }

    // A representacao de um Drink eh seu nome

    @Override
    public String toString() {
        return this.name;
    }
}
