package org.pva.loadData.repository;

import org.pva.loadData.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ClientRepositoryImpl implements ClientRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Client> findAll() {
        return jdbcTemplate.query(
                "SELECT " +
                        "id, login, balance " +
                        "FROM client",
                new ClientRowMapper());
    }

    @Override
    @Transactional
    public int[] batchUpdate(List<Client> clients) {
        return jdbcTemplate.batchUpdate("INSERT INTO clients(id, login, balance) VALUES (?, ?, ?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setString(1, clients.get(i).getId());
                        ps.setString(2, clients.get(i).getLogin());
                        ps.setLong(3, clients.get(i).getBalance());
                    }
                    @Override
                    public int getBatchSize() {
                        return clients.size();
                    }
                });
    }

    class ClientRowMapper implements RowMapper<Client> {

        @Override
        public Client mapRow(final ResultSet rs, final int rowNum) throws SQLException {
            final Client client = new Client();
            client.setId(rs.getString("id"));
            client.setLogin(rs.getString("login"));
            client.setBalance(rs.getLong("balance"));
            return client;
        }
    }
}
