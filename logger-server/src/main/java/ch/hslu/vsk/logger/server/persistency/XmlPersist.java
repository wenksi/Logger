package ch.hslu.vsk.logger.server.persistency;

import ch.hslu.vsk.logger.common.LogMessage;
import ch.hslu.vsk.stringpersistor.api.StringPersistor;

public class XmlPersist implements Persistable {
    @Override
    public String build(LogMessage message) {
        throw new UnsupportedOperationException();
    }
}