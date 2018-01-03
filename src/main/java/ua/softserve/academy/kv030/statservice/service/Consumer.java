package ua.softserve.academy.kv030.statservice.service;

import org.slf4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.softserve.academy.kv030.statservice.entity.Statistic;

@Component
public class Consumer {

    private StatisticService statisticService;
    private Logger logger;

    @Autowired
    public Consumer(StatisticService statisticService, Logger logger) {
        this.statisticService = statisticService;
        this.logger = logger;
    }

    @RabbitListener(queues="${jsa.rabbitmq.queue}")
    public void receiveMessage(Statistic statistic) {


        logger.info("Received Message: " + statistic.getId() + ", Operation: " + statistic.getOperationName());

        statisticService.saveStatistic(statistic);

        logger.info("Success saving statistic " + statistic.getOperationName() + ", uuid" + statistic.getId());

    }
}
