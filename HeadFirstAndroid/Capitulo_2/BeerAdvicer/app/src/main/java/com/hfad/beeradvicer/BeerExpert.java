package com.hfad.beeradvicer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lucas on 02/08/16.
 */
public class BeerExpert {
    // Criar o metodo que a partir de uma String contendo o tipo de cerveja retorna as marcas daquele tipo em um
    // objeto do tipo ArrayList
    List<String> getBrands (String color) {
        // Cria o ArrayList para aramzenar a lista
        List<String> brands = new ArrayList<String>();
        // Adiciona as cervejas respectivas
        if (color.equals("amber")) {
            brands.add("Jack Amber");
            brands.add("Red Moose");
        }
        else {
            brands.add("Jail Pale Ale");
            brands.add("Gout Stout");
        }
        return brands;
    }

}
