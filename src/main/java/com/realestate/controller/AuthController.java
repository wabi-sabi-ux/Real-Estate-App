package com.realestate.controller;

import com.realestate.dto.UserProfile;
import com.realestate.model.Broker;
import com.realestate.model.Customer;
import com.realestate.model.User;
import com.realestate.repository.BrokerRepository;
import com.realestate.repository.CustomerRepository;
import com.realestate.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepo;
    private final BrokerRepository brokerRepo;
    private final CustomerRepository customerRepo;
    private final PasswordEncoder encoder;

    public AuthController(UserRepository userRepo,
                          BrokerRepository brokerRepo,
                          CustomerRepository customerRepo,
                          PasswordEncoder encoder) {
        this.userRepo = userRepo;
        this.brokerRepo = brokerRepo;
        this.customerRepo = customerRepo;
        this.encoder = encoder;
    }

    @PostMapping("/register/broker")
    public ResponseEntity<?> registerBroker(@Valid @RequestBody RegisterBrokerRequest req) {
        if (userRepo.findByEmail(req.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email already registered");
        }

        User u = new User();
        u.setEmail(req.getEmail());
        u.setPassword(encoder.encode(req.getPassword())); // store BCrypt hash
        u.setRole("BROKER");
        u.setCity(req.getCity());
        u.setMobile(req.getMobile());

        Broker b = new Broker();
        b.setBroName(req.getBrokerName());
        b.setUser(u);

        Broker saved = brokerRepo.save(b);
        return ResponseEntity.ok(toProfile(saved.getUser(), saved.getBroId(), null));
    }

    @PostMapping("/register/customer")
    public ResponseEntity<?> registerCustomer(@Valid @RequestBody RegisterCustomerRequest req) {
        if (userRepo.findByEmail(req.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email already registered");
        }

        User u = new User();
        u.setEmail(req.getEmail());
        u.setPassword(encoder.encode(req.getPassword()));
        u.setRole("CUSTOMER");
        u.setCity(req.getCity());
        u.setMobile(req.getMobile());

        Customer c = new Customer();
        c.setCustName(req.getCustomerName());
        c.setUser(u);

        Customer saved = customerRepo.save(c);
        return ResponseEntity.ok(toProfile(saved.getUser(), null, saved.getCustId()));
    }

    private UserProfile toProfile(User user, Long brokerId, Long customerId) {
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
