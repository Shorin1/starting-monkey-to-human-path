package RPIS61.Danilov.wdad.learn.rmi;

import RPIS61.Danilov.wdad.data.managers.PreferencesManager;
import RPIS61.Danilov.wdad.data.managers.DataManager;
import RPIS61.Danilov.wdad.utils.PreferencesManagerConstants;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
    public static void main(String[] args) {
        PreferencesManager pm = PreferencesManager.getInstance();

        int port = Integer.parseInt(pm.getProperty(PreferencesManagerConstants.registryport));
        String address = pm.getProperty(PreferencesManagerConstants.registryaddress);

        System.setProperty("java.rmi.server.hostname",address);
        System.setProperty("java.security.policy", pm.getProperty(PreferencesManagerConstants.policypath));
        System.setProperty("java.rmi.server.usecodebaseonly", pm.getProperty(PreferencesManagerConstants.usecodebaseonly));

        try {
            Registry registry = LocateRegistry.getRegistry(address, port);
            DataManager service = (DataManager) registry.lookup("DataManager");
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
