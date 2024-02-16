package com.example.authentication.model;



import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE", length = 4)
public class Carte {
    @Id
//    @GeneratedValue(generator = "custom-id")
//    @GenericGenerator(name = "custom-id", strategy = "com.example.authentication.model.CustomIDGenerator")
    @Column(name = "id", length = 16)
    private String id;

    private float solde;
    @ManyToOne
    @JsonManagedReference
    @JsonBackReference
    private Entreprise entreprise;

    @OneToMany
    private Collection<Operation> operations = new ArrayList<>();
    @Enumerated(EnumType.STRING)
    private Status status;

    public void addOperation(Operation operation) {
        this.getOperations().add(operation);
    }

    @Override
    public String toString() {
        return "Carte{" +
                "id=" + id +
                ", solde=" + solde +
                ", operations=" + operations +
                ", status=" + status +
                '}';
    }
}
