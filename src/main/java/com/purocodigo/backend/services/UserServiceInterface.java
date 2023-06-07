package com.purocodigo.backend.services;

import com.purocodigo.backend.shared.dto.PostDto;
import com.purocodigo.backend.shared.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

public interface UserServiceInterface extends UserDetailsService {

    UserDto createUser(UserDto user);

    UserDto getUser(String email);

    public List<PostDto> getUserPosts();


}
