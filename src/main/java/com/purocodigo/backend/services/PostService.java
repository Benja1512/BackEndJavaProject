package com.purocodigo.backend.services;

import com.purocodigo.backend.entity.ExposureEntity;
import com.purocodigo.backend.entity.PostEntity;
import com.purocodigo.backend.entity.UserEntity;
import com.purocodigo.backend.repositories.ExposureRepository;
import com.purocodigo.backend.repositories.PostRepository;
import com.purocodigo.backend.repositories.UserRepository;
import com.purocodigo.backend.shared.dto.PostCreationDto;
import com.purocodigo.backend.shared.dto.PostDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class PostService implements PostServiceInterface{



    @Autowired
    PostRepository postRepository;


    @Autowired
    UserRepository userRepository;

    @Autowired
    ExposureRepository exposureRepository;
    @Override
    public PostDto createPost(PostCreationDto post) {

        UserEntity userEntity = userRepository.findByEmail(post.getUserEmail());
        ExposureEntity exposureEntity = ExposureRepository.findById(post.getExposureId());

        PostEntity postEntity = new PostEntity();
        postEntity.setUser(userEntity);
        postEntity.setExposure(exposureEntity);
        postEntity.setTitle(post.getTitle());
        postEntity.setContent(post.getContent());
        postEntity.setPostId(UUID.randomUUID().toString());
        postEntity.setExpiresAt(new Date(System.currentTimeMillis() + (post.getExpirationTime() * 60000)));

        PostEntity createdPost = postRepository.save(postEntity);

        ModelMapper mapper = new ModelMapper();
        PostDto postToReturn = mapper.map(createdPost, PostDto.class);


        return postToReturn;
    }

    @Override
    public List<PostDto> getLastPosts() {

        long publicExposureId = 2;
        List<PostEntity> postEntities = postRepository.getLastPublicPosts(publicExposureId,
                new Date(System.currentTimeMillis()));


        List<PostDto> postDtos = new ArrayList<>();


            for (PostEntity post: postEntities) {
                PostDto posDto = mapper.map(post, PostDto.class);
                postDtos.add(postDto);
        }

        //TODO Auto-generated method stub
        return postDtos;
    }

    @Override
    public PostDto getPost(String postId) {

        PostEntity postEntity = postRepository.findByPostId(postId);

        PostDto postDto = mapper.map(postEntity, PostDto.class);
        return postDto;
    }

    @Override
    public void deletePost(String postId, long userId) {
        PostEntity postEntity = postRepository.findByPostId(postId);
        if(postEntity.getUser().getId() != userId)
            throw new RuntimeException("No se puede realizar esta acccion");

        postRepository.delete(postEntity);

    }

    @Override
    public PostDto updatePost(String postId, long userId, PostCreationDto postUpdateDto){
        PostEntity postEntity = postRepository.findByPostId(postId);
        if ( postEntity.getUser().getId() != userId)
            throw new RuntimeException("Nose puede realizaer la accion");


        ExposureEntity exposureEntity = ExposureRepository.findById(postUpdateDto.getExposureId());

        postEntity.setExposure(exposureEntity);
        postEntity.setTitle(postUpdateDto.getTitle());
        postEntity.setContent(postUpdateDto.getContent());
        postEntity.setExpiresAt(new Date(System.currentTimeMillis()+ (postUpdateDto.getExpirationTime() * 60000)));


        PostEntity updatedPost = postRepository.save(postEntity);

        PostDto postDto = mapper.map(updatedPost, PostDto.class);

        return postDto;

        //TODO AUTO-GENERATED METHOD STUB
        return null;
    }

}
