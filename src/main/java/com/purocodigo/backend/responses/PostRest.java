package com.purocodigo.backend.responses;

import com.purocodigo.backend.repositories.UserRepository;
import com.purocodigo.backend.shared.dto.ExposureDto;

import java.util.Date;

public class PostRest {

    private String postId;

    private String title;

    private String content;

    private Date expiredAt;

    private Date createdAt;

    private boolean expired = false;

    private UserRest user;

    private ExposureRest exposure;

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(Date expiredAt) {
        this.expiredAt = expiredAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public UserRest getUser() {
        return user;
    }

    public void setUser(UserRest user) {
        this.user = user;
    }

    public ExposureRest getExposure() {
        return exposure;
    }

    public void setExposure(ExposureRest exposure) {
        this.exposure = exposure;
    }
}
