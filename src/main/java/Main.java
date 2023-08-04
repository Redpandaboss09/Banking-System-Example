import org.json.JSONObject;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPooled;

import java.io.IOException;

import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.Scanner;
import java.security.MessageDigest;

public class Main {
    static boolean exitProgram = false;
    private static final int SID_LENGTH = 10;

    public static void main(String[] args) throws NoSuchAlgorithmException {
        Scanner input = new Scanner(System.in);
        JedisPooled jedis = new JedisPooled("localhost", 6379);
        MessageDigest hasher = MessageDigest.getInstance("SHA-256");

        while (exitProgram) {
            displayMainMenu();
            int choice = input.nextInt();

            switch (choice) {
                case 1 -> createAccount(input, hasher, jedis);
                case 2 -> loginAccount();
                case 3 -> deleteAccount();
                case 4 -> exitProgram = true;
            }
        }
    }

    public static void displayMainMenu() {
        System.out.println("---------------------------- WELCOME TO THE BANK OF PANDA ----------------------------");
        System.out.println(" ");
        System.out.println("                                PLEASE PICK AN OPTION:                                ");
        System.out.println("            1) Create an Account                      2) Log into Existing Account    ");
        System.out.println("            3) Delete an Account                      4) Exit Program                 ");
        System.out.println(" ");
        System.out.println("--------------------------------------------------------------------------------------");
        System.out.println(" ");
    }

    private static void createAccount(Scanner input, MessageDigest hasher, JedisPooled jedis) {
        clrScreen();
        System.out.println("Please provide your first and last name, divided by a space:");
        String[] fullName = input.nextLine().split("\\s+");

        clrScreen();
        System.out.println("Please provide your date of birth: (MM/DD/YY)");
        String DOB = input.nextLine();

        clrScreen();
        System.out.println("Provide your desired password which:");
        System.out.println("1) Must contain at least 8 characters");
        System.out.println("2) Must contain at least 1 special character");
        System.out.println("3) Must contain at least 1 uppercase character");
        System.out.println("4) Must contain at least 1 number");

        String password = input.nextLine();

        //Keep inputting until requirements met
        while (!isSecure(password)) {
            password = input.nextLine();
        }

        //Hash the password
        hasher.update(password.getBytes());
        byte[] byteArray = hasher.digest();
        String hexadecimal = toHex(byteArray);

        clrScreen();
        System.out.println("Set desired account balance: ");
        double balance = input.nextDouble();

        String SID = generateSID();

        Client client = new Client(balance, fullName[0], fullName[1], DOB, SID, password);

        JSONObject user = new JSONObject();
        user.put("First Name", fullName[0]);
        user.put("Last Name", fullName[1]);
        user.put("DOB", DOB);
        user.put("Password", hexadecimal);
        user.put("Balance", balance);
        user.put("Permission", "Client");
        user.put("Visibility", "True");

        jedis.set(SID, user.toString());
    }

    public static void loginAccount() {

    }

    public static void deleteAccount() {

    }

    //Loops through inputted "password" to examine if requirements are met
    private static boolean isSecure(String password) {
        int numSpecialCharacters = 0;
        int numUppercase = 0;
        int numNumber = 0;

        if (password.length() >= 8) {
            for (int i = 0; i < password.length(); i++) {
                if (Character.isUpperCase(password.charAt(i))) {
                    numUppercase++;
                } else if (Character.isDigit(password.charAt(i))) {
                    numNumber++;
                } else {
                    numSpecialCharacters++;
                }
            }

            if (numNumber >= 1 && numUppercase >= 1 && numSpecialCharacters >= 1) {
                return true;
            }
        }

        return false;
    }

    //Clear console (ANSI)
    public static void clrScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    //Convert to hexadecimal
    private static String toHex(byte[] toBeHexed) {
        StringBuffer hexed = new StringBuffer();

        for (int i = 0; i < toBeHexed.length; i++) {
            hexed.append(Integer.toHexString(0xFF & toBeHexed[i]));
        }

        return hexed.toString();
    }

    private static String generateSID() {
        StringBuilder SID = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i <= SID_LENGTH; i++) {
            int randChoice = random.nextInt(2);

            switch (randChoice) {
                case 0 -> {
                    String randNum = String.valueOf(random.nextInt(10));
                    SID.append(randNum);
                    break;
                }
                case 1 -> {
                    String randLetter = String.valueOf((char)(random.nextInt(26) + 'a'));
                    SID.append(randLetter);
                    break;
                }
            }
        }

        return SID.toString();
    }
}