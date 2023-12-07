import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class BankApplication {

    public static void main(String[] args) {
        Bank bank = new Bank();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Create Account");
            System.out.println("2. Login");
            System.out.println("3. Exit");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    bank.createAccount(scanner);
                    break;
                case 2:
                    bank.login(scanner);
                    break;
                case 3:
                    System.out.println("Exiting the application. Goodbye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}

class Bank {
    private Map<String, Account> accounts = new HashMap<>();
    private Account currentUser;

    public void createAccount(Scanner scanner) {
        System.out.println("Enter your Name:");
        String name = scanner.next();

        System.out.println("Enter your Surname:");
        String surname = scanner.next();

        System.out.println("Create a Password:");
        String password = scanner.next();

        System.out.println("Create a PIN:");
        int pin = scanner.nextInt();

        Account newAccount = new Account(name, surname, password, pin);
        accounts.put(name, newAccount);
        System.out.println("Account created successfully!");
    }

    public void login(Scanner scanner) {
        System.out.println("Enter your Name:");
        String name = scanner.next();

        if (accounts.containsKey(name)) {
            System.out.println("Enter your PIN:");
            int pin = scanner.nextInt();

            Account account = accounts.get(name);

            if (account.authenticate(pin)) {
                currentUser = account;
                System.out.println("Login successful. Welcome, " + currentUser.getName() + "!");
                showMenu(scanner);
            } else {
                System.out.println("Incorrect PIN. Login failed.");
            }
        } else {
            System.out.println("Account not found. Please create an account first.");
        }
    }

    private void showMenu(Scanner scanner) {
        while (true) {
            System.out.println("1. Display Balance");
            System.out.println("2. Add Funds");
            System.out.println("3. Transfer Funds");
            System.out.println("4. Change PIN");
            System.out.println("5. Logout");
            System.out.println("6. Exit");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    currentUser.displayBalance();
                    break;
                case 2:
                    currentUser.addFunds(scanner);
                    break;
                case 3:
                    currentUser.transferFunds(accounts, scanner);
                    break;
                case 4:
                    currentUser.changePin(scanner);
                    break;
                case 5:
                    currentUser = null;
                    System.out.println("Logout successful.");
                    return;
                case 6:
                    System.out.println("Exiting the application. Goodbye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}

class Account {
    private String name;
    private String surname;
    private String password;
    private int pin;
    private double balance;

    public Account(String name, String surname, String password, int pin) {
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.pin = pin;
        this.balance = 0.0;
    }

    public String getName() {
        return name;
    }

    public void displayBalance() {
        System.out.println("Balance: $" + balance);
    }

    public boolean authenticate(int enteredPin) {
        return pin == enteredPin;
    }

    public void addFunds(Scanner scanner) {
        System.out.println("Enter amount to add:");
        double amount = scanner.nextDouble();
        balance += amount;
        System.out.println("Funds added successfully. New balance: $" + balance);
    }

    public void transferFunds(Map<String, Account> accounts, Scanner scanner) {
        System.out.println("Enter recipient's name:");
        String recipientName = scanner.next();

        if (accounts.containsKey(recipientName)) {
            System.out.println("Enter amount to transfer:");
            double transferAmount = scanner.nextDouble();

            if (balance >= transferAmount) {
                balance -= transferAmount;
                accounts.get(recipientName).addFunds(transferAmount);
                System.out.println("Transfer successful.");
            } else {
                System.out.println("Insufficient funds for the transfer.");
            }
        } else {
            System.out.println("Recipient account not found.");
        }
    }

    public void changePin(Scanner scanner) {
        System.out.println("Enter new PIN:");
        int newPin = scanner.nextInt();
        pin = newPin;
        System.out.println("PIN changed successfully.");
    }
}
