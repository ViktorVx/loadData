package org.pva.loadData.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.stream.IntStream;

@RestController
@RequestMapping("file")
public class LoadDataController {

    private static final Integer defaultRows = 5;

    @GetMapping(path = "download/{number}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity download(@PathVariable Integer number) throws IOException {
        String fileName = UUID.randomUUID().toString().concat(".txt");

        Files.write(Paths.get(fileName),
                (Iterable<String>) IntStream.range(1, number == null ? defaultRows : number + 1)
                        .mapToObj(i -> String.format("%s;Client%d;%d", UUID.randomUUID(), i, 100))::iterator
                );
        // todo create zip file in memory and remove file on disk
        // todo try NOT to save txt file to the disk

        return ResponseEntity.ok("ok ".concat(String.valueOf(number)));
    }

    @RequestMapping(path = "upload")
    public ResponseEntity upload() {
        return new ResponseEntity(null, HttpStatus.OK);
    }
}
