package dao;



public class DemoDaoFactory extends FactoryDao{


    @Override
    public UserDao createUserDao() {

        return   DemoUserDao.getInstance();
    }


    @Override
    public MunicipalityDao createMunicipalityDao() {

        return  DemoMunicipalityDao.getInstance();

    }


    @Override
    public ReportDao createReportDao() {
        return DemoReportDao.getInstance();
    }


}
