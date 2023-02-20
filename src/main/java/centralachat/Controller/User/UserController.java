package centralachat.Controller.User;


import centralachat.Entity.User.User;
import centralachat.Service.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/User")
    public List<User> retrieveAllUsers() {
        return userService.retrieveAllUsers();
    }

    @PostMapping("/User")
    public User addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @PutMapping("/User")
    public User updateUser(User e) {
        return userService.updateUser(e);
    }


    @DeleteMapping("/User/{idUser}")
    public String removeUser(@PathVariable("idUser") Integer idUser) {
        userService.removeUser(idUser);
        return "Deleted Successfully";
    }

    @GetMapping("/User/{idUser}")
    public User retrieveUser(@PathVariable("idUser") Integer idUser) {
        return userService.retrieveUser(idUser);
    }


}
