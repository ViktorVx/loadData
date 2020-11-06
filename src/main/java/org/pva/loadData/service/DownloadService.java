package org.pva.loadData.service;

import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class DownloadService {

    private static final Integer defaultRows = 5;

    public byte[] makeZipFile(Integer number) {
        String fileName = UUID.randomUUID().toString().concat(".txt");
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ZipOutputStream zos = new ZipOutputStream(baos)) {
            ZipEntry entry = new ZipEntry(fileName);
            zos.putNextEntry(entry);
            int bound = number == null ? defaultRows : number + 1;
            for (int i = 1; i < bound; i++) {
                byte[] bytes = String.format("%s;Client%d;%d%n", UUID.randomUUID(), i, 100).getBytes();
                zos.write(bytes);
            }
            zos.closeEntry();
            zos.close();
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
