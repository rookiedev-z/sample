package net.gittab.fsmsample.mapper;

import net.gittab.fsmsample.domain.Status;
import net.gittab.fsmsample.dto.StatusDTO;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author rookiedev
 */
public interface StatusMapper extends Mapper<Status> {

    StatusDTO findById(@Param("id") Long id);
}
