import java.util.Random; //Random number class
import java.util.Scanner; //User input
import java.util.concurrent.TimeUnit; //Time delay (For purposes of mimicking loading screen)

//Parent class that holds values that may be used in an account of any type
public class Account {
    Scanner input;

    private final String permission_type;
    private final String accountSID;
    protected boolean visibility;
    private String password;

    //Account invalid creation
    public Account() {
        permission_type = "No Account";
        accountSID = "INVALID / NONE";
        visibility = true;
        password = "Password";
    }

    //Accounts creation
    public Account(String permission_type, String SID, String password) {
        this.permission_type = permission_type;
        this.accountSID = SID;
        this.visibility = true;
        this.password = password;
    }

    protected String getPermission_type() {
        return permission_type;
    }

    protected String getAccountSID() {
        return accountSID;
    }

    protected boolean getVisibility() { return visibility; }
}
