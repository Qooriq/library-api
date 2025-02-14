package com.java.akdev.libraryapi.mapper;

import com.java.akdev.libraryapi.config.MapperConfigurationn;
import com.java.akdev.libraryapi.dto.create.UserLoginDto;
import com.java.akdev.libraryapi.dto.create.UserCreateDto;
import com.java.akdev.libraryapi.dto.create.UserEditDto;
import com.java.akdev.libraryapi.dto.read.UserReadDto;
import com.java.akdev.libraryapi.dto.read.UserRegisterDto;
import com.java.akdev.libraryapi.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfigurationn.class)
public interface UserMapper {

    UserReadDto toUserReadDto(User user);

    UserRegisterDto toUserRegisterDto(User user);

    User toUser(UserLoginDto userLoginDto);

    User toUser(UserCreateDto userDto);

    void map(@MappingTarget User to, UserCreateDto from);

    void map(@MappingTarget User to, UserEditDto from);
}
