package net.gittab.agent;

import java.io.IOException;

import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;

/**
 * AttachTest.
 *
 * @author rookiedev 2020/11/17 16:48
 **/
public class AttachTest {
    public static void main(String[] args) throws AttachNotSupportedException, IOException, AgentLoadException, AgentInitializationException {
        if (args.length <= 1) {
            System.out.println("Usage: java AttachTest <PID> /PATH/TO/AGENT.jar");
            return;
        }
        VirtualMachine vm = VirtualMachine.attach(args[0]);
        vm.loadAgent(args[1]);
    }
}
