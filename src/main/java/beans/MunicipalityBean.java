package beans;

public class MunicipalityBean {

    private String name;
    private String region;
    private String code;

    public MunicipalityBean(String name, String region, String code  ) {

        this.name = name;
        this.region = region;
        this.code = code;
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


    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {

        return name + " (" + region + ")";
    }


}
