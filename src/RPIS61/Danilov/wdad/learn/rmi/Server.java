package RPIS61.Danilov.wdad.learn.rmi;

import RPIS61.Danilov.wdad.data.managers.PreferencesManager;
import RPIS61.Danilov.wdad.utils.PreferencesManagerConstants;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server {

    private static final String XML_FILE_PATH = "src\\wdad\\learn\\xml\\library.xml";
    private static final String BIND_NAME = "DataManager";

    private static Registry registry;

    public static void main(String[] args) {
        PreferencesManager pm = PreferencesManager.getInstance();

        System.setProperty("java.security.policy", pm.getProperty(PreferencesManagerConstants.policypath));
        System.setProperty("java.rmi.server.useCodebaseOnly", pm.getProperty(PreferencesManagerConstants.usecodebaseonly));
        System.setProperty("java.rmi.server.hostname", pm.getProperty(PreferencesManagerConstants.registryaddress));

        int port = Integer.parseInt(pm.getProperty(PreferencesManagerConstants.registryport));

        try {
            if (pm.getProperties().getProperty(PreferencesManagerConstants.createregistry).equals("yes")) {
                registry = LocateRegistry.createRegistry(port);
                System.out.println("Registry created");
            } else {
                registry = LocateRegistry.getRegistry(port);
                System.out.println("Registry initialized");
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        try {
            XmlDataManagerImpl xdmi = new XmlDataManagerImpl(XML_FILE_PATH);
            UnicastRemoteObject.exportObject(xdmi, 0);
            registry.bind(BIND_NAME, xdmi);
            pm.addBindedObject(BIND_NAME, "DataManager");
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Server working...");

        try {
            while (true) {
                Thread.sleep(Integer.MAX_VALUE);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
