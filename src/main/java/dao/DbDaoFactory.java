package dao;

public class DbDaoFactory extends FactoryDao {


    @Override
    public MunicipalityDao createMunicipalityDao() {
        return new DbMunicipalityDao();
    }

    @Override
    public ReportDao createReportDao() {
        return new DbReportDao();
    }

    @Override
    public UserDao createUserDao() {
        return new DbUserDao();
    }



}
