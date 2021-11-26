package com.service.cinema.model;
import com.service.cinema.entity.CinemaEntity;
import lombok.*;
import org.modelmapper.ModelMapper;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Cinema {
    private Integer id;
    private String name;
    private Integer cityid;
    public static Cinema getModel(CinemaEntity cinemaEntity){
        ModelMapper modelMapper =new ModelMapper();
        return modelMapper.map(cinemaEntity,Cinema.class);
    }
}