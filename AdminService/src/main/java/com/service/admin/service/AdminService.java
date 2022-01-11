package com.service.admin.service;

import com.google.gson.Gson;
import com.service.admin.dto.AddScheduleForm;
import com.service.admin.dto.GenScheduleForm;
import com.service.admin.model.Message;
import com.service.admin.model.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
public class AdminService {
    @Autowired
    private RestTemplate restTemplate;
    public Message generateSchedule(@RequestBody GenScheduleForm form){
        List<Movie> movies=new ArrayList<>();
        for(Integer id:form.getMovieids()){
            Movie movie=restTemplate.getForObject("http://movies-service/api/movies/"+id,Movie.class);
            movies.add(movie);
        }
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date date = formatter.parse(form.getDate());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.HOUR, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
//            System.out.println(new Timestamp(calendar.getTimeInMillis()+9* 3600000L));
//            System.out.println(new Timestamp(calendar.getTimeInMillis()+23* 3600000L));
            SimpleDateFormat timeformat=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
            timeformat.setTimeZone(TimeZone.getTimeZone("GMT"));
            int index=0;
            for(int i=1;i<6;i++){
                long begin=9* 3600000L;
                long end=23* 3600000L;
                while (begin<end){
                    AddScheduleForm addScheduleForm=AddScheduleForm.builder()
                            .cinemaid(form.getCinemaid())
                            .movieid(movies.get(index).getId())
                            .capacity(new BigDecimal(50))
                            .cinemaroom(i)
                            .starttime(timeformat.format(new Timestamp(calendar.getTimeInMillis()+begin)))
                            .endtime(timeformat.format(new Timestamp(calendar.getTimeInMillis()+begin+movies.get(index).getRuntime().longValue()*60000)))
                            .price(new BigDecimal(120000)).build();
//                    System.out.println(addScheduleForm);
                    begin+=(movies.get(index).getRuntime().longValue()*60000+15*60000L);
                    index=(index+1)%movies.size();
                    Gson gson=new Gson();
                    System.out.println(gson.toJson(addScheduleForm));
//
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.APPLICATION_JSON);
                    HttpEntity<String> request = new HttpEntity<String>(gson.toJson(addScheduleForm), headers);
                    restTemplate.postForEntity("http://cinema-service/cinemas/schedules", request , String.class );
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            return Message.builder().code(500).build();
        }
        return Message.builder().code(200).build();
    }

}
