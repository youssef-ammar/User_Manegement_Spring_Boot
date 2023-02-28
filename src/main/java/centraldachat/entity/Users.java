package centraldachat.entity;


import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Users {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String email;
  private String password;
  private String FirstName;
  private String LastName;
  private String Mobile;
  private String verificationCode;
  private boolean enabled;

//  @ManyToMany(cascade = CascadeType.ALL)
//  private Set<Roles> roles ;

}