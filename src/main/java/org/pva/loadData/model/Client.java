package org.pva.loadData.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class  Client {
    private String id;
    private String login;
    private Long balance;
}
