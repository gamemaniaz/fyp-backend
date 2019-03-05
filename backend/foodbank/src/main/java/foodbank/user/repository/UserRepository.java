package foodbank.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import foodbank.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{

	User findByUsernameIgnoreCase(String username);
	
	List<User> findUsersByUsertype(String usertype);
	
	User findByEmailIgnoreCase(String email);
	
}
