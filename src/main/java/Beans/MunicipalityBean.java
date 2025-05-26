package Beans;

public class MunicipalityBean {

    private String name;
    private String region;


    public MunicipalityBean(String name, String region  ) {

        this.name = name;
        this.region = region;

    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


    public void setRegion(String region) {
        this.region = region;
    }

    public String getRegion() {
        return region;
    }

    @Override
    public String toString() {
        return name + " (" + region + ")";
    }


}
