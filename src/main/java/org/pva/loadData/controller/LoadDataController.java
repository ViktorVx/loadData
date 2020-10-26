package org.pva.loadData.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping("file")
public class LoadDataController {

    @GetMapping(path = "download/{number}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity download(@PathVariable Integer number) {
        String s = IntStream.range(1, number == null ? 5 : number + 1)
                .mapToObj(i -> String.format("%s;Client%d;%d", UUID.randomUUID(), i, 100))
                .collect(Collectors.joining("\n"));
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header("Content-disposition", "attachment; filename=clients.txt")
                .body(s);
    }

    @RequestMapping(path = "upload")
    public ResponseEntity upload() {
        return new ResponseEntity(null, HttpStatus.OK);
    }
}
