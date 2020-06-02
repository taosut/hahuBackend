package et.com.hahu.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import et.com.hahu.domain.enumeration.ContentType;

import et.com.hahu.domain.enumeration.PostType;

/**
 * The Post\nentity will contain both pages and posts.\n@author A true hailemaryam
 */
@Entity
@Table(name = "post")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Post implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "title")
    private String title;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "content")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "content_type")
    private ContentType contentType;

    @Enumerated(EnumType.STRING)
    @Column(name = "post_type")
    private PostType postType;

    @Lob
    @Column(name = "featured_image")
    private byte[] featuredImage;

    @Column(name = "featured_image_content_type")
    private String featuredImageContentType;

    @Column(name = "posted_date")
    private Instant postedDate;

    @Column(name = "modified_date")
    private Instant modifiedDate;

    @Column(name = "instant_post_end_date")
    private Instant instantPostEndDate;

    @Column(name = "popularity_index")
    private Double popularityIndex;

    @Column(name = "trending_index")
    private Double trendingIndex;

    @OneToMany(mappedBy = "post")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<PostMetaData> postMetaData = new HashSet<>();

    @OneToMany(mappedBy = "post")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Comment> comments = new HashSet<>();

    @OneToMany(mappedBy = "post")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Likes> likes = new HashSet<>();

    @OneToMany(mappedBy = "post")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Views> views = new HashSet<>();

    @OneToMany(mappedBy = "post")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Shares> shares = new HashSet<>();

    @OneToMany(mappedBy = "page")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Post> posts = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "posts", allowSetters = true)
    private User user;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "post_category",
               joinColumns = @JoinColumn(name = "post_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id"))
    private Set<Category> categories = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "post_tag",
               joinColumns = @JoinColumn(name = "post_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"))
    private Set<Tag> tags = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "posts", allowSetters = true)
    private Post page;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Post title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public Post content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public Post contentType(ContentType contentType) {
        this.contentType = contentType;
        return this;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    public PostType getPostType() {
        return postType;
    }

    public Post postType(PostType postType) {
        this.postType = postType;
        return this;
    }

    public void setPostType(PostType postType) {
        this.postType = postType;
    }

    public byte[] getFeaturedImage() {
        return featuredImage;
    }

    public Post featuredImage(byte[] featuredImage) {
        this.featuredImage = featuredImage;
        return this;
    }

    public void setFeaturedImage(byte[] featuredImage) {
        this.featuredImage = featuredImage;
    }

    public String getFeaturedImageContentType() {
        return featuredImageContentType;
    }

    public Post featuredImageContentType(String featuredImageContentType) {
        this.featuredImageContentType = featuredImageContentType;
        return this;
    }

    public void setFeaturedImageContentType(String featuredImageContentType) {
        this.featuredImageContentType = featuredImageContentType;
    }

    public Instant getPostedDate() {
        return postedDate;
    }

    public Post postedDate(Instant postedDate) {
        this.postedDate = postedDate;
        return this;
    }

    public void setPostedDate(Instant postedDate) {
        this.postedDate = postedDate;
    }

    public Instant getModifiedDate() {
        return modifiedDate;
    }

    public Post modifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
        return this;
    }

    public void setModifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Instant getInstantPostEndDate() {
        return instantPostEndDate;
    }

    public Post instantPostEndDate(Instant instantPostEndDate) {
        this.instantPostEndDate = instantPostEndDate;
        return this;
    }

    public void setInstantPostEndDate(Instant instantPostEndDate) {
        this.instantPostEndDate = instantPostEndDate;
    }

    public Double getPopularityIndex() {
        return popularityIndex;
    }

    public Post popularityIndex(Double popularityIndex) {
        this.popularityIndex = popularityIndex;
        return this;
    }

    public void setPopularityIndex(Double popularityIndex) {
        this.popularityIndex = popularityIndex;
    }

    public Double getTrendingIndex() {
        return trendingIndex;
    }

    public Post trendingIndex(Double trendingIndex) {
        this.trendingIndex = trendingIndex;
        return this;
    }

    public void setTrendingIndex(Double trendingIndex) {
        this.trendingIndex = trendingIndex;
    }

    public Set<PostMetaData> getPostMetaData() {
        return postMetaData;
    }

    public Post postMetaData(Set<PostMetaData> postMetaData) {
        this.postMetaData = postMetaData;
        return this;
    }

    public Post addPostMetaData(PostMetaData postMetaData) {
        this.postMetaData.add(postMetaData);
        postMetaData.setPost(this);
        return this;
    }

    public Post removePostMetaData(PostMetaData postMetaData) {
        this.postMetaData.remove(postMetaData);
        postMetaData.setPost(null);
        return this;
    }

    public void setPostMetaData(Set<PostMetaData> postMetaData) {
        this.postMetaData = postMetaData;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public Post comments(Set<Comment> comments) {
        this.comments = comments;
        return this;
    }

    public Post addComment(Comment comment) {
        this.comments.add(comment);
        comment.setPost(this);
        return this;
    }

    public Post removeComment(Comment comment) {
        this.comments.remove(comment);
        comment.setPost(null);
        return this;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Set<Likes> getLikes() {
        return likes;
    }

    public Post likes(Set<Likes> likes) {
        this.likes = likes;
        return this;
    }

    public Post addLike(Likes likes) {
        this.likes.add(likes);
        likes.setPost(this);
        return this;
    }

    public Post removeLike(Likes likes) {
        this.likes.remove(likes);
        likes.setPost(null);
        return this;
    }

    public void setLikes(Set<Likes> likes) {
        this.likes = likes;
    }

    public Set<Views> getViews() {
        return views;
    }

    public Post views(Set<Views> views) {
        this.views = views;
        return this;
    }

    public Post addViews(Views views) {
        this.views.add(views);
        views.setPost(this);
        return this;
    }

    public Post removeViews(Views views) {
        this.views.remove(views);
        views.setPost(null);
        return this;
    }

    public void setViews(Set<Views> views) {
        this.views = views;
    }

    public Set<Shares> getShares() {
        return shares;
    }

    public Post shares(Set<Shares> shares) {
        this.shares = shares;
        return this;
    }

    public Post addShares(Shares shares) {
        this.shares.add(shares);
        shares.setPost(this);
        return this;
    }

    public Post removeShares(Shares shares) {
        this.shares.remove(shares);
        shares.setPost(null);
        return this;
    }

    public void setShares(Set<Shares> shares) {
        this.shares = shares;
    }

    public Set<Post> getPosts() {
        return posts;
    }

    public Post posts(Set<Post> posts) {
        this.posts = posts;
        return this;
    }

    public Post addPost(Post post) {
        this.posts.add(post);
        post.setPage(this);
        return this;
    }

    public Post removePost(Post post) {
        this.posts.remove(post);
        post.setPage(null);
        return this;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }

    public User getUser() {
        return user;
    }

    public Post user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public Post categories(Set<Category> categories) {
        this.categories = categories;
        return this;
    }

    public Post addCategory(Category category) {
        this.categories.add(category);
        category.getPosts().add(this);
        return this;
    }

    public Post removeCategory(Category category) {
        this.categories.remove(category);
        category.getPosts().remove(this);
        return this;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public Post tags(Set<Tag> tags) {
        this.tags = tags;
        return this;
    }

    public Post addTag(Tag tag) {
        this.tags.add(tag);
        tag.getPosts().add(this);
        return this;
    }

    public Post removeTag(Tag tag) {
        this.tags.remove(tag);
        tag.getPosts().remove(this);
        return this;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public Post getPage() {
        return page;
    }

    public Post page(Post post) {
        this.page = post;
        return this;
    }

    public void setPage(Post post) {
        this.page = post;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Post)) {
            return false;
        }
        return id != null && id.equals(((Post) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Post{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", content='" + getContent() + "'" +
            ", contentType='" + getContentType() + "'" +
            ", postType='" + getPostType() + "'" +
            ", featuredImage='" + getFeaturedImage() + "'" +
            ", featuredImageContentType='" + getFeaturedImageContentType() + "'" +
            ", postedDate='" + getPostedDate() + "'" +
            ", modifiedDate='" + getModifiedDate() + "'" +
            ", instantPostEndDate='" + getInstantPostEndDate() + "'" +
            ", popularityIndex=" + getPopularityIndex() +
            ", trendingIndex=" + getTrendingIndex() +
            "}";
    }
}
