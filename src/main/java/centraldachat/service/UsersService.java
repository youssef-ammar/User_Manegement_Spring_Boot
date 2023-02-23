package centraldachat.service;

import centraldachat.entity.Users;
import centraldachat.repository.UsersRepository;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.Optional;

@Service
public class UsersService {

  @Autowired
  private UsersRepository usersRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;





  public ResponseEntity<Users> register(Users user) throws UnsupportedEncodingException, MessagingException {
    if (user == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    Optional<Users> existingUser = usersRepository.findByEmail(user.getEmail());
    if (existingUser.isPresent()) {
      return new ResponseEntity<>(HttpStatus.FOUND);
    }
    if (user.getEmail() == null || user.getPassword() == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    } else {
      String encodedPassword = passwordEncoder.encode(user.getPassword());
      user.setPassword(encodedPassword);

      String randomCode = RandomString.make(64);
      user.setVerificationCode(randomCode);
      user.setEnabled(false);
      usersRepository.save(user);
      //sendVerificationEmail(user);
      return ResponseEntity.ok(user);
    }
  }

  public boolean existsEmail(String email) {

    return usersRepository.existsByEmail(email);
  }



}
