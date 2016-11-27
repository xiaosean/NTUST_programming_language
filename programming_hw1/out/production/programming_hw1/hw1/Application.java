package hw1;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Xiao on 2016/11/26.
 */
public class Application {

    public static void main(String args[]) {
        Account[] accountList = new Account[4];
        // buid 4 different accounts in the same array
        accountList[0] = new CheckingAccount("John Smith", 1500.0);
        accountList[1] = new SavingAccount("William Hurt", 1200.0);
        accountList[2] = new CDAccount("Woody Allison", 1000.0);
        accountList[3] = new LoanAccount("Judi Foster", -1500.0);
        Account currentTestAccount = accountList[0];

        for (Account a:accountList) {
            System.out.println("");
            System.out.println("Type = " + a.accountType);
            System.out.println("--------------------------------");
            System.out.println("Account openDate = " + a.openDate);
            System.out.println("Account Name = " + a.accountName + " Account Balance = " + a.accountBalance);
        }

        System.out.println("My test data");
        System.out.println("=========================================================");
        double ret;
        Date now = new Date();
        currentTestAccount = accountList[0];
        System.out.println("從Checking Account領出$500");
        try {
            System.out.println("Withdraw Date: " + now);
            System.out.println("Account <" + currentTestAccount.name() + "> now withdraws $500.0");
            ret = currentTestAccount.withdraw(500.0);
            System.out.println("Account <" + currentTestAccount.name() + "> now has $" +  currentTestAccount.accountBalance + " balance\n");
        } catch (Exception e) {
            stdExceptionPrinting(e, currentTestAccount.balance());
        }

        System.out.println("從Checking Account再領出$1");
        try {
            System.out.println("Withdraw Date: " + now);
            System.out.println("Account <" + currentTestAccount.name() + "> now withdraws $1.0");
            ret = currentTestAccount.withdraw(1.0);
            System.out.println("Account <" + currentTestAccount.name() + "> now has $" +  currentTestAccount.accountBalance + " balance\n");
        } catch (Exception e) {
            stdExceptionPrinting(e, currentTestAccount.balance());
        }

        System.out.println("存$500到Checking Account");
        try {
            System.out.println("Deposit Date: " + now);
            System.out.println("Account <" + currentTestAccount.name() + "> now deposits $500.0");
            ret = currentTestAccount.deposit(500.0);
            System.out.println("Account <" + currentTestAccount.name() + "> now has $" + currentTestAccount.accountBalance + " balance\n");
        } catch (Exception e) {
            stdExceptionPrinting(e, currentTestAccount.balance());
        }
        currentTestAccount = accountList[1];
        System.out.println("從Saving Account領出$100 (This month count -> 1)");
        try {
            System.out.println("Withdraw Date: " + now);
            System.out.println("Account <" + currentTestAccount.name() + "> now withdraws $100.0");
            ret = currentTestAccount.withdraw(100.0);
            System.out.println("Account <" + currentTestAccount.name() + "> now has $" +  currentTestAccount.accountBalance + " balance\n");
        } catch (Exception e) {
            stdExceptionPrinting(e, currentTestAccount.balance());
        }

        System.out.println("從Saving Account領出$100 (This month count -> 2)");
        try {
            System.out.println("Withdraw Date: " + now);
            System.out.println("Account <" + currentTestAccount.name() + "> now withdraws $100.0");
            ret = currentTestAccount.withdraw(100.0);
            System.out.println("Account <" + currentTestAccount.name() + "> now has $" +  currentTestAccount.accountBalance + " balance\n");
        } catch (Exception e) {
            stdExceptionPrinting(e, currentTestAccount.balance());
        }

        System.out.println("從Saving Account領出$100 (This month count -> 3)");
        try {
            System.out.println("Withdraw Date: " + now);
            System.out.println("Account <" + currentTestAccount.name() + "> now withdraws $100.0");
            ret = currentTestAccount.withdraw(100.0);
            System.out.println("Account <" + currentTestAccount.name() + "> now has $" +  currentTestAccount.accountBalance + " balance\n");
        } catch (Exception e) {
            stdExceptionPrinting(e, currentTestAccount.balance());
        }

        System.out.println("從Saving Account領出$100 (This month count -> 4)");
        try {
            System.out.println("Withdraw Date: " + now);
            System.out.println("Account <" + currentTestAccount.name() + "> now withdraws $100.0");
            ret = currentTestAccount.withdraw(100.0);
            System.out.println("Account <" + currentTestAccount.name() + "> now has $" +  currentTestAccount.accountBalance + " balance\n");
        } catch (Exception e) {
            stdExceptionPrinting(e, currentTestAccount.balance());
        }
        System.out.println("從Saving Account領出$100 (This month count -> 5)");
        try {
            System.out.println("Withdraw Date: " + now);
            System.out.println("Account <" + currentTestAccount.name() + "> now withdraws $100.0");
            ret = currentTestAccount.withdraw(100.0);
            System.out.println("Account <" + currentTestAccount.name() + "> now has $" +  currentTestAccount.accountBalance + " balance\n");
        } catch (Exception e) {
            stdExceptionPrinting(e, currentTestAccount.balance());
        }
        System.out.println("從Saving Account存進$1000 (This month count -> 6)");
        try {
            System.out.println("Deposit Date: " + now);
            System.out.println("Account <" + currentTestAccount.name() + "> now deposit(1000)");
            ret = currentTestAccount.deposit(1000);
            System.out.println("Account <" + currentTestAccount.name() + "> now has $" +  currentTestAccount.accountBalance + " balance\n");
        } catch (Exception e) {
            stdExceptionPrinting(e, currentTestAccount.balance());
        }
        currentTestAccount = accountList[2];
        System.out.println("從CD Account存進$1000 ");
        try {
            System.out.println("Deposit Date: " + now);
            System.out.println("Account <" + currentTestAccount.name() + "> now deposit(1000)");
            ret = currentTestAccount.deposit(1000);
            System.out.println("Account <" + currentTestAccount.name() + "> now has $" +  currentTestAccount.accountBalance + " balance\n");
        } catch (Exception e) {
            stdExceptionPrinting(e, currentTestAccount.balance());
        }
        System.out.println("從CD Account 一年後檢查利息");
        try {
            Calendar nextYear = Calendar.getInstance();
            nextYear.add(Calendar.YEAR, 1);
            System.out.println("check Date: " + nextYear.getTime().toString());
            System.out.println("Account <" + currentTestAccount.name() + " check");
            ret = currentTestAccount.computeInterest(nextYear.getTime());
            System.out.println("Account <" + currentTestAccount.name() + "> now has $" +  currentTestAccount.accountBalance + " balance\n");
        } catch (Exception e) {
            stdExceptionPrinting(e, currentTestAccount.balance());
        }

        currentTestAccount = accountList[3];

        System.out.println("從Loan Account存進$1000 ");
        try {
            System.out.println("Deposit Date: " + now);
            System.out.println("Account <" + currentTestAccount.name() + "> now deposit(1000)");
            ret = currentTestAccount.deposit(1000);
            System.out.println("Account <" + currentTestAccount.name() + "> now has $" +  currentTestAccount.accountBalance + " balance\n");
        } catch (Exception e) {
            stdExceptionPrinting(e, currentTestAccount.balance());
        }
        System.out.println("從Loan Account提款$1000 ");
        try {
            System.out.println("Deposit Date: " + now);
            System.out.println("Account <" + currentTestAccount.name() + "> now deposit(1000)");
            ret = currentTestAccount.withdraw(1000);
            System.out.println("Account <" + currentTestAccount.name() + "> now has $" +  currentTestAccount.accountBalance + " balance\n");
        } catch (Exception e) {
            stdExceptionPrinting(e, currentTestAccount.balance());
        }
    }

    static void stdExceptionPrinting(Exception e, double balance) {
        System.out.println("EXCEPTION: Banking system throws a " + e.getClass() +
                " with message: \n\t" +
                "MESSAGE: " + e.getMessage());
        System.out.println("\tAccount balance remains $" + balance + "\n");
    }
}