package net.gittab.basic.enums;

/**
 * EnvEnum.
 *
 * @author rookiedev
 * @date 2020/9/9 5:39 下午
 **/
public enum EnvEnum {

    DEVELOPMENT{
        @Override
        public void deploy(Issue issue) {
            System.out.println(issue.getId() + " issue will deploy to DEVELOPMENT");
        }
    },

    TESTING{
        @Override
        public void deploy(Issue issue) {
            System.out.println(issue.getId() + " issue will deploy to TESTING");
        }
    },

    PRODUCTION{
        @Override
        public void deploy(Issue issue) {
            System.out.println(issue.getId() + " issue will deploy to PRODUCTION");
        }
    };

    public abstract void deploy(Issue issue);
}
