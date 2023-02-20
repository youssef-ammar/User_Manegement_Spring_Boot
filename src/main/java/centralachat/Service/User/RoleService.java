package centralachat.Service.User;

import centralachat.Entity.User.Role;
import centralachat.Repository.User.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RoleService implements IRoleService {
    @Autowired
    RoleRepository roleRepository;

    @Override
    public List<Role> retrieveAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role addRole(Role e) {
        return roleRepository.save(e);
    }

    @Override
    public Role updateRole(Role e) {
        return roleRepository.save(e);
    }


    @Override
    public void removeRole(Integer idRole) {
        roleRepository.deleteById(Long.valueOf(idRole));
    }
}
