package com.packetprep.projects.Service;

import org.apache.commons.io.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    public static HashSet<String> filenames = new HashSet<>();
    public static HashSet<String> jspFilenames = new HashSet<>();
    public  HashMap<String,HashSet<String>> map = new HashMap<>();

    @PostConstruct
    public void init() {
        try {

            Files.createDirectories(Paths.get(deployPath));
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload folder!");
        }
    }

    public HashMap<String,HashSet<String>> save(MultipartFile file) {
        try {
            String fname = file.getOriginalFilename();


            File theDir = new File(deployPath+"/"+fname);
            if (!theDir.exists()){
                theDir.mkdirs();
            }

            int index = fname.indexOf('.');
            Path root = Paths.get(deployPath+"/"+fname);
            if (!Files.exists(root)) {
                init();

            }
            Files.copy(file.getInputStream(), root.resolve(file.getOriginalFilename()));

            display(file.getOriginalFilename());
            scan(fname);
            return map;

        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());

        }
    }
    public void putValidator(){
        try{
            String name="hello";
            File source = new File(validatorPath);

            for(File f : source.listFiles()){
                if(f.listFiles() != null){
                    File dest = new File(deployPath + "\\WEB-INF\\classes\\com");
                    FileUtils.copyDirectory(source, dest);
                }else{
                    File dest = new File(deployPath);
                    FileUtils.copyFileToDirectory(f, dest);
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public void scan(String fname){

//        File actual = new File(deployPath + "\\WEB-INF\\classes\\com");
        File actual = new File(deployPath+"/"+fname);
        for( File f : actual.listFiles()){

            if(f.listFiles() != null){
                scanDirectory(f);
            }else{
                scanClass(f);
            }
        }

        map.put("servlets",filenames);
        map.put("jsp",jspFilenames);
//        filenames.clear();
//        jspFilenames.clear();
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
//                    System.out.println(first);
                    filenames.add(first);
//                    BufferedReader br
//                            = new BufferedReader(new FileReader(f));
//
//                    String st;
//                    // Condition holds true till
//                    // there is character in a string
//                    while ((st = br.readLine()) != null)
//
//                        // Print the string
//                        System.out.println(st);

                }else if(last.equalsIgnoreCase(".jsp")){
//                    System.out.println(first);
                    jspFilenames.add(first);
//                    BufferedReader br
//                            = new BufferedReader(new FileReader(f));
//
//                    String st;
//                    // Condition holds true till
//                    // there is character in a string
//                    while ((st = br.readLine()) != null)
//
//                        // Print the string
//                        System.out.println(st);

                }
            }

        } catch (Throwable e) {
            System.err.println(e);
            return;
        }
    }

    public void display(String fileName) throws Exception {

        String extract = "\"cmd.exe\", \"/c\", \"cd \"C:\\Users\\Packetprep\\Desktop\\Deploy\\"+ fileName+"\\ && jar -xvf "+ fileName;
        System.out.println(extract);
        ProcessBuilder builder = new ProcessBuilder(
                "cmd.exe", "/c", "cd \"C:\\Users\\Packetprep\\Desktop\\Deploy\\"+ fileName+"\" && jar -xvf "+ fileName);

//        ProcessBuilder builder = new ProcessBuilder(
//                "cmd.exe", "/c", "cd \""+ deployPath + "\""+fileName+" && jar -xvf "+ fileName);

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

//        putValidator();
        long start = System.currentTimeMillis();
        long end = start + 3*1000;
        while (System.currentTimeMillis() < end) {
            // Some expensive operation on the item.
        }
        String pack = "\"cmd.exe\", \"/c\", \"cd \\\"C:\\\\Users\\Packetprep\\Desktop\\Deploy\\"+ fileName+"\\ && jar -cvf verified"+ fileName+" *";
        System.out.println(pack);
        ProcessBuilder builder1 = new ProcessBuilder(
                "cmd.exe", "/c", "cd \"C:\\Users\\Packetprep\\Desktop\\Deploy\\"+fileName+"\" && jar -cvf verified"+ fileName +" *");


//        ProcessBuilder builder1 = new ProcessBuilder(
//                "cmd.exe", "/c","cd \""+ deployPath + "\""+fileName+" && jar -cvf verified"+ fileName +" *");

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

    }


}
