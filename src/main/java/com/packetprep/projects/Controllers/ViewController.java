package com.packetprep.projects.Controllers;

import com.packetprep.projects.Service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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

    @PostMapping("/uploadFile")
    public HashMap<String, HashSet<String>> uploadFile(@RequestParam("myFile") MultipartFile myFile) throws IOException {
        try{
//          return fileService.save(myFile);
            return fileService.save(myFile);


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


    @GetMapping("/getData")
    public ResponseEntity<String> getData() {

        String url= "http://localhost:8081/CRUD/Validate.jsp";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> result = restTemplate.getForEntity(url, String.class);
        return result;

    }
}
