package Capstone.backend.controller;


import Capstone.backend.dto.ProgettoCommissioniDto;
import Capstone.backend.exception.NotFoundException;
import Capstone.backend.model.ProgettoCommissioni;
import Capstone.backend.service.ProgettoCommissioniService;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/commissioni")
public class ProgettoCommissioniController {

@Autowired
    private ProgettoCommissioniService PCService;

    @PostMapping("/addCommissione")
    public ProgettoCommissioni saveProgettoCommissioni (@RequestBody @Validated ProgettoCommissioniDto progettoCommissioniDto, BindingResult bindingResult) throws NotFoundException, ValidationException {
        if(bindingResult.hasErrors()){
            throw new ValidationException(bindingResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).
                    reduce("",(e,s)->e+s));
        }
        return PCService.postCommissione(progettoCommissioniDto);
    }

    @GetMapping("/")
    public List<ProgettoCommissioni> getCommissioni()  throws NotFoundException {
        return PCService.getCommissini();
    }

    @GetMapping("/{id}")
    public ProgettoCommissioni getProgettoCommissioni(@PathVariable int id)  throws NotFoundException {
        return PCService.getProgettiCommissioni(id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCommissione (@PathVariable int id) throws NotFoundException {
        PCService.deleteProgettoCommissione(id);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }
}
