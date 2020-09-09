package net.gittab.basic.enums;

/**
 * DeployEnvSingleton.
 *
 * @author rookiedev
 * @date 2020/9/9 5:46 下午
 **/
public enum DeployEnvSingleton {

    INSTANCE;

    DeployEnvSingleton() {
        // 覆盖默认的发布环境
        this.envEnum = EnvEnum.TESTING;
    }

    private EnvEnum envEnum = EnvEnum.DEVELOPMENT;

    public static DeployEnvSingleton getInstance(){
        return INSTANCE;
    }

    public EnvEnum getDeployEnv(){
        return this.envEnum;
    }

}
