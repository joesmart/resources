package com.feature.resources.server.config.morphia;

import com.feature.resources.server.domain.Graphic;
import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.google.code.morphia.validation.MorphiaValidation;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.UnknownHostException;

public class MorphiaGuiceModule extends AbstractModule {

    public static final Logger LOGGER = LoggerFactory.getLogger(MorphiaGuiceModule.class);

    private String mongoDbURL;

    private String mongoDbPort;

    @Override
    protected void configure() {

    }

    @Provides
    @Singleton
    public Datastore provideMorphia(@Named("mongoDB.url") String mongoDbURL, @Named("mongoDB.port") Integer mongoDbPort, @Named("mongoDb.db") String db) {
       // MongoURI mongoURI = new MongoURI(System.getenv("MONGOHQ_URL"));
        Mongo mongo = null;
        try {
//            if (mongoURI != null) {
//                mongo = mongoURI.connect();
//            } else {
//            }
            mongo = new Mongo(mongoDbURL, mongoDbPort);
            Morphia morphia = new Morphia();
            morphia.map(Graphic.class);
            Datastore ds = morphia.createDatastore(mongo, db);
            MorphiaValidation morphiaValidation = new MorphiaValidation();
            morphiaValidation.applyTo(morphia);
            ds.ensureIndexes();
            ds.ensureCaps();
            return ds;
        } catch (UnknownHostException e) {
            LOGGER.error("UnKnowHostException:", e);
        }catch (MongoException e) {
            LOGGER.error("MongoException:", e);
        }
        return null;
    }

}
