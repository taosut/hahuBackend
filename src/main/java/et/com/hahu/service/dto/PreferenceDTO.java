package et.com.hahu.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A DTO for the {@link et.com.hahu.domain.Preference} entity.
 */
public class PreferenceDTO implements Serializable {
    
    private Long id;

    private Boolean hasPrefereceSelected;


    private Long userId;

    private String userLogin;
    private Set<CategoryDTO> categories = new HashSet<>();
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isHasPrefereceSelected() {
        return hasPrefereceSelected;
    }

    public void setHasPrefereceSelected(Boolean hasPrefereceSelected) {
        this.hasPrefereceSelected = hasPrefereceSelected;
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

    public Set<CategoryDTO> getCategories() {
        return categories;
    }

    public void setCategories(Set<CategoryDTO> categories) {
        this.categories = categories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PreferenceDTO)) {
            return false;
        }

        return id != null && id.equals(((PreferenceDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PreferenceDTO{" +
            "id=" + getId() +
            ", hasPrefereceSelected='" + isHasPrefereceSelected() + "'" +
            ", userId=" + getUserId() +
            ", userLogin='" + getUserLogin() + "'" +
            ", categories='" + getCategories() + "'" +
            "}";
    }
}
