package ru.cs.eatright.knowledgebase;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ProductReader {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static Product readProduct (File file) throws FileNotFoundException {
        Scanner input = new Scanner(file);
        StringBuilder jsoninput = new StringBuilder();
        while (input.hasNextLine()) {
            jsoninput.append(input.nextLine());
        }
        return GSON.fromJson(jsoninput.toString(), Product.class);
    }
}



