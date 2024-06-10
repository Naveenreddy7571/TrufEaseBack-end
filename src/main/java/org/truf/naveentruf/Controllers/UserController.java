package org.truf.naveentruf.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.truf.naveentruf.Models.TrufUser;
import org.truf.naveentruf.Services.Userservice;

import java.util.List;


@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private Userservice userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @PostMapping("/register/user")
    public ResponseEntity<TrufUser> register(@RequestBody TrufUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        System.out.println("Registering user with role: " + user.getRole());
        TrufUser savedUser = userService.registerUser(user);
        return ResponseEntity.ok(savedUser);
    }

    @GetMapping("/user/customer/all")
    public ResponseEntity<List<TrufUser>> getAllCustomers()
    {
        try {
            List<TrufUser> trufUserList = userService.getAllCustomers();
            return new ResponseEntity<>(trufUserList, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
