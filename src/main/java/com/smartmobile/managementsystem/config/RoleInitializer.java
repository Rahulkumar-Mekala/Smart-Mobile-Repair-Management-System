
package com.smartmobile.managementsystem.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.smartmobile.managementsystem.Entity.Role;
import com.smartmobile.managementsystem.repository.RoleRepository;

@Configuration
public class RoleInitializer {

    @Bean
    CommandLineRunner initializeRoles(RoleRepository roleRepository) {

        return args -> {

          createRole(roleRepository, "ADMIN",
                    "System Administrator");

            createRole(roleRepository, "SHOP_OWNER",
                    "Repair Shop Owner");

            createRole(roleRepository, "TECHNICIAN",
                    "Repair Technician");

            createRole(roleRepository, "CUSTOMER",
                    "Mobile Repair Customer");
        };
    }

    private void createRole(
            RoleRepository repository,
            String roleName,
            String description) {

        if (repository.findByRoleName(roleName).isEmpty()) {

            Role role = new Role();
            role.setRoleName(roleName);
            role.setDescription(description);

            repository.save(role);
        }
    }
}
