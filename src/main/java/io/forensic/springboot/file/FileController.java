package io.forensic.springboot.file;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import io.forensic.springboot.Other.OtherService;

@RestController
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileStorageService fileStorageService;
    
    @Autowired
    private OtherService otherService;

    @PostMapping("/uploadFile")
    @CrossOrigin()
    public String uploadFile(@RequestParam("file") MultipartFile file) {
//    	System.out.println("TEST1");
        fileStorageService.storeFile(file);
        return "success";
    }
    
    @PostMapping("/api/search/excelfile")
    @CrossOrigin()
    public List<Object[]> searchByExcel(@RequestParam("file") MultipartFile file){
    	fileStorageService.storeFile(file);
    	return otherService.searchByExcelData("uploads/temp.xlsx");
    }

//    @PostMapping("/uploadMultipleFiles")
//    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
//        return Arrays.asList(files)
//                .stream()
//                .map(file -> uploadFile(file))
//                .collect(Collectors.toList());
//    }
}