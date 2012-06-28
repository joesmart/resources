package com.feature.resources.server.dao;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.io.Resources;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.util.JSON;
import de.flapdoodle.embedmongo.MongoDBRuntime;
import de.flapdoodle.embedmongo.MongodExecutable;
import de.flapdoodle.embedmongo.MongodProcess;
import de.flapdoodle.embedmongo.config.MongodConfig;
import de.flapdoodle.embedmongo.distribution.Version;
import de.flapdoodle.embedmongo.runtime.Network;
import lombok.ToString;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import javax.annotation.Nullable;
import java.io.IOException;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.List;

/**
 * User: ZouYanjian
 * Date: 12-6-19
 * Time: 下午3:59
 * FileName:BasicMongoUnitTest
 */
@ToString
public class BasicMongoUnitTest {
    private static final int port = 12345;
    private static MongodProcess mongodProcess;
    private static MongodExecutable mongodExecutable = null;
    private static final MongoDBRuntime runtime = MongoDBRuntime.getDefaultInstance();

    private static Mongo mongo;
    private static Datastore datastore = null;

    @BeforeClass
    public static void initDb() {
        try {
            mongodExecutable = runtime.prepare(new MongodConfig(Version.V2_1_1, port, Network.localhostIsIPv6()));
            mongodProcess = mongodExecutable.start();
            mongo = new Mongo("localhost", port);
            datastore = new Morphia().createDatastore(mongo, "test");
        } catch (UnknownHostException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    @AfterClass
    public static void shudownDB() {
        if (mongodProcess != null) mongodProcess.stop();
    }

    public static Datastore getDatastore() {
        return datastore;
    }

    public void initData(String collectionName) throws IOException {
        List<String> collectionLists = getResourceStringList(collectionName);
        DBCollection workSpacesColloection = getDatastore().getDB().getCollection(collectionName);
        for (String json : collectionLists) {
            System.out.println(json);
            DBObject dbObject = (DBObject) JSON.parse(json);
            workSpacesColloection.insert(dbObject);
        }
    }

    public List<String> getResourceStringList(String collectionName) {
        try {
            String resourceURL = "com/feature/resources/server/testdata/" + collectionName + ".json";
            URL workSpaceUrl = Resources.getResource(resourceURL);
            List<String> collectionLists = Resources.readLines(workSpaceUrl, Charset.forName("UTF-8"));
            return  collectionLists;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int resourceDataSize(String collectionName) {
        List<String> collections = getResourceStringList(collectionName);
        Preconditions.checkNotNull(collections);
        return collections.size();
    }

    protected List<String> getGraphicStringListByCheckStatus(final String statusDesc) {
        List<String> allGraphic = getResourceStringList("Graphic");
        List<String> checkedList = Lists.newArrayList(Iterables.filter(allGraphic, new Predicate<String>() {
            @Override
            public boolean apply(@Nullable String input) {
                boolean conditions = input.contains("\"checkStatus\":" +
                        "\"" +
                        statusDesc +
                        "\"");
                return conditions;
            }
        }));
        System.out.println(checkedList.size());
        return checkedList;
    }
}
