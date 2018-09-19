package com.joker.main.share.bean;
import java.util.ArrayList;

public class MultiObjectShareBean{
 private String title;
 private String description;
 private ArrayList<String> images;
 private ArrayList<String> videos;

 public MultiObjectShareBean(){
  images=new ArrayList<>();
  videos=new ArrayList<>();
 }

 public String getTitle(){
  return title;
 }

 public void setTitle(String title){
  this.title=title;
 }

 public String getDescription(){
  return description;
 }

 public void setDescription(String description){
  this.description=description;
 }

 public ArrayList<String> getImages(){
  return images;
 }

 public void setImages(ArrayList<String> images){
  this.images=images;
 }

 public ArrayList<String> getVideos(){
  return videos;
 }

 public void setVideos(ArrayList<String> videos){
  this.videos=videos;
 }

 public void addViedo(String path) {

 }
}
