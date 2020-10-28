package org.pva.loadData.controller;

import org.pva.loadData.service.DownloadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("file")
public class LoadDataController {

    private DownloadService downloadService;

    @GetMapping(path = "download/{number}", produces = "application/zip")
    @ResponseBody
    public byte[] download(@PathVariable Integer number) {
        return downloadService.makeZipFile(number);
    }

    @RequestMapping(path = "upload")
    public ResponseEntity upload() {
        return new ResponseEntity(null, HttpStatus.OK);
    }

    @Autowired
    public void setDownloadService(DownloadService downloadService) {
        this.downloadService = downloadService;
    }
}
