package foodbank.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import foodbank.user.entity.User;
import foodbank.util.MessageConstants.ErrorMessages;
import foodbank.util.exceptions.InvalidTokenException;
import foodbank.util.exceptions.UserException;

@Component
public class JwtAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
 
	@Autowired
	private JwtValidator validator;
	
	@Override
	public boolean supports(Class<?> aClass) {
		// TODO Auto-generated method stub
		return JwtAuthenticationToken.class.isAssignableFrom(aClass);
	}

	@Override
	protected void additionalAuthenticationChecks(UserDetails arg0, UsernamePasswordAuthenticationToken arg1)
			throws AuthenticationException {
		// TODO Auto-generated method stub
	}

	@Override
	protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {
		// TODO Auto-generated method stub
		JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken)usernamePasswordAuthenticationToken;
		String token = jwtAuthenticationToken.getToken();
		/*
		JwtUser jwtUser = validator.validate(token);
		if(jwtUser == null) {
			throw new UserException("JWT Token is incorrect");
		}
		*/
		User user = validator.validateUser(token);
		if(user == null) {
			throw new InvalidTokenException(ErrorMessages.INCORRECT_TOKEN);
		}
		//List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(jwtUser.getRole());
		//List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(user.getRoles().get(0).getRoleName());
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(user.getRole().getRoleName());
		return new JwtUserDetails(user.getUsername(), user.getId(), token, grantedAuthorities);
	}

}
