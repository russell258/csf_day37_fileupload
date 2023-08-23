package sg.edu.nus.iss.csf_day37_fileupload.controllers;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import sg.edu.nus.iss.csf_day37_fileupload.repositories.BlobRepository;

@Controller
@RequestMapping
public class UploadController {
    
    @Autowired
    private BlobRepository blobRepo;

    @PostMapping(path="/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ModelAndView postUpload(@RequestPart String description, @RequestPart MultipartFile myfile){

    ModelAndView mav = new ModelAndView();
        try{
            String mediaType = myfile.getContentType();
            InputStream is = myfile.getInputStream();
            blobRepo.upload(description, mediaType,is);
        }catch (IOException ex){
            mav.setStatus(HttpStatusCode.valueOf(500));
            mav.setViewName("ERROR");
            mav.addObject("error", ex.getMessage());
            return mav;
        }




        mav.addObject("controlName",myfile.getName());
        mav.addObject("fileName", myfile.getOriginalFilename());
        mav.addObject("mediaType", myfile.getContentType());
        mav.addObject("fileSize",myfile.getSize());
        mav.setViewName("upload");
        mav.setStatus(HttpStatusCode.valueOf(200));

        return mav;
    }

}
