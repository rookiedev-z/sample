package net.gittab.basic.enums;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Issue.
 *
 * @author rookiedev
 * @date 2020/9/9 3:36 下午
 **/
@Data
@ToString
@NoArgsConstructor
public class Issue {

    private static EnumSet<StatusEnum> undoneSet = EnumSet.of(StatusEnum.TODO, StatusEnum.DOING);

    private Long id;

    private IssueType type;

    private StatusEnum status;

    public Issue(StatusEnum status){
        this.status = status;
    }

    public boolean readyToDeploy(){
        return getStatus().readyToDeploy();
    }

    public void deploy(){
        if(readyToDeploy()){
            DeployEnvSingleton.getInstance().getDeployEnv().deploy(this);
            this.setStatus(StatusEnum.DONE);
        }
    }

    public boolean isDone(){
        return getStatus().isDone();
    }

    public static List<Issue> getUndoneIssue(List<Issue> issues){
        return issues.stream().filter(issue -> undoneSet.contains(issue.getStatus())).collect(Collectors.toList());
    }

    public static EnumMap<StatusEnum, List<Issue>> groupByStatus(List<Issue> issues){
        return issues.stream().collect(Collectors.groupingBy(Issue::getStatus, () -> new EnumMap<>(StatusEnum.class), Collectors.toList()));
    }

    public int getCode(){
        switch (getStatus()){
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

    @Getter
    public enum StatusEnum {

        TODO(0),

        DOING(1){
            @Override
            public int getInnerCode() {
                return this.getCode();
            }

            @Override
            public boolean isDoing() {
                return true;
            }
        },

        READY_TO_DEPLOY(2){
            @Override
            public boolean readyToDeploy() {
                return true;
            }
        },

        DONE(3){
            @Override
            public int getInnerCode() {
                return this.getCode();
            }

            @Override
            public boolean isDone() {
                return true;
            }
        };

        public int getInnerCode(){
            // default return -1
            return 0;
        }

        public boolean isDoing(){
            return false;
        }

        public boolean readyToDeploy(){
            return false;
        }

        public boolean isDone(){
            return false;
        }

        private int code;

        StatusEnum(int code){
            this.code = code;
        }
    }

}
