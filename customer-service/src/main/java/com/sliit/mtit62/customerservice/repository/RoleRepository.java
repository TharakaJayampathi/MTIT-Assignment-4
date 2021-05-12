package com.sliit.mtit62.customerservice.repository;
import com.sliit.mtit62.customerservice.models.ERole;
import com.sliit.mtit62.customerservice.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByName(ERole name);
}
