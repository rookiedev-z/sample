package net.gittab.fsmsample.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author xiaohua zhou
 */
@Data
@Entity
@Table(name = "fsm_status")
public class Status {

    @Id
    @GeneratedValue(generator="JDBC")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer type;

    private String description;
}
