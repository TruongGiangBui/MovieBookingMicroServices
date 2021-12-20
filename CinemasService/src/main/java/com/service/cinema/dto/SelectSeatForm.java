package com.service.cinema.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SelectSeatForm {
    Integer scheduleid;
    List<Integer> selected;
}
