package ua.softserve.academy.kv030.statservice.service;

import ua.softserve.academy.kv030.statservice.entity.Statistic;

import java.util.List;

/**
 * Created by Miha on 23.12.2017.
 */
public interface StatisticService {

    void saveStatistic(Statistic statistic);

    List<Statistic> getStatistic(Long userId, String operationName);

    Long getUploadedFilesSize(Long userId, String operationName);

    String getUploadDownloadRate(Long userId);

    void deleteStatistic(String id);
}
