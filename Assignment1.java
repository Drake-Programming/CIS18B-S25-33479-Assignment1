import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * Represents a bank account that holds account name, number, and balance
 */

class BankAccount {
    // Declare bank variables
    private String accountHolderName;
    private int accountNumber;
    private double balance;

    /**
     * Gets the account holder's name
     * @return accountHolderName
     */
    public String getAccountName() { return accountHolderName; }

    /**
     * Gets account number
     * @return accountNumber
     */
    public int getAccountNumber() { return accountNumber; }

    /**
     * Gets account balance
     * @return balance
     */
    public double getBalance() { return balance; }

    /**
     * Contructs the bank account with holder name and balance
     * @param name
     * @param balance
     */
    public BankAccount(String name, double balance) {
        // Instantiate bank variables
        this.accountHolderName = name;
        this.balance = balance;
        assignAccountNumber();
    }

    /**
     * Assigns random positive number as the account number
     */
    private void assignAccountNumber() {
        Random random = new Random();

        this.accountNumber = Math.abs(random.nextInt());
    }

    /**
     * Increases account's balance by amount given if more than zero
     * @param amount
     */
    public void deposit(double amount) {
        if (amount > 0) {
            this.balance += amount;
        }
        else {
            System.out.println("Deposit amount needs to be a positive number.");
        }
    }

    /**
     * Decreases account's balance by amount given if the balance doesn't go below zero
     * @param amount
     */
    public void withdraw(double amount) {
        if (this.balance - amount >= 0) {
            this.balance -= amount;
        }
        else {
            System.out.println("Can't withdraw more than balance.\nCurrent balance is $" + getBalance());
        }
    }
}

/**
 * Represents the main bank program, provides methods for starting the program, adding or switching bank accounts, depositing, 
 * or withdrawing money from an account, and checking account balance
 */
class BankProgram {
    // Declares program variables
    private Scanner scanner;
    private ArrayList<BankAccount> accounts;
    private BankAccount currentAccount;
    private int choice;
    private boolean programRunning;
    

    /**
     *  Constructs the bank program
     */
    public BankProgram() {
        // Instantiates bank variables
        this.scanner = new Scanner(System.in);
        this.accounts = new ArrayList<>();
        this.currentAccount = null;
    }

    /**
     * Displays the main menu to the user and return the user's choice of option
     * @return choice: int
     */
    private int showMainMenu() {
        System.out.println("--Welcome to Simple Bank System--\n1. Create Account\n2. Switch Account\n3. Deposit Money\n4. Withdraw Money\n5. Check Balance\n6. Exit");
        System.out.print("Enter your choice: ");

        try {
            this.choice = this.scanner.nextInt();
            System.out.println("");
        } catch (java.util.InputMismatchException e) {
            System.out.println("\nInput only numeric values\n");
        }
        this.scanner.nextLine();
        return this.choice;
    }

    /**
     * Creates a new BankAccount object that is stores within the bank list of the pro gram
     */
    private void createAccount() {
        System.out.println("--Create Account Selected--");
        System.out.print("Enter account holder name: ");

        // Assign name
        String name = this.scanner.nextLine();

        System.out.print("Enter initial deposit: ");
        // Trying to catch non-numeric characters user can input
        try {
            // Assign deposit amount
            double initialDeposit = this.scanner.nextDouble();
            // Create new account
            BankAccount newAccount = new BankAccount(name, initialDeposit);
            // Add account to list
            this.accounts.add(newAccount);

            System.out.println("\nAccount created successfully! Account No: " + newAccount.getAccountNumber() + "\n");
        
            if (this.currentAccount == null) {
                this.currentAccount = newAccount;
                System.out.println("Current account is now set to " + currentAccount.getAccountName() + "\n");
            }
        } catch (java.util.InputMismatchException e) {
            System.out.println("\nInput only numeric values\n");
        }
        
        this.scanner.nextLine();
    }

    /**
     * Switches the current account of the bank program
     */
    private void switchAccount() {
        // Checks if account list is empty
        if (this.accounts.isEmpty()) {
            System.out.println("No accounts available. Please create an account first.\n");
        } else {
            // If list is not empty then it will display all of the accounts
            System.out.println("Available Accounts:");
            for (BankAccount account : this.accounts) {
                System.out.println("Account No: " + account.getAccountNumber() + " - " + account.getAccountName());
            }
            System.out.print("Enter account number to switch: ");
            try {
                int accountNumber = this.scanner.nextInt();
                boolean found = false;
            
                for (BankAccount account : this.accounts) {
                    if (account.getAccountNumber() == accountNumber) {
                        this.currentAccount = account;
                        found = true;
                        System.out.println("Switched to account: " + currentAccount.getAccountName() + "\n");
                    }
                }   if (!found) {
                System.out.println("Account not found!\n");
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("\nInput only numeric values\n");
            }
            scanner.nextLine();
        }
    }

    /**
     * Adds an amount specified by the user to the balance of the currently selected account
     */
    private void depositMoney() {
        if (this.currentAccount == null) {
            System.out.println("No account selected. Please create or switch to an account first.\n");
        } else {
            System.out.print("Enter amount to deposit: ");
            try {
                double depositAmount = this.scanner.nextDouble();
                this.currentAccount.deposit(depositAmount);
                System.out.println("Deposit successful! New balance: $" + currentAccount.getBalance() + "\n");
            } catch (java.util.InputMismatchException e) {
                System.out.println("\nInput only numeric values\n");
            }
            this.scanner.nextLine();
        }
    }

    /**
     * Removes an amount specified by the user to the balance of the currently selected account
     */
    private void withdrawMoney() {
        if (this.currentAccount == null) {
            System.out.println("No account selected. Please create or switch to an account first.\n");
        } else {
            System.out.print("Enter amount to withdraw: ");
            try {
                double withdrawAmount = scanner.nextDouble();
                this.currentAccount.withdraw(withdrawAmount);
            } catch (java.util.InputMismatchException e) {
                System.out.println("\nInput only numeric values\n");
            }
            this.scanner.nextLine();
        }
    }

    /**
     * Displays the current balance of the current account
     */
    private void checkBalance() {
        if (this.currentAccount == null) {
            System.out.println("No account selected. Please create or switch to an account first.\n");
        } else {
            System.out.println("Current balance: $" + this.currentAccount.getBalance() + "\n");
        }
    }

    /**
     * Ends the instance of the program
     */
    private void exitProgram() {
        this.programRunning = false;
        this.scanner.close();
        System.out.println("Thank you for using Simple Bank System!");
    }


    /**
     * Responsible for running the bank program
     */
    public void run() {

        this.programRunning = true;

        do {
            this.choice = showMainMenu();
            
            switch (this.choice) {
                case 1:
                    createAccount();
                    break;
                case 2:
                    switchAccount();
                    break;
                case 3:
                    depositMoney();
                    break;
                case 4:
                    withdrawMoney();
                    break;
                case 5:
                    checkBalance();
                    break;
                case 6:
                    exitProgram();
                    break;
            }
        } while (this.programRunning);
    }
}

class Assignment1 {
    public static void main(String args[]) {
        BankProgram program = new BankProgram();
        program.run();
    }
}