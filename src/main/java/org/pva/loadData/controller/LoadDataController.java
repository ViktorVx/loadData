package org.pva.loadData.controller;

import org.pva.loadData.service.DownloadService;
import org.pva.loadData.service.UploadService;
import org.pva.loadData.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("file")
public class LoadDataController {

    private DownloadService downloadService;
    private UploadService uploadService;

    @GetMapping(path = "download/{number}", produces = "application/zip")
    public byte[] download(@PathVariable Integer number) {
        return downloadService.makeZipFile(number);
    }

    @PostMapping(path = "upload", consumes = "multipart/form-data")
    public String upload(@RequestParam("file") MultipartFile file) {
        long start = System.nanoTime();
        try {
            uploadService.uploadData(file);
        } catch (IOException e) {
            return "File doesn't load: " + e.getMessage();
        }
        long end = System.nanoTime();
        return "Success load " + CommonUtils.nanosToMinSec(end - start);
    }

    @Autowired
    public void setDownloadService(DownloadService downloadService) {
        this.downloadService = downloadService;
    }

    @Autowired
    public void setUploadService(UploadService uploadService) {
        this.uploadService = uploadService;
    }
}
