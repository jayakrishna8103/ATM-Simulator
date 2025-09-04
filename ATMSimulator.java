package OOPS;
//
//import java.util.Scanner;
//
//public class BankATM {
//	public static void main(String[] args) {
//		BankAccount bank = new BankAccount("ACB123000", 00001111222333, "Jaya Krishna", (long) 12000, 123456,
//				987456321);
//		Scanner sc = new Scanner(System.in);
//		System.out.println("Welcome to ATM");
//		System.out.println("Enter Pin : ");
//		int pass = sc.nextInt();
//		if (pass == bank.getPin()) {
//			while (true) {
//				System.out.println(
//						"Choose the Options :\n 1.Balance Enquiry\n 2.Withdrawl\n 3.Deposit\n 4.Pin Change\n 5.Change Mobile No");
//				int opt = sc.nextInt();
//				switch (opt) {
//				case 1:
//					System.out.println("Your Balance is :" + bank.getBalance());
//					break;
//				case 2:
//					System.out.println("Enter Amount to Withdraw :");
//					long w_amount = sc.nextInt();
//					long remaining_amount = bank.getBalance() - w_amount;
//					if (remaining_amount > 1000) {
//						bank.setBalance(remaining_amount);
//						System.out.println("Withdrawl Success...");
//					} else
//						System.out.println("Minimum Balance should be 1000rs");
//					break;
//				case 3:
//					System.out.println("Enter Amount to Deposit :");
//					long d_amount = sc.nextInt();
//					long amount = bank.getBalance() + d_amount;
//					bank.setBalance(amount);
//					System.out.println("Deposit Success...");
//					break;
//				case 4:
//					System.out.println("Enter new pin to change");
//					int newpin1 = sc.nextInt();
//					System.out.println("Re-Enter new pin to change");
//					int newpin2 = sc.nextInt();
//					if (newpin1 == newpin2) {
//						bank.setPin(newpin1);
//						System.out.println("Pin Changed Succcessfully...");
//					} else
//						System.out.println("Re Entered pin doesn't match with 1st pin");
//					break;
//
//				case 5:
//					System.out.println("Enter Your Mobile Number to change :");
//					int mob = sc.nextInt();
//					bank.setMobile(mob);
//					System.out.println("Mobile Number Changed Succcessfully...");
//					break;
//
//				default:
//					System.out.println("Invalid option...");
//				}
//				System.out.println("Do you want to continue ? if Yes type \"true\" else \"false\"");
//				boolean s = sc.nextBoolean();
//				if (s)
//					continue;
//				else
//					break;
//
//			}
//		} else
//			System.out.println("Wrong : Enter the Pin Correctly");
//	}
//
//}
import java.util.*;

class Account {
    private long cardNumber;
    private int pin;
    private String name;
    private double balance;
    private Deque<String> miniStatement = new LinkedList<>();

    public Account(long cardNumber, int pin, String name, double balance) {
        this.cardNumber = cardNumber;
        this.pin = pin;
        this.name = name;
        this.balance = balance;
    }

    public long getCardNumber() {
        return cardNumber;
    }

    public int getPin() {
        return pin;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
        addTransaction("Deposited: " + amount);
    }

    public boolean withdraw(double amount) {
        if (amount > balance) {
            return false;
        }
        balance -= amount;
        addTransaction("Withdrawn: " + amount);
        return true;
    }

    public void transfer(Account receiver, double amount) {
        if (withdraw(amount)) {
            receiver.deposit(amount);
            addTransaction("Transferred " + amount + " to " + receiver.getName());
        }
    }

    public void addTransaction(String detail) {
        if (miniStatement.size() == 5) {
            miniStatement.pollFirst();
        }
        miniStatement.addLast(detail + " | Balance: " + balance);
    }

    public void printMiniStatement() {
        System.out.println("----- Mini Statement -----");
        for (String t : miniStatement) {
            System.out.println(t);
        }
        System.out.println("--------------------------");
    }
}

public class ATMSimulator {
    private static Map<Long, Account> accounts = new HashMap<>();
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n===== ATM Simulator =====");
            System.out.println("1. Create Account");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1 -> createAccount();
                case 2 -> login();
                case 3 -> {
                    System.out.println("Thank you for using ATM. Goodbye!");
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    private static void createAccount() {
        System.out.print("Enter Name: ");
        String name = sc.next();
        System.out.print("Enter Card Number: ");
        long cardNumber = sc.nextLong();
        if (accounts.containsKey(cardNumber)) {
            System.out.println("⚠ Account with this card number already exists!");
            return;
        }
        System.out.print("Enter 4-digit PIN: ");
        int pin = sc.nextInt();
        System.out.print("Enter Initial Deposit: ");
        double balance = sc.nextDouble();

        Account newAcc = new Account(cardNumber, pin, name, balance);
        accounts.put(cardNumber, newAcc);
        System.out.println("✅ Account created successfully!");
    }

    private static void login() {
        System.out.print("Enter Card Number: ");
        long cardNumber = sc.nextLong();
        System.out.print("Enter PIN: ");
        int pin = sc.nextInt();

        Account acc = accounts.get(cardNumber);
        if (acc != null && acc.getPin() == pin) {
            System.out.println("✅ Login Successful! Welcome " + acc.getName());
            atmMenu(acc);
        } else {
            System.out.println("❌ Invalid card number or PIN!");
        }
    }

    private static void atmMenu(Account acc) {
        while (true) {
            System.out.println("\n===== ATM Menu =====");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. Mini Statement");
            System.out.println("6. Logout");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1 -> System.out.println("Your Balance: " + acc.getBalance());
                case 2 -> {
                    System.out.print("Enter amount to deposit: ");
                    double amount = sc.nextDouble();
                    acc.deposit(amount);
                    System.out.println("✅ Deposited Successfully!");
                }
                case 3 -> {
                    System.out.print("Enter amount to withdraw: ");
                    double amount = sc.nextDouble();
                    if (acc.withdraw(amount)) {
                        System.out.println("✅ Withdrawn Successfully!");
                    } else {
                        System.out.println("❌ Insufficient Balance!");
                    }
                }
                case 4 -> {
                    System.out.print("Enter receiver card number: ");
                    long receiverCard = sc.nextLong();
                    Account receiver = accounts.get(receiverCard);
                    if (receiver == null) {
                        System.out.println("❌ Receiver account not found!");
                        break;
                    }
                    System.out.print("Enter amount to transfer: ");
                    double amount = sc.nextDouble();
                    if (acc.getBalance() < amount) {
                        System.out.println("❌ Insufficient Balance!");
                    } else {
                        acc.transfer(receiver, amount);
                        System.out.println("✅ Transferred Successfully!");
                    }
                }
                case 5 -> acc.printMiniStatement();
                case 6 -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }
}
