package be.vdab.toyshaydar.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
@Access(AccessType.FIELD)
public class OrderDetail {
    private long productId;
    private long ordered;
    private BigDecimal priceEach;


    public OrderDetail( long productId, long ordered, BigDecimal priceEach) {
        this.productId = productId;
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
        return productId == that.productId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId);
    }

    public long getProductId() {
        return productId;
    }

    public long getOrdered() {
        return ordered;
    }

    public BigDecimal getPriceEach() {
        return priceEach;
    }
}
