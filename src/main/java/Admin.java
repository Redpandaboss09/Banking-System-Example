public class Admin extends Account {
    private String admin_id;

    public Admin(String SID, String password) {
        super("Admin", SID, password);
        this.admin_id = SID;
    }

    private Client createUser(double balance, String fName, String lName, String DOB, String SID, String password) {
        return new Client(balance, fName, lName, DOB, SID, password);
    }

    public double getClientBalance(Client other) {
        if (other.getVisibility()) {
            return other.getBalance();
        }

        return 0;
    }

    public String getClientSID(Client other) {
        if (other.getVisibility()) {
            return other.getAccountSID();
        }

        return "Invalid / No permissions";
    }

    public String getClientFullName(Client other) {
        if (other.getVisibility()) {
            return other.getFirstName() + " " + other.getLastName();
        }

        return "Invalid / No permissions";
    }

    public String getClientDOB(Client other) {
        if (other.getVisibility()) {
            return other.getDOB();
        }

        return "Invalid / No permissions";
    }
}