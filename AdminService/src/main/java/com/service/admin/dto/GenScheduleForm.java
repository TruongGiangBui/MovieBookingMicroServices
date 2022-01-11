package com.service.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GenScheduleForm {
    List<Integer>movieids;
    String date;
    Integer cinemaid;
}
