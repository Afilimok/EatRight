package ru.cs.eatright.search;

import com.google.common.base.Objects;
import ru.cs.eatright.knowledgebase.Product;

import java.util.List;

public class ParsedQuery {
    private final List<Product> products;
    //todo: add ADVERB and VERB as parameters

    public ParsedQuery(List<Product> products) {
        this.products = products;
    }

    public List<Product> getProducts() {
        return products;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (!(o instanceof ParsedQuery)) return false;
        ParsedQuery that = (ParsedQuery) o;
        return Objects.equal(getProducts(), that.getProducts());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getProducts());
    }
}
