package com.service.booking.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Schedule {
    private Integer id;
    private Integer cinemaroom;
    private Integer cinemaid;
    private Integer movieid;
    private Timestamp starttime;
    private Timestamp endtime;
    private BigDecimal capacity;
    private List<String> seatslist;
    private BigDecimal price;
}
