package centralachat.Entity.User;

import jakarta.persistence.*;

import java.io.Serializable;
@Entity
public class Role implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String role;
    @OneToOne(mappedBy="userRole")
    private User user;
}
