package com.blogapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)//when exception occurs it gives the status not found which return404 code
public class ResourceNotFoundException extends RuntimeException{
//private long id;
 public ResourceNotFoundException(long id){ //when create the object for ResourceNotFoundException class
  //System.out.println("resource not found :"+id);  // it will call this constructor and supply the id no.
  super("Resource not found for id:"+id);  // and based on that id super keyword will display the message in the reponse in postman

 }
}


//post is not found i want an exception to be throwm which is resourscenotfoundexception,
// that resource if we pass id100 which is not exist in our db in thatcase this excepion has to be thrown
// we extends the class with runtimeexception because we want this exception purely runtime
//private Sring resourceName;//post //
// private String fieldName;//id
// private long fieldValue;//1000
