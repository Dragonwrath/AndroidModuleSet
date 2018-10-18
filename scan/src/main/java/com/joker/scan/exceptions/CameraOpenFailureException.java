package com.joker.scan.exceptions;

/**
 * When camera can't open,this exception should be thrown.
 * Such as ,leak of permission or no camera
 *
 */
public class CameraOpenFailureException extends RuntimeException{

 public CameraOpenFailureException(String message){
  super(message);
 }

}
