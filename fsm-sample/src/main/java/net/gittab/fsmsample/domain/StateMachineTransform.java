package net.gittab.fsmsample.domain;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "fsm_state_machine_nodes")
public class StateMachineTransform {

    @Id
    @GeneratedValue(generator="JDBC")
    private Long id;

    private String name;

    private Long stateMachineId;

    private Long startNodeId;

    private Long endNodeId;

    private Integer type;

    private String description;
}
