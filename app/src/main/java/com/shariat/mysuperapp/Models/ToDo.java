package com.shariat.mysuperapp.Models;

public class ToDo {

  private int id;
  private String title, description;
  private boolean status; // true for done - false for undone
  private String worker;
  private String date;

  // constructors

  public ToDo() {
  }

  public ToDo(int id, String title, String description, boolean status, String worker, String date) {
    this.id = id;
    this.title = title;
    this.description = description;
    this.status = status;
    this.worker = worker;
    this.date = date;
  }

  public ToDo(String title, String description, boolean status, String worker, String date) {
    this.title = title;
    this.description = description;
    this.status = status;
    this.worker = worker;
    this.date = date;
  }

  // getters and setters

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public boolean isStatus() {
    return status;
  }

  public void setStatus(boolean status) {
    this.status = status;
  }

  public String getWorker() {
    return worker;
  }

  public void setWorker(String worker) {
    this.worker = worker;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  // toString method is necessary for printing the contents of a class (To Do class in thi case)
  @Override
  public String toString() {
    return "ToDo{" +
        "id=" + id +
        ", title='" + title + '\'' +
        ", description='" + description + '\'' +
        ", status=" + status +
        ", worker='" + worker + '\'' +
        ", date='" + date + '\'' +
        '}';
  }
}
