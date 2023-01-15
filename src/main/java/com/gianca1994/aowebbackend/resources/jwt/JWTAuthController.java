package com.gianca1994.aowebbackend.resources.jwt;

import com.gianca1994.aowebbackend.resources.user.dto.UserDTO;
import com.gianca1994.aowebbackend.exception.Conflict;
import com.gianca1994.aowebbackend.exception.NotFound;
import com.gianca1994.aowebbackend.resources.user.User;
import com.gianca1994.aowebbackend.resources.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: Gianca1994
 * Explanation: AuthController
 */
@RestController
@CrossOrigin()
@RequestMapping("/api/v1/auth")
public class JWTAuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JWTUserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping(value = "login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequestDTO authenticationRequest) throws Exception {
        /**
         * @Author: Gianca1994
         * Explanation: This method is used to authenticate the user and generate a JWT token.
         * @param JwtRequestDTO authenticationRequest
         * @return ResponseEntity<?>
         */
        String username = authenticationRequest.getUsername().toLowerCase();
        String password = authenticationRequest.getPassword();
        authenticate(username, password);
        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponseDTO(token));
    }

    @PostMapping(value = "register")
    public ResponseEntity<?> saveUser(@RequestBody UserDTO user) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method is used to register a new user.
         * @param UserDTO user
         * @return ResponseEntity<?>
         */
        return ResponseEntity.ok(userDetailsService.saveUser(user));
    }

    private void authenticate(String username, String password) throws Exception {
        /**
         * @Author: Gianca1994
         * Explanation: This method is used to authenticate the user.
         * @param String username: The username of the user.
         * @param String password: The password of the user.
         * @return void
         */
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    @GetMapping(value = "restore/{username}")
    public void restoreHpUser(@PathVariable String username) {
        /**
         * @Author: Gianca1994
         * Explanation: This method is used to restore the hp of the user.
         * @param String username
         * @return void
         */
        User user = userRepository.findByUsername(username);
        if (user == null) throw new NotFound("User not found");
        user.setHp(user.getMaxHp());
        userRepository.save(user);
    }

}
