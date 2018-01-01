package com.pej.domains;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by D O N A T I E N on 31/12/2016.
 */
@Entity
@Table(name = "materiel")
@Proxy(lazy=false)
public class Materiel implements java.io.Serializable {

    private Integer idmateriel;
    private String nommateriel;
    @JsonIgnore
    private Set<Fichefinancement> fichefinancements = new HashSet<Fichefinancement>(0);

    public Materiel() {
    }

    public Materiel(String nommateriel) {
        this.nommateriel = nommateriel;

    }

    @Id
    @GeneratedValue(generator = "SEQ_IDMATERIEL", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_IDMATERIEL", sequenceName = "SEQ_IDMATERIEL",allocationSize=1)

    @Column(name="idmateriel", unique=true, nullable=false, length=20)
    public Integer getIdmateriel() {
        return this.idmateriel;
    }

    public void setIdmateriel(Integer idmateriel) {
        this.idmateriel = idmateriel;
    }

    @Column(name = "nommateriel", length = 255)
    public String getNommateriel() {
        return this.nommateriel;
    }

    public void setNommateriel(String nommateriel) {
        this.nommateriel = nommateriel;
    }

    @OneToMany(fetch=FetchType.LAZY, mappedBy="materiel")
    public Set<Fichefinancement> getFichefinancements() {
        return this.fichefinancements;
    }

    public void setFichefinancements(Set<Fichefinancement> fichefinancements) {
        this.fichefinancements = fichefinancements;
    }

    @Override
    public String toString() {
        return ""+idmateriel;
    }
}
