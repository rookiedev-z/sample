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
@Table(name = "fsm_state_machine_nodes")
public class StateMachineNode {

    @Id
    @GeneratedValue(generator="JDBC")
    private Long id;

    private Long stateMachineId;

    private Long statusId;

    private Integer type;

    private Long allTransformId;

    private String description;
}
