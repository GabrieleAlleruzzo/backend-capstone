package Capstone.backend.service;


import Capstone.backend.dto.ProgettoCommissioniDto;
import Capstone.backend.exception.NotFoundException;
import Capstone.backend.model.ProgettoCommissioni;
import Capstone.backend.repository.ProgettoCommissioniRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ProgettoCommissioniService {

    // collega service a interfaccia repository
    @Autowired
    private ProgettoCommissioniRepository progettoCommissioniRepository;

    public ProgettoCommissioni postCommissione(ProgettoCommissioniDto PCDto) {
        ProgettoCommissioni postCommissione = new ProgettoCommissioni();
        postCommissione.setName(PCDto.getName());
        postCommissione.setSurname(PCDto.getSurname());
        postCommissione.setTel(PCDto.getTel());
        postCommissione.setEmail(PCDto.getEmail());
        postCommissione.setTipoProg(PCDto.getTipoProg());
        postCommissione.setDescrizione(PCDto.getDescrizione());
        postCommissione.setBudget(PCDto.getBudget());
        postCommissione.setData(PCDto.getData());

        progettoCommissioniRepository.save(postCommissione);
        return postCommissione;
    }

    public List<ProgettoCommissioni> getCommissini( ) throws RuntimeException {
        return progettoCommissioniRepository.findAllByOrderByDataDesc();
    }

    public ProgettoCommissioni getProgettiCommissioni(int id) throws NotFoundException {
        return progettoCommissioniRepository.findById(id).orElseThrow(() -> new NotFoundException("Commissione con id " + id + " non trovata :^("));
    }

 public void deleteProgettoCommissione(int id) throws NotFoundException {
     ProgettoCommissioni progettoCommissioni = progettoCommissioniRepository.findById(id).orElseThrow(() -> new NotFoundException("Progetto con id " + id + " non trovato"));
     progettoCommissioniRepository.delete(progettoCommissioni);
 }
}
