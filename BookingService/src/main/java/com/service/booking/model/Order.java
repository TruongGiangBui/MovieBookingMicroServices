package com.service.booking.model;

import com.service.booking.entity.OrderEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.modelmapper.ModelMapper;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Order {
    private Integer id;
    private Integer schedule;
    private List<Integer> seatslist;
    private BigDecimal totalamount;
    private Boolean completlypayment;

    public static Order convertEntity(OrderEntity orderEntity) {
        ModelMapper modelMapper = new ModelMapper();
        Order order = modelMapper.map(orderEntity, Order.class);
        String[] seats = orderEntity.getSeats().split(",");
        List<Integer> s=new ArrayList<>();
        for (String se:seats) s.add(Integer.valueOf(se));
        order.setSeatslist(s);
        return order;
    }

    public OrderEntity toEntity() {
        ModelMapper modelMapper = new ModelMapper();
        OrderEntity orderEntity = modelMapper.map(this, OrderEntity.class);
        orderEntity.setSeats(StringUtils.join(seatslist, ","));
        return orderEntity;
    }
}