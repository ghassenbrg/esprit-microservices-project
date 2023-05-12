package com.esprit.tn.config;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.esprit.tn.model.UserDTO;
import com.esprit.tn.service.UserService;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

@Component
public class DataLoader implements ApplicationRunner {

    private UserService userService;

    @Autowired
    public DataLoader(UserService userService) {
        this.userService = userService;
    }

    public void run(ApplicationArguments args) throws StreamReadException, DatabindException, IOException {
    	ObjectMapper mapper = new ObjectMapper();
  		CollectionType type = mapper.getTypeFactory().constructCollectionType(List.class, UserDTO.class);
  		List<UserDTO> users = mapper.readValue(new ClassPathResource("users.json").getInputStream(), type);
  		users.forEach(u -> userService.create(u));
    }
}
