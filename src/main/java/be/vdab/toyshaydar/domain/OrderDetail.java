package be.vdab.toyshaydar.domain;

import javax.persistence.*;
import java.math.BigDecimal;
@Embeddable
@Access(AccessType.FIELD)
public class OrderDetail {

    private long ordered;
    private BigDecimal priceEach;


    public OrderDetail(long ordered, BigDecimal priceEach) {
        this.ordered = ordered;
        this.priceEach = priceEach;
    }

    protected OrderDetail() {
    }

    public long getOrdered() {
        return ordered;
    }

    public BigDecimal getPriceEach() {
        return priceEach;
    }
}
