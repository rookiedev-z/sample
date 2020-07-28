package net.gittab.fsmsample.conditions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.squirrelframework.foundation.fsm.Condition;

/**
 * IssueStatusCondition.
 *
 * @author rookie dev
 * @date 2020/7/28 23:17
 **/
@Slf4j
@Component
public class IssueStatusCondition implements Condition<Object> {

    @Override
    public boolean isSatisfied(Object o) {
        log.info("execute squirrel machine condition");
        return true;
    }

    @Override
    public String name() {
        return "issue_status_update_condition";
    }
}
