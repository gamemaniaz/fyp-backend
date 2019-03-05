package foodbank.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import foodbank.util.MessageConstants.ErrorMessages;
import foodbank.util.exceptions.InvalidTokenException;

public class JwtAuthenticationTokenFilter extends AbstractAuthenticationProcessingFilter {
	
	public JwtAuthenticationTokenFilter() {
		super("/**/rest/**");
		// TODO Auto-generated constructor stub
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
			throws AuthenticationException, IOException, ServletException {
		// TODO Auto-generated method stub
		String header = httpServletRequest.getHeader("authorization");
		if(header == null) { //|| !header.startsWith("Token ")) {
			throw new InvalidTokenException(ErrorMessages.MISSING_TOKEN);
		}
		//String authenticationToken = header.substring(6);
		JwtAuthenticationToken token = new JwtAuthenticationToken(header);//new JwtAuthenticationToken(authenticationToken);
		return getAuthenticationManager().authenticate(token);
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		// TODO Auto-generated method stub
		super.successfulAuthentication(request, response, chain, authResult);
		chain.doFilter(request, response);
	}

}
