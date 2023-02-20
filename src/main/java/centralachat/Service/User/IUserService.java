package centralachat.Service.User;
import  centralachat.Entity.User.User;

import java.util.List;

public interface IUserService {

    List<User> retrieveAllUsers();

    User addUser(User e);

    User updateUser(User e);

    User retrieveUser(Integer idUser);

    void removeUser(Integer idUser);

}
