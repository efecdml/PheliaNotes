package org.efecdml.phelianotes.appuser;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class AppUserConfig {
    @Bean
    CommandLineRunner commandLineRunner(AppUserRepository appUserRepository) {
        return args -> {
            AppUser oleg = new AppUser(
                    "oleg",
                    "oleg@mail.com",
                    "pass"
            );

            AppUser igor = new AppUser(
                    "igor",
                    "igor@mail.com",
                    "pas"
            );

            appUserRepository.saveAll(List.of(oleg, igor));
        };
    }
}
