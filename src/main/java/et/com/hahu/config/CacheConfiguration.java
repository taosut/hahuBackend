package et.com.hahu.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import org.hibernate.cache.jcache.ConfigSettings;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import io.github.jhipster.config.cache.PrefixedKeyGenerator;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {
    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, et.com.hahu.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, et.com.hahu.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, et.com.hahu.domain.User.class.getName());
            createCache(cm, et.com.hahu.domain.Authority.class.getName());
            createCache(cm, et.com.hahu.domain.User.class.getName() + ".authorities");
            createCache(cm, et.com.hahu.domain.Category.class.getName());
            createCache(cm, et.com.hahu.domain.Category.class.getName() + ".posts");
            createCache(cm, et.com.hahu.domain.Tag.class.getName());
            createCache(cm, et.com.hahu.domain.Tag.class.getName() + ".posts");
            createCache(cm, et.com.hahu.domain.Post.class.getName());
            createCache(cm, et.com.hahu.domain.Post.class.getName() + ".comments");
            createCache(cm, et.com.hahu.domain.Post.class.getName() + ".likes");
            createCache(cm, et.com.hahu.domain.Post.class.getName() + ".categories");
            createCache(cm, et.com.hahu.domain.Post.class.getName() + ".tags");
            createCache(cm, et.com.hahu.domain.Comment.class.getName());
            createCache(cm, et.com.hahu.domain.Comment.class.getName() + ".replies");
            createCache(cm, et.com.hahu.domain.Comment.class.getName() + ".likes");
            createCache(cm, et.com.hahu.domain.Likes.class.getName());
            createCache(cm, et.com.hahu.domain.UsersConnection.class.getName());
            createCache(cm, et.com.hahu.domain.Album.class.getName());
            createCache(cm, et.com.hahu.domain.Album.class.getName() + ".images");
            createCache(cm, et.com.hahu.domain.Image.class.getName());
            createCache(cm, et.com.hahu.domain.UserGroup.class.getName());
            createCache(cm, et.com.hahu.domain.UserGroup.class.getName() + ".additionalUserInfos");
            createCache(cm, et.com.hahu.domain.Notification.class.getName());
            createCache(cm, et.com.hahu.domain.SchoolProgress.class.getName());
            createCache(cm, et.com.hahu.domain.Schedule.class.getName());
            createCache(cm, et.com.hahu.domain.Profile.class.getName());
            createCache(cm, et.com.hahu.domain.Post.class.getName() + ".postMetaData");
            createCache(cm, et.com.hahu.domain.PostMetaData.class.getName());
            createCache(cm, et.com.hahu.domain.Image.class.getName() + ".imageMetaData");
            createCache(cm, et.com.hahu.domain.ImageMetaData.class.getName());
            createCache(cm, et.com.hahu.domain.UserGroup.class.getName() + ".users");
            createCache(cm, et.com.hahu.domain.Profile.class.getName() + ".families");
            createCache(cm, et.com.hahu.domain.UserGroup.class.getName() + ".owners");
            createCache(cm, et.com.hahu.domain.UserGroup.class.getName() + ".notifications");
            createCache(cm, et.com.hahu.domain.UserGroup.class.getName() + ".schedules");
            createCache(cm, et.com.hahu.domain.Notification.class.getName() + ".notificationMetaData");
            createCache(cm, et.com.hahu.domain.NotificationMetaData.class.getName());
            createCache(cm, et.com.hahu.domain.ScheduleType.class.getName());
            createCache(cm, et.com.hahu.domain.ScheduleType.class.getName() + ".schedules");
            createCache(cm, et.com.hahu.domain.School.class.getName());
            createCache(cm, et.com.hahu.domain.School.class.getName() + ".userGroups");
            createCache(cm, et.com.hahu.domain.School.class.getName() + ".users");
            createCache(cm, et.com.hahu.domain.Post.class.getName() + ".views");
            createCache(cm, et.com.hahu.domain.Post.class.getName() + ".shares");
            createCache(cm, et.com.hahu.domain.Shares.class.getName());
            createCache(cm, et.com.hahu.domain.Views.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache == null) {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
