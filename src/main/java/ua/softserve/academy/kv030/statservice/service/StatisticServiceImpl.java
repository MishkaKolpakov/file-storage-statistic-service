package ua.softserve.academy.kv030.statservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.gridfs.GridFSDBFile;
import org.slf4j.Logger;
import ua.softserve.academy.kv030.statservice.dao.StatisticStorage;
import ua.softserve.academy.kv030.statservice.entity.Statistic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Miha on 23.12.2017.
 */
@Service
public class StatisticServiceImpl implements StatisticService {

    private StatisticStorage statisticStorage;
    private Logger logger;

    @Autowired
    public StatisticServiceImpl(StatisticStorage fileStorage, Logger logger) {
        this.statisticStorage = fileStorage;
        this.logger = logger;
    }

    @Override
    public void saveStatistic(Statistic statistic) {

        byte[] statisticBytes;

        statisticBytes = statistic.toString().getBytes();

        statisticStorage.save(statistic, statisticBytes);
    }

    @Override
    public List<Statistic> getStatistic(Long userId, String operationName) {

        List<GridFSDBFile> gridFsdbFiles = statisticStorage.load(userId, operationName);

        ArrayList<Statistic> statistics = new ArrayList<>();

        for (GridFSDBFile file : gridFsdbFiles) {
            System.out.println(file.getMetaData());
            try {
                Statistic stat = new ObjectMapper().readValue(file.getInputStream(), Statistic.class);
                statistics.add(stat);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return statistics;
    }

    @Override
    public Long getUploadedFilesSize(Long userId, String operationName) {
        List<GridFSDBFile> gridFsdbFiles = statisticStorage.load(userId, operationName);
        Long size = 0L;
        for (GridFSDBFile file : gridFsdbFiles) {
            logger.info("Statistic metadata: " + file.getMetaData());

            size += (Long) file.getMetaData().get("size");
        }
        return size;
    }

    @Override
    public String getUploadDownloadRate(Long userId) {

        List<GridFSDBFile> gridFsdbFilesUpload = statisticStorage.load(userId, "upload");
        List<GridFSDBFile> gridFsdbFilesDownload = statisticStorage.load(userId, "download");

        return createJson(gridFsdbFilesUpload.size(), gridFsdbFilesDownload.size());
    }

    @Override
    public void deleteStatistic(String id) {

    }

    private String createJson(int upload, int download) {

        return "{\"upload\":" + upload + ", \"download\":" + download + "}";
    }
}
