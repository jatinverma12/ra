package com.risingarjun.arjun.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import org.hibernate.cache.jcache.ConfigSettings;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

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
            createCache(cm, com.risingarjun.arjun.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.risingarjun.arjun.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.risingarjun.arjun.domain.User.class.getName());
            createCache(cm, com.risingarjun.arjun.domain.Authority.class.getName());
            createCache(cm, com.risingarjun.arjun.domain.User.class.getName() + ".authorities");
            createCache(cm, com.risingarjun.arjun.domain.Blog.class.getName());
            createCache(cm, com.risingarjun.arjun.domain.Entry.class.getName());
            createCache(cm, com.risingarjun.arjun.domain.Entry.class.getName() + ".tags");
            createCache(cm, com.risingarjun.arjun.domain.Tag.class.getName());
            createCache(cm, com.risingarjun.arjun.domain.Tag.class.getName() + ".entries");
            createCache(cm, com.risingarjun.arjun.domain.Jhiauthority.class.getName());
            createCache(cm, com.risingarjun.arjun.domain.Features.class.getName());
            createCache(cm, com.risingarjun.arjun.domain.RoleAccess.class.getName());
            createCache(cm, com.risingarjun.arjun.domain.UserDetails.class.getName());
            createCache(cm, com.risingarjun.arjun.domain.UserPreferences.class.getName());
            createCache(cm, com.risingarjun.arjun.domain.Courses.class.getName());
            createCache(cm, com.risingarjun.arjun.domain.Courses.class.getName() + ".students");
            createCache(cm, com.risingarjun.arjun.domain.Courses.class.getName() + ".studentsubjects");
            createCache(cm, com.risingarjun.arjun.domain.Courses.class.getName() + ".teachers");
            createCache(cm, com.risingarjun.arjun.domain.Students.class.getName());
            createCache(cm, com.risingarjun.arjun.domain.Students.class.getName() + ".courses");
            createCache(cm, com.risingarjun.arjun.domain.Subjects.class.getName());
            createCache(cm, com.risingarjun.arjun.domain.Subjects.class.getName() + ".studentsubjects");
            createCache(cm, com.risingarjun.arjun.domain.Subjects.class.getName() + ".teachers");
            createCache(cm, com.risingarjun.arjun.domain.AcademicSessions.class.getName());
            createCache(cm, com.risingarjun.arjun.domain.StudentsSubjects.class.getName());
            createCache(cm, com.risingarjun.arjun.domain.StudentsSubjects.class.getName() + ".subjects");
            createCache(cm, com.risingarjun.arjun.domain.StudentsSubjects.class.getName() + ".courses");
            createCache(cm, com.risingarjun.arjun.domain.SubjectsBaseFee.class.getName());
            createCache(cm, com.risingarjun.arjun.domain.Discounts.class.getName());
            createCache(cm, com.risingarjun.arjun.domain.Scholarships.class.getName());
            createCache(cm, com.risingarjun.arjun.domain.StudentFees.class.getName());
            createCache(cm, com.risingarjun.arjun.domain.Employees.class.getName());
            createCache(cm, com.risingarjun.arjun.domain.Centers.class.getName());
            createCache(cm, com.risingarjun.arjun.domain.Centers.class.getName() + ".centerheads");
            createCache(cm, com.risingarjun.arjun.domain.CenterHead.class.getName());
            createCache(cm, com.risingarjun.arjun.domain.CenterHead.class.getName() + ".centers");
            createCache(cm, com.risingarjun.arjun.domain.Teachers.class.getName());
            createCache(cm, com.risingarjun.arjun.domain.Teachers.class.getName() + ".subjects");
            createCache(cm, com.risingarjun.arjun.domain.Teachers.class.getName() + ".courses");
            createCache(cm, com.risingarjun.arjun.domain.TeachersShare.class.getName());
            createCache(cm, com.risingarjun.arjun.domain.SalariesPayment.class.getName());
            createCache(cm, com.risingarjun.arjun.domain.Expenses.class.getName());
            createCache(cm, com.risingarjun.arjun.domain.Chapters.class.getName());
            createCache(cm, com.risingarjun.arjun.domain.Questions.class.getName());
            createCache(cm, com.risingarjun.arjun.domain.StudentScore.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cm.destroyCache(cacheName);
        }
        cm.createCache(cacheName, jcacheConfiguration);
    }
}
