package com.packetprep.projects.Controllers;

import com.packetprep.projects.Service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

@RestController
public class ViewController {

    @Autowired
    private FileService fileService;


//    @Value("${file.upload.location}")
//    private String uploadLocation;

    @GetMapping("/")
    public String viewIndexPage(Model model) {
        model.addAttribute("header", "Maven Generate War");
        return "index";
    }

    @GetMapping("/uploadFile/{fileName}")
    public HashMap<String, HashSet<String>> uploadFile(@PathVariable String fileName) throws IOException {
        try{
//          return fileService.save(myFile);
            return fileService.save(fileName);


//            String fileName = myFile.getOriginalFilename();
//            return ("File Uploaded -----" + fileName);

        }catch(Exception e){

            HashMap<String,HashSet<String>> errMap = new HashMap<>();
            HashSet<String> errMessage = new HashSet<>();
            errMessage.add(e.getMessage());
            errMap.put("error",errMessage);
            return errMap;


        }

    }
    @GetMapping("/delete/{fileName}")
    public String deleteFile(@PathVariable String fileName) throws IOException {
        return  fileService.deleteFile(fileName);

    }

    @GetMapping("/getData")
    public ResponseEntity<String> getData() {

        String url= "http://localhost:8081/CRUD/Validate.jsp";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> result = restTemplate.getForEntity(url, String.class);
        return result;

    }
}
