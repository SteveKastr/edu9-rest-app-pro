package gr.aueb.cf.edu9app.repository;

import gr.aueb.cf.edu9app.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {

    List<Role> findAllByOrderByNameAsc();
}
