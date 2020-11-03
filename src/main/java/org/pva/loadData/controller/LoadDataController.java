package org.pva.loadData.controller;

import org.pva.loadData.service.DownloadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@RestController
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
            byte[] bytes;
            try {
                bytes = file.getBytes();
            } catch (IOException e) {
                return "Вам не удалось загрузить  => " + e.getMessage();
            }

            try(ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
                ZipInputStream zipStream = new ZipInputStream(byteStream)) {

                ZipEntry entry = zipStream.getNextEntry();

                StringBuilder s = new StringBuilder();
                //todo make this variables as parameters in properties
                final int bufferSize = 55; //1024
                final int rowsBufferSize = 3; //1000
                byte[] buffer = new byte[bufferSize];
                int read;
                List<String> rowsBuffer = new ArrayList<>();
                while(entry != null) {
                    while ((read = zipStream.read(buffer, 0, bufferSize)) >= 0) {
                        s.append(new String(buffer, 0, read));
                        String[] rows = s.toString().split("\n");

                        List<String> cleanRows = read < bufferSize ?
                                List.of(rows) :
                                List.of(rows).subList(0, rows.length - 1);
                        rowsBuffer.addAll(cleanRows);

                        s = new StringBuilder(rows[rows.length - 1]);
                        //***
                        if (rowsBuffer.size() >= rowsBufferSize) {
                            persistRowsBuffer(rowsBuffer);
                            rowsBuffer.clear();
                        }
                        //***
                    }
                    entry = zipStream.getNextEntry();
                }
                return "Вы удачно загрузили!";
            } catch (Exception e) {
                return "Вам не удалось загрузить  => " + e.getMessage();
            }
        } else {
            return "Вам не удалось загрузить потому что файл пустой.";
        }
    }

    private void persistRowsBuffer(List<String> rowsBuffer) {
        for (String s : rowsBuffer) {
            System.out.println("*** ".concat(s));
        }
        System.out.println("Persist-----------------");
    }

    @Autowired
    public void setDownloadService(DownloadService downloadService) {
        this.downloadService = downloadService;
    }
}
