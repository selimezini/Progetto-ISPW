package Model.users;

import Model.Municipality;

public class Employee extends User {

    private Municipality myMunicipality;


    public Employee(String username, String password , String role) {

        super(username, password, role);

    }


    public Municipality getMyMunicipality() {

        return myMunicipality;
    }


    public void setMyMunicipality(Municipality m) {

        myMunicipality = m;
    }


}
