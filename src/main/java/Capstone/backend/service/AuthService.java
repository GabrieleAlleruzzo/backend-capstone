package Capstone.backend.service;


import Capstone.backend.Security.JwtTool;
import Capstone.backend.dto.AdminDto;
import Capstone.backend.exception.NotFoundException;
import Capstone.backend.exception.UnAuthorizedException;
import Capstone.backend.model.Admin;
import Capstone.backend.repository.AdminRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private JwtTool jwtTool;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String login (AdminDto adminDto) throws UnAuthorizedException, ValidationException {
        Admin admin = adminRepository.findByName(adminDto.getName()).orElseThrow(() -> new NotFoundException("Utente con questo username/password non trovato"));
        if (passwordEncoder.matches(adminDto.getPswd(), admin.getPassword())) {
            return jwtTool.createToken(admin);
        } else {
            throw new NotFoundException("Utente con questo username/password non trovato");
        }
    }
}
