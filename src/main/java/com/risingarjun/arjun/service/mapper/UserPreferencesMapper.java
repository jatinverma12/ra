package com.risingarjun.arjun.service.mapper;

import com.risingarjun.arjun.domain.*;
import com.risingarjun.arjun.service.dto.UserPreferencesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserPreferences} and its DTO {@link UserPreferencesDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface UserPreferencesMapper extends EntityMapper<UserPreferencesDTO, UserPreferences> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    UserPreferencesDTO toDto(UserPreferences userPreferences);

    @Mapping(source = "userId", target = "user")
    UserPreferences toEntity(UserPreferencesDTO userPreferencesDTO);

    default UserPreferences fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserPreferences userPreferences = new UserPreferences();
        userPreferences.setId(id);
        return userPreferences;
    }
}
