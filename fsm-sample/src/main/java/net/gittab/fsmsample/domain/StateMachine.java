package net.gittab.fsmsample.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author rookiedev
 */
@Data
@Entity
@Table(name = "fsm_state_machines")
public class StateMachine {

    @Id
    @GeneratedValue(generator="JDBC")
    private Long id;

    private String name;

    private Integer status;

    private Long projectId;

    private String description;
}
