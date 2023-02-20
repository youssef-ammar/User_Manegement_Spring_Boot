package centralachat.Service.User;


import centralachat.Entity.User.User;
import centralachat.Repository.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class UserService implements IUserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public List<User> retrieveAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User addUser(User e) {
        return userRepository.save(e);
    }

    @Override
    public User updateUser(User e) {
        return userRepository.save(e);
    }

    @Override
    public User retrieveUser(Integer idUser) {
        return userRepository.getById(Long.valueOf(idUser));
    }

    @Override
    public void removeUser(Integer idUser) {
        userRepository.deleteById(Long.valueOf(idUser));
    }
}
