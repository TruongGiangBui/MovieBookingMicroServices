package com.service.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AddScheduleForm {
    private Integer cinemaroom;
    private Integer cinemaid;
    private Integer movieid;
    private String starttime;
    private String endtime;
    private BigDecimal capacity;
    private BigDecimal price;
}
