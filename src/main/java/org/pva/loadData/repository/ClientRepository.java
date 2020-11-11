package org.pva.loadData.repository;

import org.pva.loadData.model.Client;

import java.util.List;

public interface ClientRepository {
    List<Client> findAll();
    int[] batchInsert(List<Client> clients);
}
