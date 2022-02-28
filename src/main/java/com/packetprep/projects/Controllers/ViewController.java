package com.packetprep.projects.Controllers;

import com.packetprep.projects.Service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
    public String uploadFile(@RequestParam("myFile") MultipartFile myFile) throws IOException {
//        if (!myfile.isEmpty() && isRepositoryExists()) {
//            String filename = myfile.getOriginalFilename();
//            OutputStream outputStream  = new FileOutputStream(uploadLocation+filename);
//            outputStream.write(myfile.getBytes());
//            Files files = new Files(filename, myfile.getContentType());
//            fileRepository.save(files);
//        }
        try{
            fileService.save(myFile);
            fileService.scan();
            return "File Uploaded";
        }catch(Exception e){
            return "File Not Uploaded";
        }

    }
    @GetMapping("/scan")
    public void scan() {
        fileService.scan();
    }
//    public boolean isRepositoryExists() {
//        File file = new File(uploadLocation);
//        if (!file.exists()) {
//            if (file.mkdir()) {
//                System.out.println("Directory is created!");
//                return true;
//            } else {
//                System.out.println("Failed to create directory!");
//                return false;
//            }
//        } else return true;
//    }
}
