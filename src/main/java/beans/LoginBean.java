package beans;

import exceptions.ValidationException;

public class LoginBean {

   private String username;
   private String password;
   private String role;
   private String municipalityCode;
   private String municipalityName;



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

    public String getMunicipalityName() {

       return municipalityName;
    }

    public void validate() {
        if (username == null || username.trim().isEmpty() ||
                password == null || password.trim().isEmpty()) {
            throw new ValidationException("Username e password non possono essere vuoti.");
        }
        if (username.length() > 20) {
            throw new ValidationException("Username non può superare 20 caratteri.");
        }

        boolean isEmployee = "Employee".equals(role);
        if (isEmployee) {
            if (municipalityCode == null || municipalityCode.trim().isEmpty()) {
                throw new ValidationException("Per il ruolo Employee il codice municipale è obbligatorio.");
            }

        }
    }




}
