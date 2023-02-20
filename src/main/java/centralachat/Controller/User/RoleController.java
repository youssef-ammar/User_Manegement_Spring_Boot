package centralachat.Controller.User;

import centralachat.Entity.User.Role;

import centralachat.Service.User.RoleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RoleController {

    @Autowired
    RoleService roleService;

    @GetMapping("/Role")
    public List<Role> retrieveAllRoles() {
        return roleService.retrieveAllRoles();
    }

    @PostMapping("/Role")
    public Role addUser(@RequestBody Role user) {
        return roleService.addRole(user);
    }

    @PutMapping("/Role")
    public Role updateRole(Role e) {
        return roleService.updateRole(e);
    }


    @DeleteMapping("/Role/{idRole}")
    public String removeRole(@PathVariable("idRole") Integer idRole) {
        roleService.removeRole(idRole);
        return "Deleted Successfully";
    }


}
