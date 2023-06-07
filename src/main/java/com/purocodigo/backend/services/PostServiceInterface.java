package com.purocodigo.backend.services;

import com.purocodigo.backend.shared.dto.PostCreationDto;
import com.purocodigo.backend.shared.dto.PostDto;

import java.util.List;

public interface PostServiceInterface {

    public PostDto createPost(PostCreationDto post);

    public List<PostDto> getLastPosts();

    public PostDto getPost(String id);

     public void deletePost(String postId, long userId);

     public PostDto updatePost(String postId, long userId, PostCreationDto postUpdateDto);
}
