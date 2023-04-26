package buisnessLayer;

public class FullName {
    private String firstname;
    private String lastname;

    public FullName(String firstName, String lastname) {
        this.firstname = firstName;
        this.lastname = lastname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getLastname() {
        return lastname;
    }
}
