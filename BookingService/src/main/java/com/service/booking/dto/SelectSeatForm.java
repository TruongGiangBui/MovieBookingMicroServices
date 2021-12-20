package com.service.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SelectSeatForm {
    Integer scheduleid;
    List<Integer> selected;
}
