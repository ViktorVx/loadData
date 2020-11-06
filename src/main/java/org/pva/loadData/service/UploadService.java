package org.pva.loadData.service;

import org.pva.loadData.model.Client;
import org.pva.loadData.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Service
public class UploadService {

    @Value("${loadData.buffer.size}")
    private int bufferSize;

    @Value("${loadData.rowsBuffer.size}")
    private int rowsBufferSize;

    private ClientRepository clientRepository;

    public void uploadData(MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            byte[] bytes = file.getBytes();

            try(ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
                ZipInputStream zipStream = new ZipInputStream(byteStream)) {

                ZipEntry entry = zipStream.getNextEntry();

                StringBuilder s = new StringBuilder();
                byte[] buffer = new byte[bufferSize];
                int read;
                List<String> rowsBuffer = new ArrayList<>();
                while(entry != null) {
                    while ((read = zipStream.read(buffer, 0, bufferSize)) >= 0) {
                        s.append(new String(buffer, 0, read));
                        boolean endsNewLine = s.toString().endsWith("\n");
                        String[] rows = s.toString().split("\n");

                        rowsBuffer.addAll(endsNewLine ? List.of(rows) : List.of(rows).subList(0, rows.length - 1));
                        s = new StringBuilder(endsNewLine ? "" : rows[rows.length - 1]);

                        if (rowsBuffer.size() >= rowsBufferSize) {
                            persistRowsBuffer(rowsBuffer);
                            rowsBuffer.clear();
                        }
                    }
                    entry = zipStream.getNextEntry();
                }
                if (!rowsBuffer.isEmpty()) {
                    persistRowsBuffer(rowsBuffer);
                    rowsBuffer.clear();
                }
            } catch (Exception e) {
                throw e;
            }
        } else {
            throw new RuntimeException("File is empty");
        }
    }

    private void persistRowsBuffer(List<String> rowsBuffer) {
        List<Client> clients = new ArrayList<>();
        for (String s : rowsBuffer) {
            String[] parts = s.split(";");
            clients.add(new Client(parts[0], parts[1], Long.valueOf(parts[2])));
        }
        clientRepository.batchUpdate(clients);
    }

    @Autowired
    public void setClientRepository(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }
}
