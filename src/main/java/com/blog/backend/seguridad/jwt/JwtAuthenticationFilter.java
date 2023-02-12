package com.blog.backend.seguridad.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.blog.backend.seguridad.CustomUserDetailsService;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String token = obtenerJWtdeLaSolicitud(request);
		
		if(StringUtils.hasText(token) && jwtTokenProvider.validarToken(token)) {
			String username = jwtTokenProvider.obtenerUsernameDeLaSolicitud(token);
			
			UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
			
			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken( userDetails.getUsername(), null, userDetails.getAuthorities());
			authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			
			SecurityContextHolder.getContext().setAuthentication(authToken);
		
		}
		filterChain.doFilter(request, response);
	}

	
	public String obtenerJWtdeLaSolicitud(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7,bearerToken.length());
		}
		return null;
	}

}
