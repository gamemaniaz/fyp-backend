package foodbank.user.service.impl;

import java.util.List;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import foodbank.login.dto.PasswordDTO;
import foodbank.security.model.Role;
import foodbank.security.model.repository.RoleRepository;
import foodbank.user.dto.UserDTO;
import foodbank.user.entity.User;
import foodbank.user.repository.UserRepository;
import foodbank.user.service.UserService;
import foodbank.util.EntityManager;
import foodbank.util.MessageConstants.ErrorMessages;
import foodbank.util.exceptions.UserException;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		return userRepository.findAll();
	}

	public List<User> getAllUsersByType(String usertype) {
		// TODO Auto-generated method stub
		return userRepository.findUsersByUsertype(usertype);
	}

	public User getUserDetails(String username) {
		// TODO Auto-generated method stub
		User dbUser = userRepository.findByUsernameIgnoreCase(username);
		if(dbUser == null) {
			throw new UserException(ErrorMessages.NO_SUCH_USER);
		}
		return dbUser;
	}

	public void insertUser(UserDTO user) {
		// TODO Auto-generated method stub
		User dbUser = userRepository.findByUsernameIgnoreCase(user.getUsername());
		if(dbUser != null) {
			throw new UserException(ErrorMessages.USER_ALREADY_EXISTS);
		}
		dbUser = EntityManager.transformUserDTO(user);
		dbUser.setPassword(BCrypt.hashpw(dbUser.getPassword(), BCrypt.gensalt()));
		dbUser.setRole(roleRepository.findOne(Role.ADMIN_USER));
		userRepository.save(dbUser);
	}

	public void updateUser(UserDTO user) {
		// TODO Auto-generated method stub
		User dbUser = userRepository.findByUsernameIgnoreCase(user.getUsername());
		if(dbUser == null) {
			throw new UserException(ErrorMessages.NO_SUCH_USER);
		}
		User newUserDetails = EntityManager.transformUserDTO(user);
		String newName = newUserDetails.getName();
		String newEmail = newUserDetails.getEmail();
		String newUsertype = newUserDetails.getUsertype();
		if(newName != null && !newName.isEmpty()) {
			dbUser.setName(newName);
		}
		if(newEmail != null && !newEmail.isEmpty()) {
			dbUser.setEmail(newEmail);
		}
		if(newUsertype != null && !newUsertype.isEmpty()) {
			dbUser.setUsertype(newUsertype.toLowerCase());
		}
		userRepository.save(dbUser);
	}

	public void deleteUser(String username) {
		// TODO Auto-generated method stub
		User dbUser = userRepository.findByUsernameIgnoreCase(username);
		if(dbUser == null) {
			throw new UserException(ErrorMessages.NO_SUCH_USER);
		}
		userRepository.delete(dbUser);
	}

	public Boolean changePassword(PasswordDTO passwordDetails) {
		// TODO Auto-generated method stub
		User dbUser = userRepository.findByUsernameIgnoreCase(passwordDetails.getUsername());
		if(dbUser == null) {
			throw new UserException(ErrorMessages.NO_SUCH_USER);
		}
		if(BCrypt.checkpw(passwordDetails.getOldPassword(), dbUser.getPassword())) {
			dbUser.setPassword(BCrypt.hashpw(passwordDetails.getNewPassword(), BCrypt.gensalt()));
			userRepository.save(dbUser);
			return true;
		}
		return false;
	}

}
