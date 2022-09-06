package com.example.maruf.a2svc.models;



import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Users")
public  class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private  String name;
    private  int easyCount;
    private  int midCount;
    private  int hardCount;
    private  int totalCount;
    private  int totalSubmission;
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdated;

    private String title = "";

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public User(String name, int easyCount, int midCount, int hardCount, int totalCount, int totalSubmission, Date lastUpdated) {
        this.name = name;
        this.easyCount = easyCount;
        this.midCount = midCount;
        this.hardCount = hardCount;
        this.totalCount = totalCount;
        this.totalSubmission = totalSubmission;
        this.lastUpdated = lastUpdated;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public User(){}

    public int getEasyCount() {
        return easyCount;
    }

    public void setEasyCount(int easyCount) {
        this.easyCount = easyCount;
    }

    public int getMidCount() {
        return midCount;
    }

    public void setMidCount(int midCount) {
        this.midCount = midCount;
    }

    public int getHardCount() {
        return hardCount;
    }

    public void setHardCount(int hardCount) {
        this.hardCount = hardCount;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalSubmission() {
        return totalSubmission;
    }

    public void setTotalSubmission(int totalSubmission) {
        this.totalSubmission = totalSubmission;
    }
}