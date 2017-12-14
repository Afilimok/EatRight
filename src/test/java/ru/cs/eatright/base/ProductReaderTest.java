package ru.cs.eatright.base;

import org.junit.Test;
import java.io.File;
import static org.junit.Assert.*;

public class ProductReaderTest {
    @Test
    public void readProduct () throws Exception{

        ProductReader reader = new ProductReader();
        //Product product = reader.readProduct("/Users/alina/Desktop/csc/eatright/eatRight/src/test/resources/food/apple.json");
        File x = new File("");

        String pathfortest = x.getAbsolutePath() + File.separator + "src" + File.separator+"test" + File.separator +
                "resources" + File.separator + "food" + File.separator +"apple.json";
        File t = new File(pathfortest);
        Product product = reader.readProduct(pathfortest);

        assertEquals("apple",product.getName());
        assertEquals(0.4, product.getProtein(), 0.01);
        assertEquals(0.4, product.getFat(), 0.01);
        assertEquals(9.8, product.getCarbohydrate(), 0.01);
        assertEquals(47, product.getCalorie(), 0.01);
        assertEquals(30, product.getGlycemic_index(), 0.01);
        assertEquals(3, product.getType(), 0.01);

    }
}