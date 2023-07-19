package com.alambiyah.soal.web.rest;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/soaljawaban")
public class SoalJawaban {

    private final Logger log = LoggerFactory.getLogger(SoalJawaban.class);

    @GetMapping("/job")
    public ResponseEntity<Object> getJob() {
        log.debug("REST request to get jsonnnnnn");

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity <String> entity = new HttpEntity<String>(headers);
        
        return new ResponseEntity<>(restTemplate.exchange("http://dev3.dansmultipro.co.id/api/recruitment/positions.json", HttpMethod.GET, entity, String.class).getBody(), headers, HttpStatus.OK);
    }
    
    @GetMapping("/job/{id}")
    public ResponseEntity<Object> getJobDetail(@PathVariable String id) {
        log.debug("REST request to get jsonnnnnn");

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity <String> entity = new HttpEntity<String>(headers);

        String url = "http://dev3.dansmultipro.co.id/api/recruitment/positions/" + id;
        
        return new ResponseEntity<>(restTemplate.exchange(url, HttpMethod.GET, entity, String.class).getBody(), headers, HttpStatus.OK);
    }
}
