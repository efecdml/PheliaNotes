package org.efecdml.phelianotes.appuser;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class AppUserController {
    private AppUserService appUserService;

    @GetMapping
    public List<AppUser> getAllUsers() {
        return appUserService.getAllUsers();
    }

    @PostMapping
    public void addUser(@RequestBody AppUser appUser) {
        appUserService.addUser(appUser);
    }

    @GetMapping("/{userId}")
    public Optional<AppUser> getUser(@PathVariable Long userId) {
//        return appUserRepository.findById(userId).orElseThrow(() -> new IllegalStateException("user not found"));
        return appUserService.getUser(userId);
    }

    @PutMapping("/{userId}")
    public void updateUser(
            @PathVariable Long userId,
            @RequestBody AppUser appUser) {
        appUserService.updateUser(
                userId,
                appUser.getUsername(),
                appUser.getEmail());
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        appUserService.deleteUser(userId);
    }
}
