package Capstone.backend.service;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class ImageUploadService {

    @Autowired
    private Cloudinary cloudinary;

    // metodo upload su coloudinary
    public HashMap<String, String> uploadImage(MultipartFile file) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
        String imageUrl = (String) uploadResult.get("url");
        String publicid = (String) uploadResult.get("public_id");
        HashMap<String, String>  obj = new HashMap<String,String>();
        obj.put("imageUrl",imageUrl);
        obj.put("public_id",publicid);
        return obj;
    }

    public void deleteImage (String publicId) throws IOException{
        cloudinary.uploader().destroy(publicId,ObjectUtils.emptyMap());
    }
}
