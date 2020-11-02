package org.pva.loadData.controller;

import org.pva.loadData.service.DownloadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

@Controller
@RequestMapping("file")
public class LoadDataController {

    private DownloadService downloadService;

    @GetMapping(path = "download/{number}", produces = "application/zip")
    public byte[] download(@PathVariable Integer number) {
        return downloadService.makeZipFile(number);
    }

    @PostMapping(path = "upload", consumes = "multipart/form-data")
    public String upload(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(new File("something-uploaded.zip")));
                stream.write(bytes);
                stream.close();
                return "Вы удачно загрузили!";
            } catch (Exception e) {
                return "Вам не удалось загрузить  => " + e.getMessage();
            }
        } else {
            return "Вам не удалось загрузить потому что файл пустой.";
        }
        //todo create zip uploading
        // unzipping
        // to database storing
    }

    @Autowired
    public void setDownloadService(DownloadService downloadService) {
        this.downloadService = downloadService;
    }
}
