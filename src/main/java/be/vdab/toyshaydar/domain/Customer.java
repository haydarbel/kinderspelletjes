package be.vdab.toyshaydar.domain;

import javax.persistence.*;
import java.util.Collections;
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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "countryId")
    private Country country;

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

    @Override
    public String toString() {
        return name;
    }
}
