package ua.softserve.academy.kv030.statservice.dao;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoException;
import com.mongodb.gridfs.GridFSDBFile;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Component;
import ua.softserve.academy.kv030.statservice.entity.Statistic;

import java.io.ByteArrayInputStream;
import java.util.List;

/**
 * Created by Miha on 23.12.2017.
 */
public class StatisticStorageImpl implements StatisticStorage {

    private GridFsTemplate gridFsTemplate;
    private Logger logger;

    @Autowired
    public StatisticStorageImpl(GridFsTemplate gridFsTemplate, Logger logger) {
        this.gridFsTemplate = gridFsTemplate;
        this.logger = logger;
    }

    @Override
    public void save(Statistic statistic, byte[] bytes) {
        logger.debug("Storing file into MongoDB");

        if (!gridFsTemplate.find(createFindByIdQuery(statistic.getId())).isEmpty()) {
            MongoException mongoException = new MongoException("File with specified id already exist");
            logger.error(mongoException.getMessage(), mongoException);
            throw mongoException;
        }

        DBObject metaData = new BasicDBObject();
        metaData.put("userId", statistic.getUserId());
        metaData.put("operationName", statistic.getOperationName());
        metaData.put("size", statistic.getSize());
        gridFsTemplate.store(new ByteArrayInputStream(bytes), statistic.getOperationName() + ":" + statistic.getId(), metaData);
    }

    @Override
    public List<GridFSDBFile> load(Long userId, String operationName) {
        logger.info("Loading statistics from MongoDB");

        return gridFsTemplate.find(new Query(Criteria.where("metadata.userId").is(userId))
                .addCriteria(Criteria.where("metadata.operationName").is(operationName)));
    }


    @Override
    public void delete(String id) {
        logger.debug("Deleting file with id " + id);
        gridFsTemplate.delete(createFindByIdQuery(id));
    }

    private Query createFindByIdQuery(String id) {
        return new Query().addCriteria(Criteria.where("filename").is(id));
    }
}
