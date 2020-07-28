package net.gittab.fsmsample.service;

import net.gittab.fsmsample.dto.StateMachineDTO;

/**
 * @author xiaohua zhou
 */
public interface StateMachineService {

    StateMachineDTO findById(Long stateMachineId);

}
