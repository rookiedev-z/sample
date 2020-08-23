package net.gittab.fsmsample.service;

import lombok.extern.slf4j.Slf4j;
import net.gittab.fsmsample.FsmSampleApplicationTests;
import net.gittab.fsmsample.domain.Issue;
import net.gittab.fsmsample.repository.IssueRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * IssueServiceTest.
 *
 * @author rookiedev
 * @date 2020/7/28 3:27 下午
 **/
@Slf4j
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
    public void updateIssueStatus1(){
        // 1 -> 3 by 3, 3 -> 4 by 5, 4 -> 3 by 7, all -> 1 by 14
        Long expectStatusId = 4L;
        Long projectId = 1L;
        Long issueId = 1L;
        Long transformId = 5L;
        long start = System.currentTimeMillis();
        this.issueService.updateIssueStatus(projectId, issueId, transformId);
        Issue issue = this.issueRepository.getById(issueId);
        assert expectStatusId.equals(issue.getStatusId());
        log.info("execute transform spend {} ms", (System.currentTimeMillis() - start));

        // 1 -> 3 by 3, 3 -> 4 by 5, 4 -> 3 by 7, all -> 1 by 14
        Long expectStatusId1 = 3L;
        Long projectId1 = 1L;
        Long issueId1 = 1L;
        Long transformId1 = 7L;
        long start1 = System.currentTimeMillis();
        this.issueService.updateIssueStatus(projectId1, issueId1, transformId1);
        Issue issue1 = this.issueRepository.getById(issueId1);
        assert expectStatusId1.equals(issue1.getStatusId());
        log.info("execute transform spend {} ms", (System.currentTimeMillis() - start1));

    }

    @Test
    public void updateIssueStatus2(){

//        // 1 -> 3 by 3, 3 -> 4 by 5, 4 -> 3 by 7, all -> 1 by 14
        Long expectStatusId1 = 4L;
        Long projectId1 = 1L;
        Long issueId1 = 1L;
        Long transformId1 = 5L;
        long start1 = System.currentTimeMillis();
        this.issueService.updateIssueStatus(projectId1, issueId1, transformId1);
        Issue issue1 = this.issueRepository.getById(issueId1);
        assert expectStatusId1.equals(issue1.getStatusId());
        log.info("execute transform spend {} ms", (System.currentTimeMillis() - start1));
    }
}
