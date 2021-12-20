package com.service.booking.model;

import com.service.booking.entity.TicketEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Ticket {
    private Integer id;
    private String cinema;
    private String movie;
    private String schedule;
    private Integer cinemaroom;
    private String seat;
    private Integer ordertid;
    public static Ticket convertEntity(TicketEntity ticketEntity) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(ticketEntity, Ticket.class);
    }

    public TicketEntity toEntity() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, TicketEntity.class);
    }
}
