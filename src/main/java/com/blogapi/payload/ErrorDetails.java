package com.blogapi.payload;

import java.util.Date;

public class ErrorDetails { //  when an exception an handled we want to send the message back. to send a message back here we create this class
                            // what message you should given back to user this all includes here
                            // to show the custom erron in response of postman

    private Date timestamp;
    private String message;
    private String details;
    public ErrorDetails(Date timestamp, String message, String details){ // act as setter
        this.timestamp=timestamp;
        this.message=message;
        this.details=details;
    }
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

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }


}
