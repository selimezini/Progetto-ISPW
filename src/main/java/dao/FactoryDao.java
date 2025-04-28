package dao;


import org.example.viewprova2.Main;

public abstract class FactoryDao {


    private static FactoryDao instance;

    protected FactoryDao() {}


    public static FactoryDao getInstance() {

    if(instance == null) {
        String storageMode = Main.getStorageMode();
        if(storageMode.equals("db")) {
            instance = new DbDaoFactory();
        }else if(storageMode.equals("demo")) {
            instance = new DemoDaoFactory();
        }else if (storageMode.equals("file")) {
            instance = new FsysDaoFactory();
        }
    }

    return instance;

    }


    public abstract  MunicipalityDao createMunicipalityDao();

    public abstract ReportDao createReportDao();

    public abstract UserDao createUserDao();

}
