package com.pz.mysociety.common.util;

import com.pz.mysociety.common.constant.DocumentPath;
import lombok.NoArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.scheduling.annotation.Async;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class FileHandlingUtil {


    public static boolean fileUpload(String photoIdBase64, String fileName, String filePath, String existingFile)
    {
        boolean status=false;
        try{
           if(Functions.nonNullString(existingFile)) {
           Path destinationFile = Paths.get(DocumentPath.UPLOAD_DIR_DOCUMENT, existingFile);
           Files.deleteIfExists(destinationFile);
        }
            byte[] decodedImg = Base64.getDecoder().decode(photoIdBase64.getBytes(StandardCharsets.UTF_8));
            Path destinationFile = Paths.get(filePath, fileName);
            String a = "Hunter";
            Path b = Paths.get(filePath, a);
            Files.createDirectories(b);
            Files.write(destinationFile, decodedImg);
            status=true;
        } catch (IOException e){
            e.printStackTrace();
        }

        return status;
    }

    public static String fileUploadAndDir(String photoIdBase64, String directory, String fileName, String filePath, String existingFile)
    {
        try{
            if(Functions.nonNullString(existingFile)) {
                Path destinationFile = Paths.get(DocumentPath.UPLOAD_DIR_DOCUMENT, existingFile);
                Files.deleteIfExists(destinationFile);
            }
            byte[] decodedImg = Base64.getDecoder().decode(photoIdBase64.getBytes(StandardCharsets.UTF_8));
            Path createDir = Paths.get(filePath, directory);
            Path destinationFile = Paths.get(createDir.toString(), fileName);

            if(!Files.isDirectory(createDir)){
                Files.createDirectories(createDir);
            }
            Files.write(destinationFile, decodedImg);
            return destinationFile.toString();
        } catch (IOException e){
            e.printStackTrace();
        }

        return "Error";
    }

    public static String createDirectory(String filePath, String societyName, String year, String month) {

        Path societyDir = Paths.get(filePath, societyName);
        Path createDir = Paths.get(societyDir.toString(), year);
        Path monthDir = Paths.get(createDir.toString(), month);

        if(!Files.isDirectory(societyDir)){
            try {
                Files.createDirectories(societyDir);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(!Files.isDirectory(createDir)){
            try {
                Files.createDirectories(createDir);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(!Files.isDirectory(monthDir)){
            try {
                Files.createDirectories(monthDir);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return monthDir + "/";

    }

    public static boolean deleteFile(String directory, String existingFile) {
        boolean status = false;
            if (Functions.nonNullString(existingFile)) {
                Path destinationFile = Paths.get(directory, existingFile);
                status = true;
                try {
                    Files.deleteIfExists(destinationFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        return status;
    }

//    public void renameFile(String filePath, String directory, String oldName, String newName){
////        Path newDestinationFile = Paths.get(filePath, directory, newName);
//        Path oldFile = Paths.get(oldName);
//        try {
//            System.out.println(Thread.currentThread());
//            Thread.sleep(5000);
//            Files.move(oldFile, oldFile.resolveSibling(newName));
//            System.out.println("Done");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        File oldFile = new File(oldName);
//        File renameFile = new File(newDestinationFile.toString());
//        System.out.println(oldFile);
//        System.out.println(renameFile);
//       if(oldFile.renameTo(renameFile)) {System.out.println("Done");}else {System.out.println("Not");}
//    }

    public static void rename(String path, String dir, String old, String fileNew){

        new Thread(() -> {
            System.out.println(path + dir+ old+ fileNew);
            System.out.println(Thread.currentThread() + "Second");
//            String a = filePath;
//            String n = newName;
            Path oldFile = Paths.get(old);
            try {
                System.out.println(Thread.currentThread() + "Second");
                System.out.println(Thread.currentThread().getName());
                Thread.sleep(5000);
                Files.move(oldFile, oldFile.resolveSibling(fileNew));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
