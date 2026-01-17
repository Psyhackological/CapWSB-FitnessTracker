package pl.wsb.fitnesstracker.user.internal;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.wsb.fitnesstracker.user.api.User;
import pl.wsb.fitnesstracker.user.api.UserDto;
import pl.wsb.fitnesstracker.user.api.UserNotFoundException;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/v1/users")
class UserController {

    private final UserServiceImpl userService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    UserController(
            UserServiceImpl userService,
            UserRepository userRepository,
            UserMapper userMapper) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @GetMapping
    List<UserDto> getAllUsers() {
        return userService.findAllUsers()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    @GetMapping("/simple")
    List<UserDto> getSimpleUsers() {
        return getAllUsers();
    }

    @GetMapping("/{id}")
    UserDto getUserById(@PathVariable Long id) {
        return userMapper.toDto(
                userService.getUser(id)
                        .orElseThrow(() -> new UserNotFoundException(id))
        );
    }

    @GetMapping("/email")
    List<UserDto> getUserByEmail(@RequestParam String email) {
        return userService.getUserByEmail(email)
                .map(userMapper::toDto)
                .stream()
                .toList();
    }

    @GetMapping("/older/{date}")
    List<UserDto> getUsersOlderThan(
            @PathVariable
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date) {

        return userService.findAllUsers().stream()
                .filter(u -> u.getBirthdate().isBefore(date))
                .map(userMapper::toDto)
                .toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    void createUser(@RequestBody User user) {
        userService.createUser(user);
    }

    @PutMapping("/{id}")
    void updateUser(@PathVariable Long id, @RequestBody User incoming) {

        User existing = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        existing.setFirstName(incoming.getFirstName());
        existing.setLastName(incoming.getLastName());
        existing.setBirthdate(incoming.getBirthdate());
        existing.setEmail(incoming.getEmail());

        userRepository.save(existing);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
    }
}
