package com.purocodigo.backend.responses;

import java.util.Date;

public class ErrorMessage {

    private Date timestamp;


    private String message;

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ErrorMessage(Date date, String message) {

    }

}
