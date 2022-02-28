package com.packetprep.projects.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.lang.reflect.*;

@Service
public class FileService {

    @Value("${file.upload.location}")
    private String uploadPath;


    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(Paths.get(uploadPath));
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload folder!");
        }
    }

    public void save(MultipartFile file) {
        try {
            Path root = Paths.get(uploadPath);
            if (!Files.exists(root)) {
                init();
            }
            Files.copy(file.getInputStream(), root.resolve(file.getOriginalFilename()));
//


        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

    public void scan(){
//        Collection<File> all = new ArrayList<File>();
//        addTree(this.uploadPath, all);
//        System.out.println(all);
        File actual = new File(uploadPath + "/CRUD");
        for( File f : actual.listFiles()){
//            System.out.println( f.getName() );
            if(f.listFiles() != null){
                scanDirectory(f);
            }else{
                scanClass(f);
            }
        }
    }
    public void scanDirectory(File f){
//        Collection<File> all = new ArrayList<File>();
//        addTree(this.uploadPath, all);
//        System.out.println(all);
        File actual = new File(uploadPath + "/CRUD");
        for( File fl : f.listFiles()){
//            System.out.println( fl.getName() );
            if(fl.listFiles() != null) {
                scanDirectory(fl);
            }else{
                scanClass(fl);
            }
        }
    }
    public void scanClass(File f){
        try{

            String name = f.getName();
            int j=0;
            boolean flag = false;
            for(int i=0;i<name.length();i++){
                if(name.charAt(i) == '.'){
                    j=i;
                    flag = true;
                    break;
                }
            }
            if(flag == true){

                String last= name.substring(j);
                String first= name.substring(0,j);
//                System.out.println(first);
                if(last.equalsIgnoreCase(".java")){
                    System.out.println(first);
                    BufferedReader br
                            = new BufferedReader(new FileReader(f));
//                    String st;
//                    while ((st = br.readLine()) != null)
//
//                        System.out.println(st);

                }
            }


//            if(f.listFiles() == null){
//                String thisClassname = f.getName();
//                System.out.println(thisClassname);
//            }
//            Class thisClass = thisClassname.class;
        } catch (Throwable e) {
            System.err.println(e);
            return;
        }
    }

//    public void addTree(Path directory, Collection<Path> all)
//            throws IOException {
//        try (DirectoryStream<Path> ds = Files.newDirectoryStream(directory)) {
//            for (Path child : ds) {
//                all.add(child);
//                if (Files.isDirectory(child)) {
//                    addTree(child, all);
//                }
//            }
//        }
//    }
//    public void testlinux() throws IOException {
//
//        try {
//
//            ProcessBuilder pb = new ProcessBuilder("/usr/local/bin/wkhtmltopdf"," http://www.yahoo.com /tmp/yahoo.pdf");
//
//            pb.start();
//
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//    }
}
