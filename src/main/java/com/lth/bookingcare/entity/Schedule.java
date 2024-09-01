package com.lth.bookingcare.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date date;
    @OneToOne
    private Hour hour;
    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ScheduleUser> scheduleUsers = new ArrayList<>();
    // Constructor

    public Schedule() {
    }

    public Schedule(Long id, Date date, Hour hour, List<ScheduleUser> scheduleUsers) {
        this.id = id;
        this.date = date;
        this.hour = hour;
        this.scheduleUsers = scheduleUsers;
    }
    public Schedule(Date date, Hour hour) {
        this.date = date;
        this.hour = hour;
    }
    // Getter and setter

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Hour getHour() {
        return hour;
    }

    public void setHour(Hour hour) {
        this.hour = hour;
    }

    public List<ScheduleUser> getScheduleUsers() {
        return scheduleUsers;
    }

    public void setScheduleUsers(List<ScheduleUser> scheduleUsers) {
        this.scheduleUsers = scheduleUsers;
    }
}
