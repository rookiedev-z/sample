package net.gittab.fsmsample.processor;

import lombok.extern.slf4j.Slf4j;
import net.gittab.fsmsample.annotation.PostAction;
import net.gittab.fsmsample.domain.Issue;
import net.gittab.fsmsample.enums.ActionCode;
import net.gittab.fsmsample.repository.IssueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * IssueStatusAction.
 *
 * @author rookiedev
 * @date 2020/7/28 1:18 下午
 **/
@Slf4j
@Component
public class IssueStatusPostAction extends AbstractAction{

    @Autowired
    private IssueRepository issueRepository;

    @PostAction(code = ActionCode.ASSIGN_USER, desc = "issue status update")
    public void assignUser(Long issueId, Long assigneeId){

        log.info("assign issue {} to user {}", issueId, assigneeId);

        Issue issue = this.issueRepository.getById(issueId);

        // 将 issue assign 给指定的用户
        issue.setAssigneeId(assigneeId);
        this.issueRepository.save(issue);

    }
}
