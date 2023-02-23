package centraldachat.security.services;

import centraldachat.entity.Users;


public class UserFactory {
  public static UserDetailsImpl build(Users user){

    return new UserDetailsImpl(user.getId(),user.getEmail(), user.getPassword());
  }
}
