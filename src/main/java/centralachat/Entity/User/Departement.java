package centralachat.Entity.User;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Set;

@Entity
public class Departement implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy="departement")
    private Set<Emplacement> Emplacements;
}
