package com.esempio.Ecommerce.api.controller.auth;

import com.esempio.Ecommerce.api.model.RegistrationBody;
import com.esempio.Ecommerce.exception.UserAlreadyExistsException;
import com.esempio.Ecommerce.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private UserService userService;

    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity registerUser(@Valid @RequestBody RegistrationBody registrationBody){
        //quando fai il frontend ricordati che se ti danno dati in più funziona comunque e quindi risolvi sta roba
        // prova: System.out.println(registrationBody.getUsername());
        try {
            userService.registerUser(registrationBody);
            return ResponseEntity.ok().build();
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
 }