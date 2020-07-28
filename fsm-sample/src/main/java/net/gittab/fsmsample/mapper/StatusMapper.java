package net.gittab.fsmsample.mapper;

import net.gittab.fsmsample.domain.Status;
import net.gittab.fsmsample.dto.StatusDTO;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author xiaohua zhou
 */
public interface StatusMapper extends Mapper<Status> {

    StatusDTO findById(@Param("id") Long id);
}
