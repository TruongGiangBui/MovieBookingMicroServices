package com.service.cinema.dto;

import com.service.cinema.entity.ScheduleEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddScheduleForm {
    private Integer cinemaroom;
    private Integer cinemaid;
    private Integer movieid;
    private Timestamp starttime;
    private Timestamp endtime;
    private BigDecimal capacity;
    private BigDecimal price;
    public ScheduleEntity toEntity(){
        ModelMapper modelMapper =new ModelMapper();
        ScheduleEntity scheduleEntity=modelMapper.map(this,ScheduleEntity.class);
        List<Integer> seatslist=new ArrayList<Integer>(Collections.nCopies(capacity.intValue(), 0));
        scheduleEntity.setSeats(StringUtils.join(seatslist, ","));
        return scheduleEntity;
    }
}
