package be.vdab.toyshaydar.domain;

import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
@Access(AccessType.FIELD)
public class OrderDetail {

    private long ordered;
    private BigDecimal priceEach;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "productId")
    private Product product;


    public OrderDetail(Product product, long ordered, BigDecimal priceEach) {
        this.product = product;
        this.ordered = ordered;
        this.priceEach = priceEach;
    }

    protected OrderDetail() {
    }

    public boolean canItBeShipped() {
        return product.getInStock() >= ordered && product.getInOrder() >= ordered;
    }

    public boolean reduceInorderAndInStockWithOrdered() {
        if (canItBeShipped()) {
            product.setInStock(product.getInStock() - ordered);
            product.setInOrder(product.getInOrder() - ordered);
            return true;
        } else {
            return false;
        }
    }

    @NumberFormat(pattern = "#,##0.00")
    public BigDecimal getValue() {
        return priceEach.multiply(BigDecimal.valueOf(ordered));
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

    @Override
    public String toString() {
        return product.getName();
    }
}
