package com.pej.domains;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;
@Entity
@Table(name = "annees")
@Proxy(lazy=false)
public class Annees implements java.io.Serializable {

    private Integer idannees;
    private String name;
    @JsonIgnore
    private Set<Suivie> suivies = new HashSet<Suivie>(0);

    public Annees() {
    }

    public Annees(String name) {
        this.name = name;

    }

    @Id
    @GeneratedValue(generator = "SEQ_IDANNEES", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_IDANNEES", sequenceName = "SEQ_IDANNEES",allocationSize=1)

    @Column(name="idannees", unique=true, nullable=false, length=20)
    public Integer getIdannees() {
        return this.idannees;
    }

    public void setIdannees(Integer idannees) {
        this.idannees = idannees;
    }

    @Column(name = "NAME", length = 255)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(fetch=FetchType.LAZY, mappedBy="annees")
    public Set<Suivie> getSuivies() {
        return this.suivies;
    }

    public void setSuivies(Set<Suivie> suivies) {
        this.suivies = suivies;
    }

    @Override
    public String toString() {
        return ""+idannees;
    }
}
