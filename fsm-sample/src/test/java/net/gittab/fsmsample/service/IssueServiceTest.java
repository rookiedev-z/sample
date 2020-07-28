package net.gittab.fsmsample.service;

import org.springframework.beans.factory.annotation.Autowired;

import net.gittab.fsmsample.FsmSampleApplicationTests;
import net.gittab.fsmsample.domain.Issue;
import net.gittab.fsmsample.repository.IssueRepository;
import org.junit.jupiter.api.Test;

/**
 * IssueServiceTest.
 *
 * @author xiaohua zhou
 * @date 2020/7/28 3:27 下午
 **/
public class IssueServiceTest extends FsmSampleApplicationTests {

    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private IssueService issueService;

    @Test
    public void createIssue(){
        Long projectId = 1L;
        String issueNum = "Issue-01";
        Issue issue = new Issue();
        issue.setNum(issueNum);
        issue.setSummary("issue 摘要");
        issue.setDescription("issue 描述信息");
        this.issueService.createIssue(projectId, issue);
    }

    @Test
    public void updateIssueStatus(){
        // 1 -> 3 by 3, 3 -> 4 by 5, 4 -> 3 by 7
        Long expectStatusId = 3L;
        Long projectId = 1L;
        Long issueId = 1L;
        Long transformId = 7L;
        this.issueService.updateIssueStatus(projectId, issueId, transformId);
        Issue issue = this.issueRepository.getById(issueId);
        assert expectStatusId.equals(issue.getStatusId());
    }
}
