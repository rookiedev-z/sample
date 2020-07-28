package net.gittab.fsmsample.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * Issue.
 *
 * @author xiaohua zhou
 * @date 2020/7/28 1:26 下午
 **/
@Data
@Entity
@Table(name = "fsm_issues")
public class Issue {

    @Id
    @GeneratedValue(generator="JDBC")
    private Long id;

    private Long projectId;

    private String num;

    private String summary;

    private Long statusId;

    private Long assigneeId;

    private String description;
}
