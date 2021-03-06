/*
 * ClientStringPersistorAdapter.java
 * Created on 09.11.2020
 *
 * Copyright(c) 2020 Boas Meier.
 * This software is the proprietary information of Boas Meier.
 */
package ch.hslu.vsk.logger.component;

import ch.hslu.vsk.logger.common.LogMessage;
import ch.hslu.vsk.logger.common.ObjectHelper;
import ch.hslu.vsk.stringpersistor.api.PersistedString;
import ch.hslu.vsk.stringpersistor.api.StringPersistor;
import ch.hslu.vsk.stringpersistor.impl.StringPersistorFile;
import java.io.File;
import java.time.Instant;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.logging.Logger;

/**
 * Code of Class ClientStringPersistorAdapter.
 *
 * @author Boas Meier
 */
public final class ClientStringPersistorAdapter implements ClientLogPersistor {

    private static final Logger LOG = Logger.getLogger(ClientStringPersistorAdapter.class.getName());

    private final StringPersistor persistor;
    private File file;

    public ClientStringPersistorAdapter() {
        this.persistor = new StringPersistorFile();
    }

    /**
     * Store a message to the StringPersistor.
     *
     * @param message LogMessage Object to store.
     */
    @Override
    public void save(final LogMessage message) {
        file = new File("." + File.separator + "tmp_" + message.getLoggerName() + "_cache.log");
        persistor.setFile(file);
        LOG.info("Persist to file: " + file);
        String objectAsString = ObjectHelper.objectToString(message);
        persistor.save(Instant.now(), objectAsString);
    }

    @Override
    public Queue<LogMessage> get() {
        if (file == null) {
            return new LinkedList<>();
        }
        Queue<LogMessage> strings = new LinkedList<>();
        persistor.setFile(file);
        List<PersistedString> tmp = persistor.get(Integer.MAX_VALUE);
        while (!tmp.isEmpty()) {
            LogMessage msg = (LogMessage) ObjectHelper.stringToObject(tmp.remove(0).getPayload());
            strings.add(msg);
        }
        LOG.info("Delete cache file (" + file + "): " + file.delete());
        Collections.reverse((List<LogMessage>) strings);
        return strings;
    }
}
