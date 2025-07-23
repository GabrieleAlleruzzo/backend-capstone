package Capstone.backend.controller;


import Capstone.backend.dto.AdminDto;
import Capstone.backend.exception.UnAuthorizedException;
import Capstone.backend.model.Admin;
import Capstone.backend.service.AuthService;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/auth/login")
    public String login (@RequestBody @Validated AdminDto adminDto, BindingResult bindingResult) throws UnAuthorizedException, ValidationException {
        if(bindingResult.hasErrors()){
            throw new ValidationException(bindingResult.getAllErrors().stream().
                    map(objectError -> objectError.getDefaultMessage()).
                    reduce("", (s,e)->s+e));
        }
        return authService.login(adminDto);
    }

    @PostMapping("/auth/addadmin")
    public Admin registerAdmin (@RequestBody @Validated AdminDto adminDto, BindingResult bindingResult) throws UnAuthorizedException, ValidationException {
        if(bindingResult.hasErrors()){
            throw new ValidationException(bindingResult.getAllErrors().stream().
                    map(objectError -> objectError.getDefaultMessage()).
                    reduce("", (s,e)->s+e));
        }
        return authService.addAdmin(adminDto);
    }
}
