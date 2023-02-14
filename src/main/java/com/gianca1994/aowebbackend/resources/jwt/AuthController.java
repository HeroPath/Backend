package com.gianca1994.aowebbackend.resources.jwt;

import com.gianca1994.aowebbackend.exception.NotFound;
import com.gianca1994.aowebbackend.resources.jwt.config.JwtTokenUtil;
import com.gianca1994.aowebbackend.resources.jwt.dto.request.JwtRequestDTO;
import com.gianca1994.aowebbackend.resources.jwt.dto.response.JwtResponseDTO;
import com.gianca1994.aowebbackend.resources.jwt.utilities.JWTConst;
import com.gianca1994.aowebbackend.resources.user.UserRepository;
import com.gianca1994.aowebbackend.resources.user.dto.request.UserRegisterDTO;
import com.gianca1994.aowebbackend.exception.Conflict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

/**
 * @Author: Gianca1994
 * Explanation: This class is used to manage the authentication and registration of users.
 */

@RestController
@CrossOrigin()
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private JwtTokenUtil jwt;

    @Autowired
    private AuthService authS;

    @Autowired
    private UserRepository userR;

    @PostMapping(value = "login")
    public ResponseEntity<?> login(@RequestBody JwtRequestDTO authenticationRequest) throws Exception {
        /**
         * @Author: Gianca1994
         * Explanation: This method is used to authenticate the user and generate a JWT token.
         * @param JwtRequestDTO authenticationRequest
         * @return ResponseEntity<?>
         */
        String username = authenticationRequest.getUsername().toLowerCase();
        String password = authenticationRequest.getPassword();

        if (!userR.existsByUsername(username)) throw new NotFound(JWTConst.USER_NOT_FOUND);
        authS.authenticate(username, password);

        final UserDetails userDetails = authS.loadUserByUsername(username);
        final String token = jwt.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponseDTO(token));
    }

    @PostMapping(value = "register")
    public ResponseEntity<?> register(@RequestBody UserRegisterDTO user) throws Conflict, NoSuchAlgorithmException {
        /**
         * @Author: Gianca1994
         * Explanation: This method is used to register a new user.
         * @param UserDTO user
         * @return ResponseEntity<?>
         */
        return ResponseEntity.ok(authS.saveUser(user));
    }
}

