package ch.hslu.vsk.logger.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

/*
* Remote interface to  register viewer client.
 */
public interface RemoteLogger extends Remote {
    /**
     * Registers itself to the remote logger server.
     * @param client Callback receiver.
     * @throws RemoteException if remote methods fails.
     */
    void register(RemoteCallbackHandler client) throws RemoteException;
}
