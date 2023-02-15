package centralachat.Entity.User;


import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class Emplacement implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String address;
    @ManyToOne
    Departement departement;


}
