package et.com.hahu.service.dto;

import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import javax.persistence.Lob;

/**
 * A DTO for the {@link et.com.hahu.domain.Image} entity.
 */
@ApiModel(description = "The Image entity will store images for user.\n@author A true hailemaryam")
public class ImageDTO implements Serializable {
    
    private Long id;

    @Lob
    private byte[] image;

    private String imageContentType;

    private Long albumId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ImageDTO)) {
            return false;
        }

        return id != null && id.equals(((ImageDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ImageDTO{" +
            "id=" + getId() +
            ", image='" + getImage() + "'" +
            ", albumId=" + getAlbumId() +
            "}";
    }
}
