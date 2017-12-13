package ru.cs.eatright.search;

import ru.cs.eatright.knowledgebase.Product;

import java.util.List;

public class ResponseCreator {
    private static final String emptyResponse = "Кажется ваш вопрос не относится к еде. Попробуйте еще раз?";
    private static final String generalInfo = "Продукты, упомянутые в вашем запросе имеют следующие характеристики (на 100 грамм): '%s'.";
    private static final String normalAverageCaloricity = "Средняя калорийность продуктов в вашем запросе '%f'cal.";
    private static final String hugeAverageCaloricity = "Калорийность продуктов в вашем запросе '%f'cal. Вы точно собираетесь это есть?!";

    public static String createResponse(List<ParsedQuery> parsedQueries) {
        if (parsedQueries.isEmpty()) return emptyResponse;

        String productsInfo = "";
        int productNum = 0;
        double commonCaloricity = 0;
        for (ParsedQuery parsedQuery: parsedQueries) {
            for (Product product: parsedQuery.getProducts()) {
                productsInfo += product.toString();
                productNum++;
                commonCaloricity += product.getCalorie();
            }
        }

        String response = String.format(generalInfo, productsInfo);
        double averageCaloricity = commonCaloricity/productNum;
        response += averageCaloricity > 450
                ? String.format(hugeAverageCaloricity, averageCaloricity)
                : String.format(normalAverageCaloricity, averageCaloricity);

        return response;
    }
}
