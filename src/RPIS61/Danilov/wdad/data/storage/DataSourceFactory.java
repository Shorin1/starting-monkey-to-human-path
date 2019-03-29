package RPIS61.Danilov.wdad.data.storage;

import RPIS61.Danilov.wdad.data.managers.PreferencesManager;
import RPIS61.Danilov.wdad.utils.PreferencesManagerConstants;

import javax.sql.DataSource;


public class DataSourceFactory {
    public static DataSource createDataSource(){
        com.mysql.cj.jdbc.MysqlDataSource mds = new com.mysql.cj.jdbc.MysqlDataSource();
        PreferencesManager pm = PreferencesManager.getInstance();
        mds.setServerName(pm.getProperty(PreferencesManagerConstants.hostName));
        mds.setPortNumber(Integer.parseInt(pm.getProperty(PreferencesManagerConstants.port)));
        mds.setDatabaseName(pm.getProperty(PreferencesManagerConstants.DBName));
        mds.setUser(pm.getProperty(PreferencesManagerConstants.user));
        mds.setPassword(pm.getProperty(PreferencesManagerConstants.pass));
        return mds;
    }

    public static DataSource createDataSource(String className, String driverType, String host, int port, String dbName, String user, String password){
        com.mysql.cj.jdbc.MysqlDataSource mds = new com.mysql.cj.jdbc.MysqlDataSource();
        mds.setServerName(host);
        mds.setPortNumber(port);
        mds.setDatabaseName(dbName);
        mds.setUser(user);
        mds.setPassword(password);
        return mds;
    }
}
