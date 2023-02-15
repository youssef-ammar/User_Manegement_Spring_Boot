package centralachat.Entity.User;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    @OneToOne
    private Emplacement userEmp;
    @OneToOne
    private Role userRole;
}
