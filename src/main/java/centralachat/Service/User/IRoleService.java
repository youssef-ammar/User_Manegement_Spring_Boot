package centralachat.Service.User;

import centralachat.Entity.User.Role;

import java.util.List;

public interface IRoleService {

    List<Role> retrieveAllRoles();

    Role addRole(Role e);

    Role updateRole(Role e);


    void removeRole(Integer idRole);
}
