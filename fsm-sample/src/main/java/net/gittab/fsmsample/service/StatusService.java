package net.gittab.fsmsample.service;

import net.gittab.fsmsample.dto.StatusDTO;

/**
 * @author rookiedev
 */
public interface StatusService {

    StatusDTO findById(Long statusId);
}
