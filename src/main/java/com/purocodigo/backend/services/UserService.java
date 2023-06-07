package com.purocodigo.backend.services;

import com.purocodigo.backend.entity.PostEntity;
import com.purocodigo.backend.repositories.PostRepository;
import com.purocodigo.backend.repositories.UserRepository;
import com.purocodigo.backend.entity.UserEntity;
import com.purocodigo.backend.exceptions.EmailExistsException;
import com.purocodigo.backend.shared.dto.PostDto;
import com.purocodigo.backend.shared.dto.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
public class UserService implements UserServiceInterface {

    @Autowired
    UserRepository postRepository;

    @Autowired
    PostRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    ModelMapper mapper;



    @Override
    public UserDto createUser(UserDto user) {

        if (userRepository.findByEmail(user.getEmail()) != null)
            throw new EmailExistsException("el correo electronico ya existe");

        // toda la logica para crear el usuario

        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);

        userRepository.save(userEntity);

        userEntity.setEncryptedPassword(
                bCryptPasswordEncoder.encode(user.getPassword()));

        UUID userId = UUID.randomUUID();
        userEntity.setUserId(userId.toString());


        UserEntity storedUserDetails = userRepository.save(userEntity);

        UserDto userToReturn = new UserDto();
        BeanUtils.copyProperties(storedUserDetails, userToReturn);

        return userToReturn;
    }


    // crear login de usuario
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        UserEntity userEntity = userRepository.findByEmail(email);

        if(userEntity == null ) {
            throw new UsernameNotFoundException(email);

        }
        return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), new ArrayList<>());

    }

    @Override
    public UserDto getUser(String email){
        UserEntity userEntity = userRepository.findByEmail(email);

        if(userEntity == null ) {
            throw new UsernameNotFoundException(email);

        }

        UserDto userToReturn = new UserDto();

        BeanUtils.copyProperties(userEntity, userToReturn);

        return userToReturn;

    }

    @Override
    public List<PostDto> getUserPosts(String email) {

        UserEntity userEntity = userRepository.findByEmail(email);

        List<PostEntity> posts = postRepository.getByUserIdOrderCreatedAtDesc(userEntity.getId());

        List<PostDto> postDtos = new ArrayList<>();

        for (PostDto post : postDtos) {
            PostDto postDto = mapper.map(post, PostDto.class);
            postDtos.add(postDto);

        }

    return postDtos;
    }

}
