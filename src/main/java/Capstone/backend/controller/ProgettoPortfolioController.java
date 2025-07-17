package Capstone.backend.controller;



import Capstone.backend.dto.ProgettoPortfolioDto;
import Capstone.backend.exception.NotFoundException;
import Capstone.backend.model.ProgettoPortfolio;
import Capstone.backend.service.ImageUploadService;
import Capstone.backend.service.ProgettoPortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping (path="/portfolio")
public class ProgettoPortfolioController {

    @Autowired
    private ProgettoPortfolioService PPService;

    @Autowired
    private ImageUploadService imageUploadService;

    @GetMapping("/all")
    public List<ProgettoPortfolio> getPortfolio() throws NotFoundException {
        return PPService.getPortfolio();
    }

    @GetMapping("/ID/{id}")
    public ProgettoPortfolio getProgettiPortfolio(@PathVariable int id) throws NotFoundException {
        return PPService.getProgettiPortfolio(id);
    }

    @PostMapping("/upload-with-data")
    public ResponseEntity<ProgettoPortfolio> uploadImageAndData(
            @RequestPart("img_cover") MultipartFile imgCoverFile,
            @RequestPart("img_01") MultipartFile img01File,
            @RequestPart("img_02") MultipartFile img02File,
            @RequestPart("img_03") MultipartFile img03File,
            @RequestPart("data") ProgettoPortfolioDto progettoPortfolioDto) throws IOException {
        Map<String,String> imgCover = imageUploadService.uploadImage(imgCoverFile);
        Map<String,String> img01 = imageUploadService.uploadImage(img01File);
        Map<String,String> img02 = imageUploadService.uploadImage(img02File);
        Map<String,String> img03 = imageUploadService.uploadImage(img03File);
        HashMap<String,  String> obj = new HashMap<>();
        obj.put("imgStringCover", imgCover.get("imageUrl"));
        obj.put("imgString01", img01.get("imageUrl"));
        obj.put("imgString02", img02.get("imageUrl"));
        obj.put("imgString03", img03.get("imageUrl"));
        obj.put("imgStringCoverId", imgCover.get("public_id"));
        obj.put("imgString01Id", img01.get("public_id"));
        obj.put("imgString02Id", img02.get("public_id"));
        obj.put("imgString03Id", img03.get("public_id"));


        return new ResponseEntity<>(PPService.saveProgettoPortfolio(progettoPortfolioDto, obj),HttpStatus.CREATED);
        }

        // annotazione che permette di distinguere le API per i diversi tipi di utenti
        // @PreAuthorize("admin")
        @PutMapping("/put")
        public ResponseEntity<ProgettoPortfolio> putPortfolio (
                @RequestPart(value = "data", required = false ) ProgettoPortfolioDto progettoPortfolioDto,
                @RequestPart(value = "img_cover", required = false) MultipartFile imgCoverFile,
                @RequestPart(value = "img_01", required = false) MultipartFile img01File,
                @RequestPart(value = "img_02", required = false) MultipartFile img02File,
                @RequestPart(value = "img_03", required = false) MultipartFile img03File
                ) throws IOException {
            ProgettoPortfolio progettoPortfolio = PPService.putProgettoPortfolio(progettoPortfolioDto, imgCoverFile, img01File, img02File, img03File);
            return new ResponseEntity<> (progettoPortfolio, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePortfolio (@PathVariable int id) throws NotFoundException, IOException{
        PPService.deleteProgettoPortfolio(id);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }
    }

