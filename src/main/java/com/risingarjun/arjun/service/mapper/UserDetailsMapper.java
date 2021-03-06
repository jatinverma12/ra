package com.risingarjun.arjun.service.mapper;

import com.risingarjun.arjun.domain.*;
import com.risingarjun.arjun.service.dto.UserDetailsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserDetails} and its DTO {@link UserDetailsDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface UserDetailsMapper extends EntityMapper<UserDetailsDTO, UserDetails> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    UserDetailsDTO toDto(UserDetails userDetails);

    @Mapping(source = "userId", target = "user")
    UserDetails toEntity(UserDetailsDTO userDetailsDTO);

    default UserDetails fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserDetails userDetails = new UserDetails();
        userDetails.setId(id);
        return userDetails;
    }
}
