package ua.softserve.academy.kv030.statservice.config;

import ua.softserve.academy.kv030.statservice.dao.StatisticStorage;
import ua.softserve.academy.kv030.statservice.dao.StatisticStorageImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

/**
 * Created by Miha on 23.12.2017.
 */

@Configuration
public class AppConfig {

    @Bean
    @Scope("prototype")
    public Logger logger(InjectionPoint ip) {
        return LoggerFactory.getLogger(ip.getMember().getDeclaringClass());
    }

    @Autowired
    private Logger logger;

    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Bean
    public StatisticStorage statisticStorage() {
        return new StatisticStorageImpl(gridFsTemplate, logger);
    }
}
