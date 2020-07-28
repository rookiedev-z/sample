package net.gittab.fsmsample.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author xiaohua zhou
 */
@Data
@Entity
@Table(name = "fsm_state_machine_transforms")
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
