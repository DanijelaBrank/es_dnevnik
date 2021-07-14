package com.iktpreobuka.es_dnevnik.config;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class JWTAuthorizationFilter extends OncePerRequestFilter {
	private final String HEADER = "Authorization";
	private final String PREFIX = "Bearer ";
	private String secret;

	public JWTAuthorizationFilter(String secret) {
		super();
		this.secret = secret;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		//try {
			if (checkJWTToken(request, response)) {
				Claims claims = validateToken(request);
				if (claims.get("authorities") != null) {
					setUpSpringAuthentication(claims);
				} else {
					SecurityContextHolder.clearContext();
				}
			} else {
				SecurityContextHolder.clearContext();
			}
			chain.doFilter(request, response);
		}
		
	//	catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException e) {
	//		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
	//		((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
	//		return;
	//	}
//	}

	private Claims validateToken(HttpServletRequest request) {
		String jwtToken = request.getHeader(HEADER).replace(PREFIX, "");
		return Jwts.parser().setSigningKey(this.secret.getBytes()).parseClaimsJws(jwtToken).getBody();
	}

	private void setUpSpringAuthentication(Claims claims) {
		@SuppressWarnings("unchecked")
		List<String> authorities = (List<String>) claims.get("authorities");
		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(claims.getSubject(), null,
				authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));

		SecurityContextHolder.getContext().setAuthentication(auth);
	}

	private boolean checkJWTToken(HttpServletRequest request, HttpServletResponse res) {
		String authenticationHeader = request.getHeader(HEADER);
		if (authenticationHeader == null || !authenticationHeader.startsWith(PREFIX)) {
			return false;
		}
		return true;
	}
}








//public class JWTAuthorizationFilter extends OncePerRequestFilter {
//
//	private final String HEADER = "Authorization";
//	private final String PREFIX = "Bearer ";
//	private String secret;
//	
//	public JWTAuthorizationFilter(String secret) {
//	super();
//	this.secret = secret;
//	}
//	
//	@Override
//	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//			throws ServletException, IOException {
//		// TODO proverimo da li token postoji
////		try {
////			if (checkJWTToken(request, response)) {
////			Claims claims = validateToken(request);
////			if (claims.get("authorities") != null) {
////			setUpSpringAuthentication(claims);
////			} else {
////			SecurityContextHolder.clearContext();
////			}
////			} else {
////			SecurityContextHolder.clearContext();
////			}
////			chain.doFilter(request, response);
////			} catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException e) {
////			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
////			((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
////			return;
////			}
//		
//		
//		
//		
//		
//		
//		
//		
//		if(checkIfJWTExistts(request)) {
//			
//			Claims claims = validateToken(request);
//		}else SecurityContextHolder.clearContext();
//		
//	}
//	FilterChain.doFilter(request,response);
//		
//		//TODO ako postoji proverimo da li je validan, ako jeste vratiti sve role koje korisnik ima
//		// TODO ako smo pronasli role postavimo ih na security context
//		//TODO ako nesto nije ok ocistiti kontekst i iyadji iy metode
//		//TODO dodaj nas filter u niz filtera
//
//	}
//
//		private boolean checkIfJWTExistts(HttpServletRequest request) {
//			//String token=request.getHeader("Authorization").replace("Bearer ", "");
//			String token=request.getHeader("Authorization");
//		
//			if(token==null ||!token.startsWith("Bearer")){
//			return false;
//	}
//			
////			if(token!=null &&token.startsWith("Bearer")){
////				return true;
////			}
//		}
//		
//		private void setUpSpringAuthentication(Claims claims) {
//			@SuppressWarnings("unchecked")
//			List<String> authorities = (List<String>) claims.get("authorities");
//			UsernamePasswordAuthenticationToken auth = new
//			UsernamePasswordAuthenticationToken(claims.getSubject(), null, authorities.stream()
//			.map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
//
//			SecurityContextHolder.getContext().setAuthentication(auth);
//			}
//}
