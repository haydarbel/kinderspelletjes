package be.vdab.toyshaydar.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "countries")
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @Version
    private long version;

    public Country(String name) {
        this.name = name;
    }

    protected Country() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Country)) return false;
        Country country = (Country) o;
        return name.equalsIgnoreCase(country.getName());
    }

    @Override
    public int hashCode() {
        return name.toUpperCase().hashCode();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return name;
    }
}
