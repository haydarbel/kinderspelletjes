package be.vdab.toyshaydar.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
@Access(AccessType.FIELD)
public class OrderDetail {

    private long ordered;
    private BigDecimal priceEach;

    @ManyToOne(fetch = FetchType.EAGER,optional = false)
    @JoinColumn(name = "productId")
    private Product product;


    public OrderDetail( Product product, long ordered, BigDecimal priceEach) {
        this.product = product;
        this.ordered = ordered;
        this.priceEach = priceEach;
    }

    protected OrderDetail() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderDetail)) return false;
        OrderDetail that = (OrderDetail) o;
        return product == that.product;
    }

    @Override
    public int hashCode() {
        return Objects.hash(product);
    }

    public Product getProduct() {
        return product;
    }

    public long getOrdered() {
        return ordered;
    }

    public BigDecimal getPriceEach() {
        return priceEach;
    }

    public BigDecimal getValue() {
        return priceEach.multiply(BigDecimal.valueOf(ordered));
    }

    public boolean isShippable() {
        return product.canBeShippedProduct() >= ordered;
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "product=" + product.getName() +
                '}';
    }
}
