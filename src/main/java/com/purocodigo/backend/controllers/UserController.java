package com.purocodigo.backend.controllers;


import com.purocodigo.backend.model.requests.UserDetailsRequestModels;
import com.purocodigo.backend.responses.UserRest;
import com.purocodigo.backend.security.AuthenticationFilter;
import com.purocodigo.backend.shared.dto.UserDto;
import com.purocodigo.backend.services.UserService;
import com.purocodigo.backend.services.UserServiceInterface;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Security;
import java.util.ArrayList;

@RestController
@RequestMapping("/users") // localhost:8080/users
public class UserController {


    @Autowired //inyeccion de dependencia
    UserServiceInterface userService;

    @Autowired
    ModelMapper mapper;


    @GetMapping(produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public String getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getPrincipal().toString();

        userDto userDto = userService.getUser(email);

        ModelMapper mapper = new ModelMapper();

        UserRest userToReturn = mapper.map(sourceDto, UserRest.class);

        BeanUtils.copyProperties(userDto, userToReturn);

        return userToReturn;

    }

    @PostMapping
    public UserRest createUser(@RequestBody UserDetailsRequestModels userDetails) {

        UserRest userToReturn = new UserRest();

        UserDto userDto = new UserDto();


        BeanUtils.copyProperties(userDetails, userDto);

        UserDto createdUser = userService.createUser(userDto);

        BeanUtils.copyProperties(createdUser, userToReturn);

        return userToReturn;
    }

    @GetMapping(path = "/posts") //localhost:8080/users/posts
        pubic List<PostRest> getPost() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getPrincipal().toString();

        List<PostDto> posts = userService.getUserPosts(email);

        List<PostRest> postRests = new ArrayList<>();

        for (PostDto post: posts ){
            PostRest postRest = mapper.map(post, PostRest.class);
            if (postRest.getExpiresAt(.compareTo(new Date(System.currentTimeMillis()))) < 0) {
                postRest.setExpired(true);
            }
            postRest.add(postRest);
        }

        //convertir list de post dto a post rest

        return postRests;
    }

}
