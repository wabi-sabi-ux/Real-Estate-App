package com.realestate.controller;

import com.realestate.dto.UserProfile;
import com.realestate.model.User;
import com.realestate.repository.BrokerRepository;
import com.realestate.repository.CustomerRepository;
import com.realestate.service.IUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final IUserService userService;
    private final BrokerRepository brokerRepository;
    private final CustomerRepository customerRepository;

    public UserController(IUserService userService,
                         BrokerRepository brokerRepository,
                         CustomerRepository customerRepository) {
        this.userService = userService;
        this.brokerRepository = brokerRepository;
        this.customerRepository = customerRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password) {
        Optional<User> user = userService.login(email, password);

        if (user.isPresent()) {
            return ResponseEntity.ok(toProfile(user.get()));
        } else {
            return ResponseEntity.status(401).body("Invalid email or password");
        }
    }

    private UserProfile toProfile(User user) {
        Long brokerId = brokerRepository.findByUser_UserId(user.getUserId())
                .map(b -> b.getBroId())
                .orElse(null);
        Long customerId = customerRepository.findByUser_UserId(user.getUserId())
                .map(c -> c.getCustId())
                .orElse(null);

        return new UserProfile(
                user.getUserId(),
                user.getEmail(),
                user.getRole(),
                user.getMobile(),
                user.getCity(),
                brokerId,
                customerId
        );
    }
}
