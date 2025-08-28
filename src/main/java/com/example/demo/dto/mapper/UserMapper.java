package com.example.demo.dto.mapper;

import com.example.demo.dto.user.UserDTO;
import com.example.demo.dto.user.CreateUserDTO;
import com.example.demo.entities.User;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.ArrayList;

@Component
public class UserMapper {
    
    public UserDTO toDto(User entity) {
        if (entity == null) return null;
        
        UserDTO dto = new UserDTO();
        dto.setId(entity.getId());
        dto.setUsername(entity.getUsername());
        dto.setEmail(entity.getEmail());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setRole(entity.getRole());
        dto.setIsActive(entity.getIsActive());
        dto.setLastLogin(entity.getLastLogin());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        
        return dto;
    }
    
    public List<UserDTO> toDtoList(List<User> entities) {
        if (entities == null) return new ArrayList<>();
        return entities.stream().map(this::toDto).toList();
    }
    
    public User toEntity(CreateUserDTO dto) {
        if (dto == null) return null;
        
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPasswordHash(dto.getPassword()); // In real app, this should be hashed
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setRole(dto.getRole());
        
        return user;
    }
}
