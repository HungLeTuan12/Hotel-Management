package com.lth.bookingcare.entity;

import com.lth.bookingcare.constant.Gender;
import com.lth.bookingcare.constant.Status;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    private String phone;
    private String dob;
    private String email;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String address;
    private Date date;
    private Long idHour;
    @Enumerated(EnumType.STRING)
    private Status status;
    private String note;
    private String token;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    @ManyToOne
    @JoinColumn(name = "doctor_id", referencedColumnName = "id")
    private User user;

    // Constructor

    public Booking() {
    }

    public Booking(Long id, String fullName, String phone, String email, Gender gender, String address, Date date, Long idHour, Status status, String note, String token, LocalDateTime createdAt, LocalDateTime updatedAt, User user) {
        this.id = id;
        this.fullName = fullName;
        this.phone = phone;
        this.email = email;
        this.gender = gender;
        this.address = address;
        this.date = date;
        this.idHour = idHour;
        this.status = status;
        this.note = note;
        this.token = token;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.user = user;
    }
    public Booking(String fullName, String dob, String phone, String email, Gender gender, String address, Date date, Long idHour, Status status, String note, String token) {
        this.fullName = fullName;
        this.dob = dob;
        this.phone = phone;
        this.email = email;
        this.gender = gender;
        this.address = address;
        this.date = date;
        this.idHour = idHour;
        this.status = status;
        this.note = note;
        this.token = token;
    }
    // Data

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getIdHour() {
        return idHour;
    }

    public void setIdHour(Long idHour) {
        this.idHour = idHour;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }
}
