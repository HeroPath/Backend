package com.gianca1994.aowebbackend.resources.jwt.config;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gianca1994.aowebbackend.resources.jwt.utilities.JWTConst;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * @Author: Gianca1994
 * Explanation: This class is used to handle the case when the user is not authenticated.
 */

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        /**
         * @Author: Gianca1994
         * Explanation: This method is used to handle the case when the user is not authenticated.
         * @param HttpServletRequest request
         * @param HttpServletResponse response
         * @param AuthenticationException authException
         * @return void
         */
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, JWTConst.UNAUTHORIZED);

    }

}
