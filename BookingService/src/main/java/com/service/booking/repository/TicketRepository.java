package com.service.booking.repository;

import com.service.booking.entity.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<TicketEntity,Integer> {
    List<TicketEntity> findTicketEntitiesByOrderid(Integer orderid);
}
