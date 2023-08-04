import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Client extends Account {
    private double balance;
    private String firstName;
    private String lastName;
    private String DOB;

    public Client() {
        super();
    }

    public Client(double balance, String fName, String lName, String DOB, String SID, String password) {
        super("Client", SID, password);
        this.balance = balance;
        this.firstName = fName;
        this.lastName = lName;
        this.DOB = DOB;
    }

    protected void withdraw(int amount) throws InterruptedException {
        if (this.getPermission_type().equals("No Account")) {
            System.out.println("Invalid / No Account");
        }

        if (balance == 0) {
            System.out.println("Not enough money in account");
            System.out.println("Account needs minimum $5");
            System.out.println("Current amount: " + balance);
            System.out.println(" ");
            System.out.println("Would you like to take out a deposit? (Y/N)");

            int i = 0;
            while (i <= 3) {
                String choice = input.nextLine().toUpperCase();
                switch (choice) {
                    case "Y" -> {
                        System.out.println("How much would you like to deposit?");
                        deposit(input.nextDouble());
                        break;
                    }
                    case "N" -> {
                        System.out.println("Exiting withdraw...");
                        TimeUnit.SECONDS.sleep(2);
                        break;
                    }
                    default -> {
                        System.out.println("Invalid input, try again...");
                        TimeUnit.SECONDS.sleep(1);
                        i++;
                        continue;
                    }
                }
            }
        }
    }

    private void deposit(double amount) throws InterruptedException {
        balance += amount;

        System.out.println("Added $" + amount);
        System.out.println("Current balance: $" + balance);
        TimeUnit.SECONDS.sleep(2);
    }

    //Visibility of account to be viewed by admin (true by default)
    private void changeVisibility(boolean change) {
        this.visibility = change;
    }

    protected double getBalance() {
        return balance;
    }

    protected String getFirstName() {
        return firstName;
    }

    protected String getLastName() {
        return lastName;
    }

    protected String getDOB() {
        return DOB;
    }


}
