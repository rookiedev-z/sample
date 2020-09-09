package net.gittab.basic.enums;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;

/**
 * Main.
 *
 * @author rookiedev
 * @date 2020/9/9 3:23 下午
 **/
public class Main {

    public static void main(String[] args) {
        Issue issue = new Issue();
        issue.setId(1L);
        // compare result false
        System.out.println(issue.getStatus() == Issue.StatusEnum.DONE);

        // throw NPE
        /// System.out.println(issue.getCode());
        // throw NPE
        /// System.out.println(issue.getStatus().equals(Issue.StatusEnum.DONE));

        // equals 的内部实现就是 ==，比较的是引用的地址是否相等，同时 final 修饰
//        public final boolean equals(Object other) {
//            return this==other;
//        }

        issue.setStatus(Issue.StatusEnum.TODO);
        // compare result false
        System.out.println(issue.getStatus().equals(TestFlowEnum.TODO));
        // compile error
        /// System.out.println(issue.getStatus() == TestFlowEnum.TODO);
        issue.setStatus(Issue.StatusEnum.READY_TO_DEPLOY);

        System.out.println(issue.readyToDeploy());

        // EnumSet
        Issue issue1 = new Issue(Issue.StatusEnum.TODO);
        Issue issue2 = new Issue(Issue.StatusEnum.DOING);
        Issue issue3 = new Issue(Issue.StatusEnum.DOING);
        Issue issue4 = new Issue(Issue.StatusEnum.DONE);
        List<Issue> issues = Arrays.asList(issue1, issue2, issue3, issue4);
        List<Issue> undoneIssues = Issue.getUndoneIssue(issues);
        System.out.println(undoneIssues.size());
        undoneIssues.forEach(item -> System.out.println(item.toString()));

        // todo status issue size is 1, doing status issue size is 2, done status issue size is 1
        EnumMap<Issue.StatusEnum, List<Issue>> map = Issue.groupByStatus(issues);
        System.out.println(String.format("todo status issue size is %s, doing status issue size is %s, done status issue size is %s", map.get(Issue.StatusEnum.TODO).size(), map.get(Issue.StatusEnum.DOING).size(), map.get(Issue.StatusEnum.DONE).size()));

        issue.deploy();

        // issue current status is DONE
        System.out.println("issue current status is " + issue.getStatus());

    }

    private static int switchTest(Issue.StatusEnum status){

        switch (status){
            case TODO:
                return 0;
            case DOING:
                return 1;
            case DONE:
                return 2;
            default:
                return -1;
        }
    }
}
