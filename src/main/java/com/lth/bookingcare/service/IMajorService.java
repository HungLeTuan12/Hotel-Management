package com.lth.bookingcare.service;

import com.lth.bookingcare.dto.MajorDTO;
import com.lth.bookingcare.entity.Major;

import java.util.List;

public interface IMajorService {
    List<Major> getAll();
    Major findById(Long id);
    boolean checkNameMajor(String name);
    boolean saveMajor(MajorDTO majorDTO);
    void updateMajor(MajorDTO majorDTO, Long id);
    void deleteMajor(Long id);
    void convertDTOToEntity(MajorDTO majorDTO, Major major);
}
