package com.codewithmosh.store.users;
import org.mapstruct.Mapper;
import org.mapstruct.*;

@Mapper(componentModel="spring")
public interface UserMapper {

    @Mapping( target = "createdAt" , expression= "java(java.time.LocalDateTime.now())")
    UserDto toDto (User user );

    User toEntity (RegisterUserRequest request) ;

    void updateUser( UpdateUserRequest request , @MappingTarget User user);


}