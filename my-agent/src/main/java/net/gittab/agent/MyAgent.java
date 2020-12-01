package net.gittab.agent;

/**
 * MyAgent.
 *
 * @author rookiedev 2020/11/17 15:57
 **/
public class MyAgent {

    public static void premain(String arg) {
        System.out.println("Hello Premain");
    }

    public static void agentmain(String arg){
        System.out.println("Hello Agentmain");
    }
}
