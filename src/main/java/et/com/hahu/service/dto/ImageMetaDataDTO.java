package et.com.hahu.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link et.com.hahu.domain.ImageMetaData} entity.
 */
public class ImageMetaDataDTO implements Serializable {
    
    private Long id;

    private String name;

    private String value;


    private Long imageId;
    
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ImageMetaDataDTO)) {
            return false;
        }

        return id != null && id.equals(((ImageMetaDataDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ImageMetaDataDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", value='" + getValue() + "'" +
            ", imageId=" + getImageId() +
            "}";
    }
}
