package com.risingarjun.arjun.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.risingarjun.arjun.domain.UserPreferences} entity.
 */
public class UserPreferencesDTO implements Serializable {

    private Long id;

    private String theme;


    private Long userId;

    private String userLogin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserPreferencesDTO userPreferencesDTO = (UserPreferencesDTO) o;
        if (userPreferencesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userPreferencesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserPreferencesDTO{" +
            "id=" + getId() +
            ", theme='" + getTheme() + "'" +
            ", user=" + getUserId() +
            ", user='" + getUserLogin() + "'" +
            "}";
    }
}
