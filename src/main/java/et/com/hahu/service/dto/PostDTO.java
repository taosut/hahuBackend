package et.com.hahu.service.dto;

import io.swagger.annotations.ApiModel;
import java.time.Instant;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Lob;
import et.com.hahu.domain.enumeration.ContentType;
import et.com.hahu.domain.enumeration.PostType;

/**
 * A DTO for the {@link et.com.hahu.domain.Post} entity.
 */
@ApiModel(description = "The Post\nentity will contain both pages and posts.\n@author A true hailemaryam")
public class PostDTO implements Serializable {
    
    private Long id;

    private String title;

    @Lob
    private String content;

    private ContentType contentType;

    private PostType postType;

    @Lob
    private byte[] featuredImage;

    private String featuredImageContentType;
    private Instant postedDate;

    private Instant modifiedDate;

    private Instant instantPostEndDate;

    private Double popularityIndex;

    private Double trendingIndex;


    private Long userId;

    private String userLogin;
    private Set<CategoryDTO> categories = new HashSet<>();
    private Set<TagDTO> tags = new HashSet<>();

    private Long pageId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    public PostType getPostType() {
        return postType;
    }

    public void setPostType(PostType postType) {
        this.postType = postType;
    }

    public byte[] getFeaturedImage() {
        return featuredImage;
    }

    public void setFeaturedImage(byte[] featuredImage) {
        this.featuredImage = featuredImage;
    }

    public String getFeaturedImageContentType() {
        return featuredImageContentType;
    }

    public void setFeaturedImageContentType(String featuredImageContentType) {
        this.featuredImageContentType = featuredImageContentType;
    }

    public Instant getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(Instant postedDate) {
        this.postedDate = postedDate;
    }

    public Instant getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Instant getInstantPostEndDate() {
        return instantPostEndDate;
    }

    public void setInstantPostEndDate(Instant instantPostEndDate) {
        this.instantPostEndDate = instantPostEndDate;
    }

    public Double getPopularityIndex() {
        return popularityIndex;
    }

    public void setPopularityIndex(Double popularityIndex) {
        this.popularityIndex = popularityIndex;
    }

    public Double getTrendingIndex() {
        return trendingIndex;
    }

    public void setTrendingIndex(Double trendingIndex) {
        this.trendingIndex = trendingIndex;
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

    public Set<TagDTO> getTags() {
        return tags;
    }

    public void setTags(Set<TagDTO> tags) {
        this.tags = tags;
    }

    public Long getPageId() {
        return pageId;
    }

    public void setPageId(Long postId) {
        this.pageId = postId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PostDTO)) {
            return false;
        }

        return id != null && id.equals(((PostDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PostDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", content='" + getContent() + "'" +
            ", contentType='" + getContentType() + "'" +
            ", postType='" + getPostType() + "'" +
            ", featuredImage='" + getFeaturedImage() + "'" +
            ", postedDate='" + getPostedDate() + "'" +
            ", modifiedDate='" + getModifiedDate() + "'" +
            ", instantPostEndDate='" + getInstantPostEndDate() + "'" +
            ", popularityIndex=" + getPopularityIndex() +
            ", trendingIndex=" + getTrendingIndex() +
            ", userId=" + getUserId() +
            ", userLogin='" + getUserLogin() + "'" +
            ", categories='" + getCategories() + "'" +
            ", tags='" + getTags() + "'" +
            ", pageId=" + getPageId() +
            "}";
    }
}
