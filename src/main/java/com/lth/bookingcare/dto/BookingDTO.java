package com.lth.bookingcare.dto;

import com.lth.bookingcare.constant.Gender;

public class BookingDTO {
    private String name;
    private String dob;
    private String phone;
    private String email;
    private Gender gender;
    private String address;
    private Long idMajor;
    private Long idUser;
    private String date;
    private Long idHour;
    private String note;
    private String status;
    // Data

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
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

    public Long getIdMajor() {
        return idMajor;
    }

    public void setIdMajor(Long idMajor) {
        this.idMajor = idMajor;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getIdHour() {
        return idHour;
    }

    public void setIdHour(Long idHour) {
        this.idHour = idHour;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
