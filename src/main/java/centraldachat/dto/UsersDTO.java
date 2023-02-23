package centraldachat.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UsersDTO implements Serializable {

  private Long id;
  private String email;
  private String password;
//  private String FirstName;
//  private String LastName;
  private String verificationCode;
  private boolean enabled;
}
