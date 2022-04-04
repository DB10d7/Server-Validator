package com.packetprep.projects.Controllers;

import com.packetprep.projects.Service.FileService;
import com.packetprep.projects.ShellCommands;
import org.json.JSONObject;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

@RestController
public class ViewController {

    @Autowired
    private FileService fileService;
    @Autowired
    private ShellCommands shellCommands;

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
          return fileService.save(myFile);

//            String fileName = myFile.getOriginalFilename();
//            return ("File Uploaded -----" + fileName);

        }catch(Exception e){
//            return "File Not Uploaded";
            HashMap<String,HashSet<String>> errMap = new HashMap<>();
            HashSet<String> errMessage = new HashSet<>();
            errMessage.add("File Not Uploaded!");
            errMap.put("error",errMessage);
            return errMap;
//
        }

    }
//    @GetMapping("/scan")
//    public HashMap<String, HashSet<String>> scan() {
//        return fileService.scan();
//    }



    @GetMapping("/copy")
    public void copyValidator() {
        fileService.putValidator();
    }

    @GetMapping("/getData")
    public ResponseEntity<String> getData() {

        String url= "http://localhost:8081/CRUD/Validate.jsp";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> result = restTemplate.getForEntity(url, String.class);
//        System.out.println(result);
        return result;
//        JSONParser parser = new JSONParser(result);
//        JSONObject json = (JSONObject) parser.parse(result);
//        return Arrays.asList(countries);
    }
}
