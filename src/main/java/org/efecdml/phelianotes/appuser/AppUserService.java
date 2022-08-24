package org.efecdml.phelianotes.appuser;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@AllArgsConstructor
@Service
public class AppUserService {
    private final AppUserRepository appUserRepository;

    public List<AppUser> getAllUsers() {
        return appUserRepository.findAll();
    }

    public Optional<AppUser> getUser(Long userId) {
        Optional<AppUser> appUserOptional = appUserRepository.findById(userId);
        if (!appUserOptional.isPresent()) {
            throw new IllegalArgumentException("User with " + userId + " not found..");
        }

        return appUserRepository.findById(userId);
    }

    public void addUser(AppUser appUser) {
        Optional<AppUser> appUserOptional = appUserRepository.findByUsername(appUser.getUsername());

        if (appUserOptional.isPresent()) {
            throw new IllegalStateException("This username is already taken..");
        }

        appUserRepository.save(appUser);
    }

    public void updateUser(
            Long userId,
            String username,
            String email
    ) {
        AppUser appUser = appUserRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("User with " + userId + " ID doesn't exist.."));

        if (username != null && username.length() > 1 && !Objects.equals(username, appUser.getUsername())) {
            Optional<AppUser> appUserOptional = appUserRepository.findByUsername(username);
            if (appUserOptional.isPresent()) {
                throw new IllegalStateException("This username is already taken..");
            }
            appUser.setUsername(username);
        }

        if (email != null && email.length() > 1 && !Objects.equals(appUser.getEmail(), email)) {
            appUser.setEmail(email);
        }

        appUserRepository.save(appUser);
    }

    public void deleteUser(Long userId) {
        boolean exist = appUserRepository.existsById(userId);

        if (!exist) {
            throw new IllegalStateException("User with " + userId + " doesn't exist..");
        }

        appUserRepository.deleteById(userId);
    }
}
