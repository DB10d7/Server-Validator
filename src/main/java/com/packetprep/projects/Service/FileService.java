package com.packetprep.projects.Service;

import org.apache.commons.io.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import org.apache.commons.io.FileUtils;

import javax.annotation.PostConstruct;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

@Service
public class FileService {

    @Value("${file.upload.location}")
    private String uploadPath;


    @Value("${file.store.location}")
    private String storePath;

    @Value("${file.validator.location}")
    private String validatorPath;

    @Value("${file.deploy.location}")
    private String deployPath;

    public  HashSet<String> filenames = new HashSet<>();
    public  HashSet<String> jspFilenames = new HashSet<>();
    public  HashMap<String,HashSet<String>> map = new HashMap<>();

    @PostConstruct
    public void init() {
        try {

            Files.createDirectories(Paths.get(uploadPath));
            System.out.println("hello form init");
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload folder!");
        }
    }

    public HashMap<String,HashSet<String>> save(String fname) {

        try {
//            String fname = file.getOriginalFilename();
//
//
//
//            File webappFile = new File(uploadPath+"/"+fname);
//            if(webappFile.exists()){
//
//                webappFile.delete();
//            }
//            Path webapp = Paths.get(uploadPath);
//            if (!Files.exists(webapp)) {
//                init();
//
//            }
//            Files.copy(file.getInputStream(), webapp.resolve(file.getOriginalFilename()));
            String url = "https://s3-xplore.s3.ap-south-1.amazonaws.com/zip_practice/"+fname;
            BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
//            File webappFile = new File(uploadPath+"/"+fname);
//            if(webappFile.exists()){
//
//                webappFile.delete();
//            }
            Path webapp = Paths.get(uploadPath);
//            if (!Files.exists(webapp)) {
//                init();
//
//            }
            Files.copy(in, webapp.resolve(fname));
//            String url = "https://s3-xplore.s3.ap-south-1.amazonaws.com/zip_practice/"+fname;
//            System.out.println(url);
//            URL url2 = new URL(url);
////       File file = new File(https://s3-xplore.s3.ap-south-1.amazonaws.com/zip_practice/KrskyJZ7k9_krishnateja.war)
////        int i = url.lastIndexOf('/');
////        String fileName = url.substring(i+1);
////            FileUtils.copyURLToFile(url2, new File(uploadPath+"/"+fname));
//            File fil = new File(uploadPath+"/"+fname);
//            BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
////            try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
////                 FileOutputStream fileOutputStream = new FileOutputStream(fil)) {
////                byte dataBuffer[] = new byte[1024];
////                int bytesRead;
////                while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
////                    fileOutputStream.write(dataBuffer, 0, bytesRead);
////                }
//                Path webapp = Paths.get(uploadPath+"/"+fname);
////            if (!Files.exists(webapp)) {
////                init();
////
////            }
////            Files.copy(in, webapp.resolve(fname));
//                Files.copy(in, webapp, StandardCopyOption.REPLACE_EXISTING);
//            } catch (IOException e) {
//                // handle exception
//            }
//            File theDir = new File(deployPath+"/"+fname);
//            if(theDir.exists()){
//                FileUtils.deleteDirectory(theDir);
//                theDir.delete();
//            }
//            if (!theDir.exists()){
//                theDir.mkdir();
//            }
//
//            int index = fname.indexOf('.');
//            Path root = Paths.get(deployPath+"/"+fname);
//            if (!Files.exists(root)) {
//                init();
//
//            }
//            Files.copy(file.getInputStream(), root.resolve(file.getOriginalFilename()));


//            display(file.getOriginalFilename());
            int index = fname.indexOf('.');
            String fileName = fname.substring(0,index);
            long start = System.currentTimeMillis();
            long end = start + 15*1000;
            while (System.currentTimeMillis() < end) {
                // Some expensive operation on the item.
            }
            scan(fileName);
            return map;

        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e);


        }
    }
    public void downloadFile(String fileName) throws IOException {
       String url = "https://s3-xplore.s3.ap-south-1.amazonaws.com/zip_practice/"+fileName;
       System.out.println(url);
        URL url2 = new URL(url);
//       File file = new File(https://s3-xplore.s3.ap-south-1.amazonaws.com/zip_practice/KrskyJZ7k9_krishnateja.war)
//        int i = url.lastIndexOf('/');
//        String fileName = url.substring(i+1);
        FileUtils.copyURLToFile(url2, new File(deployPath+"/"+fileName));
    }

    public void scan(String fname){


        File actual = new File(uploadPath+"/"+fname);
        for( File f : actual.listFiles()){

            if(f.listFiles() != null){
                scanDirectory(f);
            }else{
                scanClass(f);
            }
        }
        HashSet<String> names = new HashSet<>();
        for(String s : filenames){
            names.add(s);
        }
        HashSet<String> jspnames = new HashSet<>();
        for(String s: jspFilenames){
            jspnames.add(s);
        }
        map.put("servlets",names);
        map.put("jsp",jspnames);
        this.filenames.clear();
        this.jspFilenames.clear();
//        return map;
    }
    public void scanDirectory(File f){

//        File actual = new File(uploadPath + "/CRUD");
        for( File fl : f.listFiles()){

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

                if(last.equalsIgnoreCase(".java")){

                    filenames.add(first);


                }else if(last.equalsIgnoreCase(".jsp")){

                    jspFilenames.add(first);


                }
            }

        } catch (Throwable e) {
            System.err.println(e);
            return;
        }
    }

    public void display(String fileName) throws Exception {


        boolean isWindows = System.getProperty("os.name")
                .toLowerCase().startsWith("windows");
        if(isWindows){
            ProcessBuilder builder = new ProcessBuilder(
                    "cmd.exe", "/c", "cd \"C:\\Users\\Packetprep\\Desktop\\Deploy\\"+ fileName+"\" && jar -xvf "+ fileName);



            builder.redirectErrorStream(true);
            Process p = builder.start();

            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while (true) {
                line = r.readLine();
                if (line == null) { break; }
                System.out.println(line);
            }

            p.destroyForcibly();
            System.out.println(p.isAlive());


            long start = System.currentTimeMillis();
            long end = start + 3*1000;
            while (System.currentTimeMillis() < end) {
                // Some expensive operation on the item.
            }
            String pack = "\"cmd.exe\", \"/c\", \"cd \\\"C:\\\\Users\\Packetprep\\Desktop\\Deploy\\"+ fileName+"\\ && jar -cvf verified"+ fileName+" *";
            System.out.println(pack);
            ProcessBuilder builder1 = new ProcessBuilder(
                    "cmd.exe", "/c", "cd \"C:\\Users\\Packetprep\\Desktop\\Deploy\\"+fileName+"\" && jar -cvf verified"+ fileName +" *");




            builder1.redirectErrorStream(true);
            Process p1 = builder1.start();
            System.out.println(p1.isAlive());

            BufferedReader r1 = new BufferedReader(new InputStreamReader(p1.getInputStream()));
            String line1;
            while (true) {
                line1 = r1.readLine();
                if (line1 == null) { break; }
                System.out.println(line1);
            }
            p1.destroyForcibly();
            System.out.println(p1.isAlive());
        }else{
            ProcessBuilder builder = new ProcessBuilder(
                    "sh", "-c", "cd \"var\\lib\\warFileStorage"+ fileName+"\" && jar -xvf "+ fileName);



            builder.redirectErrorStream(true);
            Process p = builder.start();

            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while (true) {
                line = r.readLine();
                if (line == null) { break; }
                System.out.println(line);
            }

            p.destroyForcibly();
            System.out.println(p.isAlive());


        }


    }


}
