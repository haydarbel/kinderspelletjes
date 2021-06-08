package be.vdab.toyshaydar.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String scale;
    private String description;
    private long inStock;
    private long inOrder;
    private BigDecimal price;
    @Version
    private long version;

    public Product(String name, String scale, String description, long inStock, long inOrder, BigDecimal price) {
        this.name = name;
        this.scale = scale;
        this.description = description;
        this.inStock = inStock;
        this.inOrder = inOrder;
        this.price = price;
    }

    protected Product() {
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getScale() {
        return scale;
    }

    public String getDescription() {
        return description;
    }

    public long getInStock() {
        return inStock;
    }

    public long getInOrder() {
        return inOrder;
    }

    protected void setInStock(long inStock) {
        this.inStock = inStock;
    }

    protected void setInOrder(long inOrder) {
        this.inOrder = inOrder;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return name.equalsIgnoreCase(product.name);
    }

    @Override
    public int hashCode() {
        return name.toUpperCase().hashCode();
    }

    @Override
    public String toString() {
        return  name ;
    }
}
