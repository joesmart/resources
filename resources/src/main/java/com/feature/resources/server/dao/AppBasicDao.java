package com.feature.resources.server.dao;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.dao.BasicDAO;
import com.google.inject.Inject;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class AppBasicDao<T, K> extends BasicDAO<T, K> {
    public static final Logger LOGGER = LoggerFactory.getLogger(AppBasicDao.class);
    protected GridFS gridFSStore;

    @Inject
    protected AppBasicDao(Datastore ds) {
        super(ds);
        gridFSStore = new GridFS(ds.getDB(), "file_store");
    }

    public Object create(String fileName, String mimeType, InputStream fileStream) throws IOException {
        GridFSInputFile localFile = gridFSStore.createFile(fileStream, fileName);
        localFile.setContentType(mimeType);
        localFile.save();
        return localFile.getId();
    }

    public void findAttachment(Object id, OutputStream out) {
        ObjectId objectId = new ObjectId(id.toString());
        GridFSDBFile gridFSDBFile = gridFSStore.find(objectId);
        try {
            gridFSDBFile.writeTo(out);
        } catch (IOException e) {
            LOGGER.info("Find attachment Error",e);
        }
    }

    public GridFSDBFile getGridFSDBFile(Object id) {
        ObjectId objectId = new ObjectId(id.toString());
        GridFSDBFile gridFSDBFile = gridFSStore.find(objectId);
        return gridFSDBFile;
    }

    public boolean deleteGridFSDBFile(Object id) {
        gridFSStore.remove(new ObjectId(id.toString()));
        return true;
    }
}
