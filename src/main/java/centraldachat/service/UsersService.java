package centraldachat.service;


//import centraldachat.entity.Roles;

import centraldachat.entity.Users;
//import centraldachat.repository.RolesRepository;
import centraldachat.repository.UsersRepository;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;


import static centraldachat.enums.Role.ADMIN;

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;
//    @Autowired
//    private RolesRepository rolesRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender mailSender;

    public List<Users> retrieveAllUsers() {

        return usersRepository.findAll();
    }

//    public Set<Roles> retrieveRole(long id) {
//
//        return rolesRepository.findByName(ADMIN);
//    }

    public Users addUser(Users e) {
        return usersRepository.save(e);
    }


    public Users updateUser(Users e) {

        return usersRepository.save(e);
    }


    public void removeUser(Integer idUser) {
        usersRepository.deleteById(Long.valueOf(idUser));
    }


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
            sendVerificationEmail(user);
            return ResponseEntity.ok(user);
        }
    }

    public void forgetPass(String token, String newPassword) throws UnsupportedEncodingException, MessagingException {
        Users user = usersRepository.findByToken(token).get();
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        user.setToken(null);
    }

    public void sendPassMail(String userEmail) throws MessagingException, UnsupportedEncodingException {

        String toAddress = userEmail;
        String fromAddress = "youssef.ammar.tn@gmail.com";
        String senderName = "ALPHA_TEAM";
        String subject = "Your ALPHA_TEAM password";
        String content = "Dear Mr/Madame, did you want to reset your password ? "
                + "Someone (hopefully you) has asked us to reset the password for your<br>"
                + "account. Please click the button below to do so. If you didn't<br>"
                + "request this password reset, you can go ahead and ignore this email!"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">RESET PASSWORD</a></h3>"
                + "Thank you,<br>"
                + "ALPHA_TEAM.";
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);
        String token = UUID.randomUUID().toString();

        String verifyURL = "http://localhost:9090/oauth/resetPassword?token=" + token;

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);


        mailSender.send(message);
        usersRepository.findByEmail(userEmail).get().setToken(token);

    }

    private void sendVerificationEmail(Users user) throws MessagingException, UnsupportedEncodingException {

        String toAddress = user.getEmail();
        String fromAddress = "youssef.ammar.tn@gmail.com";
        String senderName = "ALPHA_TEAM";
        String subject = "Please verify your registration";
        String content = "Dear Mr/Madame,<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "ALPHA_TEAM.";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        String verifyURL = "http://localhost:9090/oauth/verify/" + user.getVerificationCode();

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);

        mailSender.send(message);

        System.out.println("Email has been sent");
    }


    public boolean existsEmail(String email) {

        return usersRepository.existsByEmail(email);
    }

    public ResponseEntity<Users> verify(String verificationCode) {
        if (verificationCode == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<Users> user = usersRepository.findByVerificationCode(verificationCode);

        if (user.isPresent()) {
            user.get().setVerificationCode(null);
            user.get().setEnabled(true);
            usersRepository.save(user.get());
            return ResponseEntity.ok(user.get());
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
