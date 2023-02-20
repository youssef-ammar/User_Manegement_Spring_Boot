package centralachat.Entity.User;


import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class Boutique implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String address;
    @OneToOne(mappedBy="userEmp")
    private User user;
    @ManyToOne
    Departement departement;


}
