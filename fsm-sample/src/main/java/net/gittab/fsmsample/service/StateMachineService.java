package net.gittab.fsmsample.service;

import net.gittab.fsmsample.dto.StateMachineDTO;

/**
 * @author rookiedev
 */
public interface StateMachineService {

    StateMachineDTO findById(Long stateMachineId);

}
