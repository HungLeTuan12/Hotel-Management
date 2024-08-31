package com.lth.bookingcare.service.impl;

import com.lth.bookingcare.dto.MajorDTO;
import com.lth.bookingcare.entity.Major;
import com.lth.bookingcare.exception.ResourceAlreadyExist;
import com.lth.bookingcare.exception.ResourceNotFoundException;
import com.lth.bookingcare.repository.MajorRepository;
import com.lth.bookingcare.service.IMajorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MajorService implements IMajorService {
    @Autowired
    private MajorRepository majorRepository;
    @Override
    public List<Major> getAll() {
        return majorRepository.findAll();
    }

    @Override
    public Major findById(Long id) {
        return majorRepository.findById(id).get();
    }

    @Override
    public boolean checkNameMajor(String name) {
        return majorRepository.findByName(name) != null;
    }

    @Override
    public boolean saveMajor(MajorDTO majorDTO) {
        Major major = new Major();
        convertDTOToEntity(majorDTO, major);
        Major major1 = majorRepository.save(major);
        return major1 != null;
    }

    @Override
    public void updateMajor(MajorDTO majorDTO, Long id) {
        Major major = majorRepository.findById(id).get();
        if(checkNameMajor(majorDTO.getName())) {
            throw new ResourceAlreadyExist("Major is already exist !");
        }
        else {
            convertDTOToEntity(majorDTO, major);
            majorRepository.save(major);
        }
    }

    @Override
    public void deleteMajor(Long id) {
        majorRepository.deleteById(id);
    }

    @Override
    public void convertDTOToEntity(MajorDTO majorDTO, Major major) {
        major.setName(majorDTO.getName());
        major.setDescription(majorDTO.getDescription());
        major.setImages(majorDTO.getImages());
    }
}
