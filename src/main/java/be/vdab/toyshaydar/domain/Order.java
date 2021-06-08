package be.vdab.toyshaydar.domain;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import java.math.BigDecimal;
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

    @ElementCollection
    @CollectionTable(name = "orderdetails",
            joinColumns = @JoinColumn(name = "orderId"))
    private Set<OrderDetail> orderDetails = new LinkedHashSet<>();

    public Set<OrderDetail> getOrderDetails() {
        return Collections.unmodifiableSet(orderDetails);
    }

    public boolean addOrderDetail(OrderDetail orderDetail) {
        return orderDetails.add(orderDetail);
    }

    @Version
    private long version;

    public void setVersion(long version) {
        this.version = version;
    }

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

    public boolean setOrderAsShipped() {
        for (OrderDetail orderDetail : orderDetails) {
            if (!orderDetail.makeOrderDetailDone()) {
                return false;
            }
        }
        setStatus(OrderStatus.SHIPPED);
        setShipped(LocalDate.now());
        return true;
    }

    @NumberFormat(pattern = "#,##0.00")
    public BigDecimal getOrderTotalValue() {
        return orderDetails.stream()
                .map(OrderDetail::getValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public long getId() {
        return id;
    }
    @DateTimeFormat(pattern = "dd/MM/yy")
    public LocalDate getOrdered() {
        return ordered;
    }
    @DateTimeFormat(pattern = "dd/MM/yy")
    public LocalDate getRequired() {
        return required;
    }

    public void setShipped(LocalDate shipped) {
        this.shipped = shipped;
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

    private void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Customer getCustomer() {
        return customer;
    }

    public long getVersion() {
        return version;
    }
}
