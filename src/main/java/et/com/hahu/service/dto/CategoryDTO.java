package et.com.hahu.service.dto;

import io.swagger.annotations.ApiModel;
import java.io.Serializable;

/**
 * A DTO for the {@link et.com.hahu.domain.Category} entity.
 */
@ApiModel(description = "The Category entity contains category for posts\n@author A true hailemaryam")
public class CategoryDTO implements Serializable {
    
    private Long id;

    private String name;

    private String description;

    private Boolean recomendationCategory;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isRecomendationCategory() {
        return recomendationCategory;
    }

    public void setRecomendationCategory(Boolean recomendationCategory) {
        this.recomendationCategory = recomendationCategory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CategoryDTO)) {
            return false;
        }

        return id != null && id.equals(((CategoryDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CategoryDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", recomendationCategory='" + isRecomendationCategory() + "'" +
            "}";
    }
}
