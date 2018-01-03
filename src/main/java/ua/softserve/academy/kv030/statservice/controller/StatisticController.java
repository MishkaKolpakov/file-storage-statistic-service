package ua.softserve.academy.kv030.statservice.controller;

import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.softserve.academy.kv030.statservice.entity.Statistic;
import ua.softserve.academy.kv030.statservice.service.StatisticService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Miha on 23.12.2017.
 */
@RestController
public class StatisticController{

    private StatisticService statisticService;
    private Logger logger;

    @Autowired
    public StatisticController(StatisticService statisticService, Logger logger) {
        this.statisticService = statisticService;
        this.logger = logger;
    }

    @RequestMapping(value = "/stat/size/{userId}",
            produces = { "application/xml", "application/json" },
            method = RequestMethod.GET)
    public ResponseEntity<String> statSize(@ApiParam(value = "ID of user to get statistics for", required = true) @PathVariable("userId") String userId) {
        logger.info("UserId " + userId);
        long size = statisticService.getUploadedFilesSize(Long.valueOf(userId), "upload");

        return new ResponseEntity<>(String.valueOf(size), HttpStatus.OK);
    }

    @RequestMapping(value = "/updownrate/{userId}",
            produces = { "application/xml", "application/json" },
            method = RequestMethod.GET)
    public ResponseEntity<String> uploadDownloadRate(@ApiParam(value = "ID of user to get statistics for", required = true) @PathVariable("userId") String userId) {

        logger.info("UserId " + userId);
        String result = statisticService.getUploadDownloadRate(Long.valueOf(userId));

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
