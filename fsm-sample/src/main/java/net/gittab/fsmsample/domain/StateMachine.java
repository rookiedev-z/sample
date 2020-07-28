package net.gittab.fsmsample.domain;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "fsm_state_machines")
public class StateMachine {

    @Id
    @GeneratedValue(generator="JDBC")
    private Long id;

    private String name;

    private Integer status;

    private String description;
}
