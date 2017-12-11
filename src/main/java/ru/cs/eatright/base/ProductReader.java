package ru.cs.eatright.base;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ProductReader {
    private final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public Product readProduct (String file) throws FileNotFoundException {
        Scanner input = new Scanner(new File(file));
        StringBuilder jsoninput = new StringBuilder();
        while (input.hasNextLine()) {
            jsoninput.append(input.nextLine());
        }
        return GSON.fromJson(jsoninput.toString(), Product.class);
    }
}



