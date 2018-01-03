package ua.softserve.academy.kv030.statservice.dao;

import com.mongodb.gridfs.GridFSDBFile;
import ua.softserve.academy.kv030.statservice.entity.Statistic;

import java.util.List;

/**
 * Created by Miha on 23.12.2017.
 */
public interface StatisticStorage {
    /**
     * Save statistics with the specified id into the storage
     *
     * @param statistic
     * @param bytes statistic bytes
     */
    void save (Statistic statistic, byte[] bytes);

    /**
     * Load statistic with the specified id from the storage
     *
     * @param userId the file id
     * @return file bytes
     */
    List<GridFSDBFile> load (Long userId, String operationName);

    /**
     * Delete statistic with the specified id
     *
     * @param id the file id
     */
    void delete(String id);
}
