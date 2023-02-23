package com.gianca1994.aowebbackend.resources.jwt.config;

import java.io.IOException;

import com.gianca1994.aowebbackend.resources.jwt.AuthService;
import com.gianca1994.aowebbackend.resources.jwt.utilities.JWTConst;
import io.jsonwebtoken.ExpiredJwtException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import io.jsonwebtoken.MalformedJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * @Author: Gianca1994
 * @Explanation: This class is used to filter the request and check if the token is valid or not.
 */

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private AuthService auth;

    @Autowired
    private JwtTokenUtil jwt;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        /**
         * @Author: Gianca1994
         * @Explanation: This method is used to check if the request is ajax or not.
         * @param HttpServletRequest request
         * @param HttpServletResponse response
         * @param FilterChain chain
         * @return void
         */
        final String requestTokenHeader = request.getHeader(JWTConst.HEADER_STRING);
        String username = null, jwtToken = null;

        if (requestTokenHeader != null && requestTokenHeader.startsWith(JWTConst.TOKEN_PREFIX)) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                username = jwt.getUsernameFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                System.out.println(JWTConst.UNABLE_GET_TOKEN);
                throw new ServletException(JWTConst.UNABLE_GET_TOKEN);
            } catch (ExpiredJwtException e) {
                System.out.println(JWTConst.TOKEN_EXPIRED);
                throw new ServletException(JWTConst.TOKEN_EXPIRED);
            } catch (MalformedJwtException e){
                System.out.println(JWTConst.TOKEN_ADULTERATED);
                throw new ServletException(JWTConst.TOKEN_ADULTERATED);
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.auth.loadUserByUsername(username);

            if (jwt.validateToken(jwtToken, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        chain.doFilter(request, response);
    }
}
