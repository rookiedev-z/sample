package net.gittab.fsmsample.service;

import net.gittab.fsmsample.dto.StatusDTO;

/**
 * @author xiaohua zhou
 */
public interface StatusService {

    StatusDTO findById(Long statusId);
}
