package com.javaweb.converter;

import com.javaweb.model.dto.UserDTO;
import com.javaweb.entity.UserEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    @Autowired
    private ModelMapper modelMapper;

    public UserDTO convertToDto (UserEntity entity){
        return modelMapper.map(entity, UserDTO.class);
    }

    public UserEntity convertToEntity (UserDTO dto){
        return modelMapper.map(dto, UserEntity.class);
    }
    
}
