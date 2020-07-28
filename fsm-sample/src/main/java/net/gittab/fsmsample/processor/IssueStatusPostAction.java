package net.gittab.fsmsample.processor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import net.gittab.fsmsample.annotation.PostAction;
import net.gittab.fsmsample.domain.Issue;
import net.gittab.fsmsample.enums.ActionCode;
import net.gittab.fsmsample.repository.IssueRepository;

/**
 * IssueStatusAction.
 *
 * @author xiaohua zhou
 * @date 2020/7/28 1:18 下午
 **/
@Slf4j
@Component
public class IssueStatusPostAction extends AbstractAction{

    @Autowired
    private IssueRepository issueRepository;

    @PostAction(code = ActionCode.ASSIGN_USER, desc = "issue status update")
    public void assignUser(Long issueId, Long userId){
      log.info("assign issue {} to user {}", issueId, userId);

      Issue issue = this.issueRepository.getById(issueId);

      // 将 issue assign 给指定的用户

    }
}
