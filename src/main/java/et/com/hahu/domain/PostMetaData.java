package et.com.hahu.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A PostMetaData.
 */
@Entity
@Table(name = "post_meta_data")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PostMetaData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "value")
    private String value;

    @Lob
    @Column(name = "blob_value")
    private byte[] blobValue;

    @Column(name = "blob_value_content_type")
    private String blobValueContentType;

    @ManyToOne
    @JsonIgnoreProperties(value = "postMetaData", allowSetters = true)
    private Post post;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public PostMetaData name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public PostMetaData value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public byte[] getBlobValue() {
        return blobValue;
    }

    public PostMetaData blobValue(byte[] blobValue) {
        this.blobValue = blobValue;
        return this;
    }

    public void setBlobValue(byte[] blobValue) {
        this.blobValue = blobValue;
    }

    public String getBlobValueContentType() {
        return blobValueContentType;
    }

    public PostMetaData blobValueContentType(String blobValueContentType) {
        this.blobValueContentType = blobValueContentType;
        return this;
    }

    public void setBlobValueContentType(String blobValueContentType) {
        this.blobValueContentType = blobValueContentType;
    }

    public Post getPost() {
        return post;
    }

    public PostMetaData post(Post post) {
        this.post = post;
        return this;
    }

    public void setPost(Post post) {
        this.post = post;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PostMetaData)) {
            return false;
        }
        return id != null && id.equals(((PostMetaData) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PostMetaData{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", value='" + getValue() + "'" +
            ", blobValue='" + getBlobValue() + "'" +
            ", blobValueContentType='" + getBlobValueContentType() + "'" +
            "}";
    }
}
