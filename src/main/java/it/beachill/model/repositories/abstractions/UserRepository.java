package it.beachill.model.repositories.abstractions;

import it.beachill.model.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmailIgnoreCase(String username);
	
	List<User> findByEmailContainingIgnoreCase(String toFind);
	

	Collection<? extends User> findByNameContainingIgnoreCase(String toFind);

	Collection<? extends User> findBySurnameContainingIgnoreCase(String toFind);

}
