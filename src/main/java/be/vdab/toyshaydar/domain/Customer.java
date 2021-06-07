package be.vdab.toyshaydar.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @Embedded
    private Adress adress;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "countryId")
    private Country country;

    @OneToMany(mappedBy = "customer")
    @OrderBy("ordered,required")
    private Set<Order> orders;

    @Version
    private long version;

    public Customer(String name, Adress adress, Country country) {
        this.name = name;
        this.adress = adress;
        this.country = country;
    }

    protected Customer() {
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Adress getAdress() {
        return adress;
    }

    public Country getCountry() {
        return country;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }
}
