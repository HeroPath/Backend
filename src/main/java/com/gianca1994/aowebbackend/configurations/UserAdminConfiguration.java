package com.gianca1994.aowebbackend.configurations;

import com.gianca1994.aowebbackend.dto.UserDTO;
import com.gianca1994.aowebbackend.model.User;
import com.gianca1994.aowebbackend.repository.RoleRepository;
import com.gianca1994.aowebbackend.repository.UserRepository;
import com.gianca1994.aowebbackend.service.JWTUserDetailsService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserAdminConfiguration {

    @Bean
    public CommandLineRunner autoSaveUserAdmin(UserRepository userRepository,
                                               RoleRepository roleRepository,
                                               JWTUserDetailsService userDetailsService) {
        return args -> {
            UserDTO userAdmin = new UserDTO("gianca", "test", "gianca9405@gmail.com", 1L);
            User user = userDetailsService.saveUser(userAdmin);
            user.setRole(roleRepository.findById(2L).get());
            userRepository.save(user);
        };
    }
}
