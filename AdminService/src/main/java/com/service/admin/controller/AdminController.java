package com.service.admin.controller;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.service.admin.config.RestTemplateConfig;
import com.service.admin.dto.AddMovieForm;
import com.service.admin.model.Movie;
import com.service.admin.model.Movie1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.http.*;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(allowCredentials = "*")
@RibbonClient(name = "admin-service",configuration = RestTemplateConfig.class)
public class AdminController {
    @Autowired
    private  RestTemplate restTemplate;
    @GetMapping("/")
    public ResponseEntity<List<AddMovieForm>> test() throws IOException {

//        Gson gson=new GsonBuilder()
//                .setDateFormat("yyyy-MM-dd").create();
//        List<AddMovieForm> results=new ArrayList<>();
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
//        TypeReference<List<Movie1>> typeReference = new TypeReference<List<Movie1>>(){};
//        File initialFile = new File("/home/truonggiang/project III/MyApp/AdminService/src/main/resources/films.json");
//        InputStream inputStream = new FileInputStream(initialFile);
//        try {
//            List<Movie1> movies = mapper.readValue(inputStream,typeReference);
//
//            for(Movie1 movie1:movies){
//                results.add(new AddMovieForm(movie1));
//            }
//            for(AddMovieForm form:results){
//
//                HttpHeaders headers = new HttpHeaders();
//                headers.setContentType(MediaType.APPLICATION_JSON);
//
//
//                HttpEntity<String> request = new HttpEntity<String>(gson.toJson(form), headers);
//
//                ResponseEntity<String> response = restTemplate.postForEntity("http://movies-service/api/movies", request , String.class );
//            }
//        } catch (Exception e){
//            System.out.println( e.getMessage());
//        }
        System.out.println(restTemplate.getForObject("http://movies-service/api/movies/2",Movie.class));
        return new  ResponseEntity<>(HttpStatus.ACCEPTED);
    }
    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public String send(String message) throws Exception {
        return message;
    }
}
