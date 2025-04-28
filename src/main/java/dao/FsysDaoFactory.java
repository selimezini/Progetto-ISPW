package dao;

public class FsysDaoFactory extends FactoryDao{
    @Override
    public MunicipalityDao createMunicipalityDao() {
        return new FsysMunicipalityDao();
    }

    @Override
    public ReportDao createReportDao() {
        return new FsysReportDao();
    }

    @Override
    public UserDao createUserDao() {
        return new FsysUserDao();
    }
}
