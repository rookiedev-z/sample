package net.gittab.fsmsample.service;

import net.gittab.fsmsample.domain.Issue;

/**
 * @author xiaohua zhou
 */
public interface IssueService {

    void createIssue(Long projectId, Issue issue);

    void updateIssueStatus(Long projectId, Long issueId, Long transformId);

}
