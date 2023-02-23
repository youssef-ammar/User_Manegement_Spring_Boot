package centraldachat.controller;

import centraldachat.dto.UsersDTO;
import centraldachat.entity.Users;
import centraldachat.payload.response.JwtResponse;
import centraldachat.security.jwt.JwtUtils;
import centraldachat.security.services.UserDetailsImpl;
import centraldachat.service.UsersService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

@CrossOrigin(origins = "http://localhost:3000")

@RestController
public class UsersController {

  public final static String FOUND = "FOUND";
  public final static String BAD_REQUEST = "BAD_REQUEST";
  public final static String NOT_FOUND = "NOT_FOUND";

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private UsersService usersService;

  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  JwtUtils jwtUtils;


  public UsersController(UsersService usersService) {
    super();
    this.usersService = usersService;
  }



  @PostMapping("/oauth/signin")
  public ResponseEntity<Object> authenticateUser(@RequestBody UsersDTO usersDTO) {
    Users user = modelMapper.map(usersDTO, Users.class);
    System.out.println(user.getEmail());
    if (!usersService.existsEmail(user.getEmail())) {
      return new ResponseEntity<>(NOT_FOUND, HttpStatus.OK);
    }

    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateJwtToken(authentication);

    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    return new ResponseEntity<>(new JwtResponse(jwt,
        userDetails.getId(),
        userDetails.getEmail()
    ), HttpStatus.OK);
  }


  @PostMapping("/oauth/process_register")
  public ResponseEntity<Object> processRegister(@RequestBody UsersDTO usersDTO) throws UnsupportedEncodingException, MessagingException {
    Users userReq = modelMapper.map(usersDTO, Users.class);
    ResponseEntity<Users> user = usersService.register(userReq);

    if (user.getStatusCodeValue() == 200) {
      UsersDTO userRes = modelMapper.map(user.getBody(), UsersDTO.class);
      return new ResponseEntity<>(userRes, HttpStatus.OK);
    } else if (user.getStatusCodeValue() == 400) {
      return new ResponseEntity<>(BAD_REQUEST, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(FOUND, HttpStatus.OK);
    }
  }

  @PostMapping("/test")
  public void test() {
    System.out.println("test");
  }


}


