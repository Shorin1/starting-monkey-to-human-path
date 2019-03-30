package RPIS61.Danilov.wdad.learn.rmi;

import RPIS61.Danilov.wdad.data.managers.PreferencesManager;
import RPIS61.Danilov.wdad.learn.XmlDataManager;
import RPIS61.Danilov.wdad.learn.xml.Reader;
import RPIS61.Danilov.wdad.utils.PreferencesManagerConstants;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

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
            XmlDataManager service = (XmlDataManager) registry.lookup("XmlDataManager");
            List<Reader> readers = service.getReaders();
            for (Reader reader:readers){
                System.out.println(reader.getFirstName());
            }
        } catch (Exception e){
            e.printStackTrace();
        }



        
    }
}
