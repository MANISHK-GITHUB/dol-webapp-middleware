package com.fl.dol;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@SpringBootApplication
public class DolWebappMiddlewareApplication {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {

        SpringApplication.run(DolWebappMiddlewareApplication.class, args);
    }

    @RequestMapping("/")
    String sayHello() {
        return "Good Morning Pat Mam --!";
    }

    @GetMapping("/getone")
    public @ResponseBody ResponseEntity<String> get() {
        return new ResponseEntity<String>("GET Response from dol-webapp-middleware", HttpStatus.OK);
    }

    @GetMapping(value = "/callAPEX")
    public ResponseEntity<String> callAPEX() {
        String apimURL = "http://dodb1.dol.state.fl.us:8080/ords/inet/api/games/scratchinfo";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJPUkRTIFJFU1QgVVNFUiIsInN1YiI6ImdhbWVzIiwiYXVkIjoiSldUIFJlY2lwaWVudCIsImlhdCI6MTY3NTA4OTE1MywiZXhwIjoxNjg1MTMzMTUzLCJ1c3IiOiAiR0FNRVNfVVNFUiIsImlkIjogIk9SRFMtWFhYLTEzMyJ9.IP3aERVBp74X9x25Nf0FF-FZKX6RHNs8ljlzNt8iMyQ");
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = null;
        try {
            response = restTemplate.exchange(apimURL, HttpMethod.POST, entity, String.class);
            //String SQL_INSERT_API = "insert into dbo.ords_data_webapp1(name, api_id, attributes) values(?,?,?)";
            //String SQL_INSERT_API = "insert into web_app_sds.ords_data_webapp1(name, api_id, attributes) values(?,?,?)";
        }catch(Exception exception){
            exception.printStackTrace();
        }
        System.out.println(" Response Entity Size in String : -"+response.toString().length());

       // boolean insertSuccess = jdbcTemplate.update(SQL_INSERT_API, "scratchinfo",5, response.getBody().toString()) > 0;
       // System.out.println( " Insert Was  " + insertSuccess);

        return response;
    }

    @GetMapping(value = "/callAPEXLongText")
    public ResponseEntity<String> callAPEXLongText(){
        String sql = "SELECT attributes FROM dbo.ords_data_webapp1 WHERE ID = ?";
        String responseInString = jdbcTemplate.queryForObject(sql,new Object[]{1}, String.class);
        System.out.println("Record was Found ---"+responseInString.length());
        HttpHeaders headers = new HttpHeaders();
        headers.set("CacheCall"," From DB");
        ResponseEntity<String> response = new ResponseEntity<>(responseInString,headers,HttpStatus.CREATED);
        // ResponseEntity<String> response = ResponseEntity.ok(responseInString);

        return response;
    }



}
