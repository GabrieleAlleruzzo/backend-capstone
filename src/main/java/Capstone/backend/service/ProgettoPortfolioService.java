package Capstone.backend.service;

import Capstone.backend.dto.ProgettoPortfolioDto;
import Capstone.backend.exception.NotFoundException;
import Capstone.backend.model.ProgettoPortfolio;
import Capstone.backend.repository.ProgettoPortfolioRepository;
import com.cloudinary.Cloudinary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class ProgettoPortfolioService {
    @Autowired
    private ImageUploadService imgUploaderService;

    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private ProgettoPortfolioRepository progettoPortfolioRepository;

    public List<ProgettoPortfolio> getPortfolio() throws RuntimeException {
        return progettoPortfolioRepository.findAllByOrderByDataDesc();
    }

    public ProgettoPortfolio getProgettiPortfolio(int id) throws NotFoundException {
        return progettoPortfolioRepository.findById(id).orElseThrow(() -> new NotFoundException("Progetto con id " + id + " non trovato"));
    }

    public ProgettoPortfolio saveProgettoPortfolio(ProgettoPortfolioDto progettoPortfolioDto, HashMap<String, String> obj ) throws RuntimeException {
        ProgettoPortfolio newProgettoPortfolio = new ProgettoPortfolio();
        newProgettoPortfolio.setNomeProgetto(progettoPortfolioDto.getNomeProgetto());
        newProgettoPortfolio.setImgCopertina(obj.get("imgStringCover"));
        newProgettoPortfolio.setImgCopertinaId(obj.get("imgStringCoverId"));
        newProgettoPortfolio.setDescrizione(progettoPortfolioDto.getDescrizione());
        newProgettoPortfolio.setImg1(obj.get("imgString01"));
        newProgettoPortfolio.setImg1Id(obj.get("imgString01Id"));
        newProgettoPortfolio.setImg2(obj.get("imgString02"));
        newProgettoPortfolio.setImg2Id(obj.get("imgString02Id"));
        newProgettoPortfolio.setImg3(obj.get("imgString03"));
        newProgettoPortfolio.setImg3Id(obj.get("imgString03Id"));
        newProgettoPortfolio.setData(new Date());
        return progettoPortfolioRepository.save(newProgettoPortfolio);
    }

    public ProgettoPortfolio putProgettoPortfolio (ProgettoPortfolioDto progettoPortfolioDto, MultipartFile imgCoverFile,MultipartFile img01File, MultipartFile img02File,  MultipartFile img03File) throws RuntimeException, IOException, NotFoundException {
        ProgettoPortfolio putProgettoPortfolio = progettoPortfolioRepository.findById(progettoPortfolioDto.getId()).orElseThrow(() -> new NotFoundException("Progetto con id " + progettoPortfolioDto.getId() + " non trovato"));
        if (progettoPortfolioDto.getNomeProgetto() !=null && !progettoPortfolioDto.getNomeProgetto().isEmpty()) {
            putProgettoPortfolio.setNomeProgetto(progettoPortfolioDto.getNomeProgetto());
        }
        if (progettoPortfolioDto.getDescrizione() !=null && !progettoPortfolioDto.getDescrizione().isEmpty()) {
            putProgettoPortfolio.setDescrizione(progettoPortfolioDto.getDescrizione());
        }
        if (imgCoverFile !=null && !imgCoverFile.isEmpty()) {
            imgUploaderService.deleteImage(putProgettoPortfolio.getImgCopertinaId());
            HashMap<String, String> obj = imgUploaderService.uploadImage(imgCoverFile);
            putProgettoPortfolio.setImgCopertina(obj.get("imageUrl"));
            putProgettoPortfolio.setImgCopertinaId(obj.get("public_id"));
        }
        if (img01File !=null && !img01File.isEmpty()) {
            putProgettoPortfolio.setImg1(progettoPortfolioDto.getImg1());
            imgUploaderService.deleteImage(putProgettoPortfolio.getImg1Id());
            HashMap<String, String> obj = imgUploaderService.uploadImage(img01File);
            putProgettoPortfolio.setImg1(obj.get("imageUrl"));
            putProgettoPortfolio.setImg1Id(obj.get("public_id"));
        }
        if (img02File !=null && !img02File.isEmpty()) {
            putProgettoPortfolio.setImg2(progettoPortfolioDto.getImg2());
            putProgettoPortfolio.setImg2(progettoPortfolioDto.getImg2());
            imgUploaderService.deleteImage(putProgettoPortfolio.getImg2Id());
            HashMap<String, String> obj = imgUploaderService.uploadImage(img02File);
            putProgettoPortfolio.setImg2(obj.get("imageUrl"));
            putProgettoPortfolio.setImg2Id(obj.get("public_id"));
        }
        if (img03File !=null && !img03File.isEmpty()) {
            putProgettoPortfolio.setImg3(progettoPortfolioDto.getImg3());
            putProgettoPortfolio.setImg3(progettoPortfolioDto.getImg1());
            imgUploaderService.deleteImage(putProgettoPortfolio.getImg3Id());
            HashMap<String, String> obj = imgUploaderService.uploadImage(img03File);
            putProgettoPortfolio.setImg3(obj.get("imageUrl"));
            putProgettoPortfolio.setImg3Id(obj.get("public_id"));
        }
        if (progettoPortfolioDto.getData() !=null ) {
            putProgettoPortfolio.setData(progettoPortfolioDto.getData());
    }
        progettoPortfolioRepository.save(putProgettoPortfolio);
        return putProgettoPortfolio;
    }

    public void deleteProgettoPortfolio (int id) throws IOException{
        ProgettoPortfolio progettoPortfolio = progettoPortfolioRepository.findById(id).orElseThrow(() -> new NotFoundException("Progetto con id " + id + " non trovato"));
        imgUploaderService.deleteImage(progettoPortfolio.getImgCopertinaId());
        imgUploaderService.deleteImage(progettoPortfolio.getImg1());
        imgUploaderService.deleteImage(progettoPortfolio.getImg2());
        imgUploaderService.deleteImage(progettoPortfolio.getImg3());
        progettoPortfolioRepository.delete(progettoPortfolio);
    }


}
