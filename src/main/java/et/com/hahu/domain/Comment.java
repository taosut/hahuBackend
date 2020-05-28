package et.com.hahu.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * The Comment entity will contain comments on post, user or comment.\n@author A true hailemaryam
 */
@Entity
@Table(name = "comment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "posted_date")
    private Instant postedDate;

    @Column(name = "modified_date")
    private Instant modifiedDate;

    @OneToMany(mappedBy = "comment")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Comment> replies = new HashSet<>();

    @OneToMany(mappedBy = "comment")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Likes> likes = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "comments", allowSetters = true)
    private User user;

    @ManyToOne
    @JsonIgnoreProperties(value = "comments", allowSetters = true)
    private Post post;

    @ManyToOne
    @JsonIgnoreProperties(value = "replies", allowSetters = true)
    private Comment comment;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public Comment content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Instant getPostedDate() {
        return postedDate;
    }

    public Comment postedDate(Instant postedDate) {
        this.postedDate = postedDate;
        return this;
    }

    public void setPostedDate(Instant postedDate) {
        this.postedDate = postedDate;
    }

    public Instant getModifiedDate() {
        return modifiedDate;
    }

    public Comment modifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
        return this;
    }

    public void setModifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Set<Comment> getReplies() {
        return replies;
    }

    public Comment replies(Set<Comment> comments) {
        this.replies = comments;
        return this;
    }

    public Comment addReply(Comment comment) {
        this.replies.add(comment);
        comment.setComment(this);
        return this;
    }

    public Comment removeReply(Comment comment) {
        this.replies.remove(comment);
        comment.setComment(null);
        return this;
    }

    public void setReplies(Set<Comment> comments) {
        this.replies = comments;
    }

    public Set<Likes> getLikes() {
        return likes;
    }

    public Comment likes(Set<Likes> likes) {
        this.likes = likes;
        return this;
    }

    public Comment addLike(Likes likes) {
        this.likes.add(likes);
        likes.setComment(this);
        return this;
    }

    public Comment removeLike(Likes likes) {
        this.likes.remove(likes);
        likes.setComment(null);
        return this;
    }

    public void setLikes(Set<Likes> likes) {
        this.likes = likes;
    }

    public User getUser() {
        return user;
    }

    public Comment user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Post getPost() {
        return post;
    }

    public Comment post(Post post) {
        this.post = post;
        return this;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Comment getComment() {
        return comment;
    }

    public Comment comment(Comment comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Comment)) {
            return false;
        }
        return id != null && id.equals(((Comment) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Comment{" +
            "id=" + getId() +
            ", content='" + getContent() + "'" +
            ", postedDate='" + getPostedDate() + "'" +
            ", modifiedDate='" + getModifiedDate() + "'" +
            "}";
    }
}
