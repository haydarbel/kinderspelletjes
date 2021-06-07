package be.vdab.toyshaydar.domain;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalDate ordered;
    private LocalDate required;
    private LocalDate shipped;
    private String comments;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customerId")
    private Customer customer;

    @ManyToMany
    @JoinTable(
            name = "orderdetails",
            joinColumns = @JoinColumn(name = "orderId"),
            inverseJoinColumns = @JoinColumn(name = "productId"))
    private Set<Product> products = new LinkedHashSet<>();




    public Set<Product> getProducts() {
        return Collections.unmodifiableSet(products);
    }


    @Version
    private long version;

    public Order(LocalDate ordered, LocalDate required, LocalDate shipped,
                 String comments, OrderStatus status, Customer customer) {
        this.ordered = ordered;
        this.required = required;
        this.shipped = shipped;
        this.comments = comments;
        this.status = status;
        this.customer = customer;
    }

    protected Order() {
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    public long getId() {
        return id;
    }

    public LocalDate getOrdered() {
        return ordered;
    }

    public LocalDate getRequired() {
        return required;
    }

    public LocalDate getShipped() {
        return shipped;
    }

    public String getComments() {
        return comments;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public Customer getCustomer() {
        return customer;
    }

    public long getVersion() {
        return version;
    }
}
