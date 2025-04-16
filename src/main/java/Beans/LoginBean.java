package Beans;

public class LoginBean {

   private String username;
   private String password;
   private String role;
   private String municipalityCode;

     public LoginBean(String username, String password, String role, String municipalityCode) {
         this.username = username;
         this.password = password;
         this.role = role;
         this.municipalityCode = municipalityCode;
     }



   public String getMunicipalityCode() {
        return municipalityCode;
    }

    public void setMunicipalityCode(String municipalityCode) {
        this.municipalityCode = municipalityCode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


}
