package net.gittab.async.converter;

import net.gittab.async.domain.Order;
import net.gittab.async.model.OrderDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * OrderConverter.
 *
 * @author rookiedev
 * @date 2020/8/23 02:54
 **/
@Mapper
public interface OrderConverter {

    OrderConverter INSTANCE = Mappers.getMapper(OrderConverter.class);

    OrderDTO domain2DTO(Order order);

    Order dto2Domain(OrderDTO orderDTO);
}
