package com.cob.emr.audit;

import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AuditConfiguration {

    private final EntityManagerFactory entityManagerFactory;
    private final HibernateAuditListener hibernateAuditListener;

    public AuditConfiguration(EntityManagerFactory entityManagerFactory,
                              HibernateAuditListener hibernateAuditListener) {
        this.entityManagerFactory = entityManagerFactory;
        this.hibernateAuditListener = hibernateAuditListener;
    }

    @PostConstruct
    public void registerListeners() {
        SessionFactoryImpl sessionFactory = entityManagerFactory.unwrap(SessionFactoryImpl.class);
        EventListenerRegistry registry = sessionFactory.getServiceRegistry()
                .getService(EventListenerRegistry.class);
        registry.appendListeners(EventType.POST_INSERT, hibernateAuditListener);
        registry.appendListeners(EventType.POST_UPDATE, hibernateAuditListener);
        registry.appendListeners(EventType.POST_DELETE, hibernateAuditListener);
    }

    @Bean(name = "auditExecutor")
    public Executor auditExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(5);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("audit-");
        executor.initialize();
        return executor;
    }
}
