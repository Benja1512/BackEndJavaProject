package com.purocodigo.backend.controllers;


import com.purocodigo.backend.model.requests.PostCreateRequestModel;
import com.purocodigo.backend.responses.OperationStatusModel;
import com.purocodigo.backend.responses.PostRest;
import com.purocodigo.backend.services.PostServiceInterface;
import com.purocodigo.backend.services.UserServiceInterface;
import com.purocodigo.backend.shared.dto.PostCreationDto;
import com.purocodigo.backend.shared.dto.PostDto;
import com.purocodigo.backend.shared.dto.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    PostServiceInterface postService;

    @Autowired
    UserServiceInterface userServiceInterface;

    @Autowired
    ModelMapper mapper;


    @PostMapping
    public PostRest createPost(@RequestBody PostCreateRequestModel createRequestModel) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getPrincipal().toString();


        PostCreationDto postCreationDto = mapper.map(createRequestModel, PostCreationDto.class);

        postCreationDto.setUserEmail(email);

        PostDto postDto = postService.createPost(postCreationDto);
        PostRest postToReturn = mapper.map(postDto, PostRest.class);

        if (postToReturn.getExpiredAt().compareTo(new Date(System.currentTimeMillis())) < 0) {
            postToReturn.setExpired(true);
        }

        return postToReturn;
    }

    @GetMapping(path = "/last") //localhost:8080/post/last
    public List<PostRest> lastPost() {
        List<PostDto> posts = postService.getLastPosts();

        List<PostRest> postRests = new ArrayList<>();

        for (PostDto post : posts) {
            PostRest postRest = mapper.map(post, PostRest.class);
            postRest.add(postRest);
        }

        //convertir list de post dto a post rest

        return postRests;
    }


    @GetMapping(path = "/{id}") // localhost:8080/posts/

    public PostRest getPost(@PathVariable String id) {

        PostDto postDto = postService.getPost(id);

        PostRest postRest = mapper.map(postDto, PostRest.class);

        if (postRest.getExpiredAt().compareTo(new Date(System.currentTimeMillis())) < 0) {

            postRest.setExpired(true);
        }

        // VALIDAR SI EL POST ES PRIVADO O SI EL PSOT YA EXPIRO //

        if (postRest.getExposure().getId() == 1 || postRest.getExpired()) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            UserDto user = userService.getUser(authentication.getPrincipal().toString());


            if (user.getId() != postDto.getUser().getId()) {
                throw new RuntimeException("No tienes permisos para realizar esta accion");
            }
        }

        return postRest;
    }

    @DeleteMapping(path = "/{id}")
    public OperationStatusModel deletePost(@PathVariable String id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserDto user = userService.getUser(authentication.getPrincipal().toString());

        OperationStatusModel operationStatusModel = new OperationStatusModel();
        operationStatusModel.setName("DELETE");

        postService.deletePost(id, user.getId());
        operationStatusModel.setResult("SUCCESS");

        return operationStatusModel;

    }

    @PutMapping(path = "/{id}")
    public PostRest updatePost(@RequestBody PostCreateRequestModel post, @PathVariable String id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserDto user = userService.getUser(authentication.getPrincipal().toString());

        PostCreationDto postUpdateDto = mapper.map(postCreationRequestModel, PostCreationDto.class);

        PostDto postDto = postService.updatePost(id, user.getId(), postUpdateDto);

        PostRest updatedPost = mapper.map(postDto, PostRest.class);

        return updatedPost;

    }

}