package hw1;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Xiao on 2016/11/26.
 */
class BankingException extends Exception {
    BankingException() {
        super();
    }

    BankingException(String s) {
        super(s);
    }
}

interface BasicAccount {
    String name();

    double balance();
}

interface WithdrawableAccount extends BasicAccount {
    double withdraw(double amount) throws BankingException;
}

interface DepositableAccount extends BasicAccount {
    double deposit(double amount) throws BankingException;
}

interface InterestableAccount extends BasicAccount {
    double computeInterest() throws BankingException;
}

interface FullFunctionalAccount extends WithdrawableAccount,
        DepositableAccount,
        InterestableAccount {
}

public abstract class Account {

    // protected variables to store commom attributes for every bank accounts
    protected String accountName;
    protected double accountBalance;
    protected double accountInterestRate;
    protected Date openDate;
    protected Date lastInterestDate;
    protected String accountType;

    // public methods for every bank accounts
    public String name() {
        return (accountName);
    }

    public double balance() {
        return (accountBalance);
    }

    abstract double deposit(double amount, Date withdrawDate) throws BankingException;

    public double deposit(double amount) throws BankingException {
        Date depositDate = new Date();
        return (deposit(amount, depositDate));
    }

    abstract double withdraw(double amount, Date withdrawDate) throws BankingException;

    public double withdraw(double amount) throws BankingException {
        Date withdrawDate = new Date();
        return (withdraw(amount, withdrawDate));
    }

    abstract double computeInterest(Date interestDate) throws BankingException;

    public double computeInterest() throws BankingException {
        Date interestDate = new Date();
        return (computeInterest(interestDate));
    }
}

/*
 *  Derived class: CheckingAccount
 *
 *  Description:
 *      Interest is computed daily; there's no fee for
 *      withdraw; there is a minimum balance of $1000.
 */
class CheckingAccount extends Account implements FullFunctionalAccount {
    CheckingAccount(String s, double firstDeposit) {
        accountType = "CheckingAccount";
        accountName = s;
        accountBalance = firstDeposit;
        accountInterestRate = 0.12;
        lastInterestDate = openDate = new Date();
    }

    CheckingAccount(String s, double firstDeposit, Date firstDate) {
        accountType = "CheckingAccount";
        accountName = s;
        accountBalance = firstDeposit;
        accountInterestRate = 0.12;
        lastInterestDate = openDate = firstDate;
    }
    public double deposit(double amount, Date depositDate) throws BankingException {
        accountBalance += amount;
        return (accountBalance);
    }
    public double withdraw(double amount, Date withdrawDate) throws BankingException {
        // minimum balance is 1000, raise exception if violated
        if ((accountBalance - amount) < 1000) {
            throw new BankingException("Underfraft from checking account name:" + accountName);
        } else {
            accountBalance -= amount;
            return (accountBalance);
        }
    }

    public double computeInterest(Date interestDate) throws BankingException {
        if (interestDate.before(lastInterestDate)) {
            throw new BankingException("Invalid date to compute interest for account name" +
                    accountName);
        }
        int numberOfDays = (int) ((interestDate.getTime() - lastInterestDate.getTime()) / 86400000.0);
        System.out.println("Number of days since last interest is " + numberOfDays);
        double interestEarned = (double) numberOfDays / 365.0 * accountInterestRate * accountBalance;
        System.out.println("Interest earned is " + interestEarned);
        lastInterestDate = interestDate;
        accountBalance += interestEarned;
        return (accountBalance);
    }
}

/*
 *  Derived class: SavingAccount
 *
 *  Description:
 *     monthly interest; fee of $1 for every transaction, except
  the first three per month are free; no minimum balance.
 */
class SavingAccount extends Account implements FullFunctionalAccount {
    int transactionFee = 0;
    double transactionCountsInThisMonth = 0;
    Date lastTransactionDate;

    SavingAccount(String s, double firstDeposit) {
        accountType = "SavingAccount";
        accountName = s;
        accountBalance = firstDeposit;
        accountInterestRate = 0.12;
        openDate = new Date();
        lastInterestDate = lastTransactionDate = openDate;

    }

    SavingAccount(String s, double firstDeposit, Date firstDate) {
        accountType = "SavingAccount";
        accountName = s;
        accountBalance = firstDeposit;
        accountInterestRate = 0.12;
        openDate = firstDate;
        lastInterestDate = lastTransactionDate = openDate;
    }

    public double deposit(double amount, Date depositDate) throws BankingException {
        int extraFee = 0;//use transaction extra fee if the first three per month
        if (!checkLastUseInSameMonth(depositDate))
            transactionCountsInThisMonth = 0;
        else if (transactionCountsInThisMonth >= 2) // if it is not
            extraFee = 1;
        System.out.println("This month transaction already count (exclude this time) => " + transactionCountsInThisMonth);
        accountBalance += amount - extraFee;
        lastTransactionDate = depositDate;
        transactionCountsInThisMonth++;
        return (accountBalance);
    }

    private boolean checkLastUseInSameMonth(Date transactionDate) {
        System.out.println("Last transaction date:" +  lastInterestDate.toString());
        System.out.println("This transaction date:" +  transactionDate.toString());
        if (lastTransactionDate.getYear() == transactionDate.getYear() &&
                lastTransactionDate.getMonth() == transactionDate.getMonth())
            return true;
        return false;
    }

    public double withdraw(double amount, Date withdrawDate) throws BankingException {
        int extraFee = 0;//use transaction extra fee if the first three per month
        //check extra fee if this month already use 3 times.
        if (!checkLastUseInSameMonth(withdrawDate))
            transactionCountsInThisMonth = 0;
        else if (transactionCountsInThisMonth >= 3) // if it is not
            extraFee = 1;
        System.out.println("This month transaction count  (exclude this time) => " + transactionCountsInThisMonth);
        if ((accountBalance - amount - extraFee) < 0) {
            throw new BankingException("Underfraft from checking account name:" + accountName);
        } else { //success
            accountBalance -= amount + extraFee;
            lastTransactionDate = withdrawDate;
            transactionCountsInThisMonth++;
            return (accountBalance);
        }
    }

    public double computeInterest(Date interestDate) throws BankingException {
        if (interestDate.before(lastInterestDate)) {
            throw new BankingException("Invalid date to compute interest for account name" + accountName);
        }
        int numberOfMonth = (lastInterestDate.getYear() - interestDate.getYear()) * 12 + (lastInterestDate.getMonth() - interestDate.getMonth());
        System.out.println("Number of Months since last interest is " + numberOfMonth);
        double interestEarned = (double) numberOfMonth / 12 * accountInterestRate * accountBalance;
        System.out.println("Interest earned is " + interestEarned);
        lastInterestDate = interestDate;
        accountBalance += interestEarned;
        return (accountBalance);
    }
}

/*
 *  Derived class: CDAccount
 *
 *  Description:
 *     monthly interest; fee of $1 for every transaction, except
the first three per month are free; no minimum balance.
*/
class CDAccount extends Account implements FullFunctionalAccount {
    Calendar expiredDate = Calendar.getInstance();


    CDAccount(String s, double firstDeposit) {
        accountType = "CDAccount";
        accountName = s;
        accountBalance = firstDeposit;
        accountInterestRate = 0.3;
        lastInterestDate = openDate = new Date();
        expiredDate.setTime(lastInterestDate);
        expiredDate.add(Calendar.YEAR,1);
    }

    CDAccount(String s, double firstDeposit, Date firstDate) {
        accountType = "CDAccount";
        accountName = s;
        accountBalance = firstDeposit;
        accountInterestRate = 0.3;
        lastInterestDate = openDate = firstDate;
        expiredDate.setTime(lastInterestDate);
        expiredDate.add(Calendar.YEAR,1);
    }

    public double withdraw(double amount, Date withdrawDate) throws BankingException {
        int extraFee = 0;//use transaction extra fee if it isn's pass 1 year
        //check extra fee
        if (!withdrawDate.after(expiredDate.getTime()))
            extraFee = 250;
        if ((accountBalance - amount - extraFee) < 0) {
            throw new BankingException("Underfraft from checking account name:" + accountName);
        } else { //success
            accountBalance -= amount + extraFee;
            return (accountBalance);
        }
    }

    public double computeInterest(Date interestDate) throws BankingException {
        if (interestDate.before(lastInterestDate)) {
            throw new BankingException("Invalid date to compute interest for account name:" + accountName);
        }
        double interestEarned = 0;
        if (interestDate.after(expiredDate.getTime()))
            interestEarned = accountInterestRate * accountBalance;
        System.out.println("Interest earned is " + interestEarned);
        lastInterestDate = interestDate;
        accountBalance += interestEarned;
        return (accountBalance);
    }

    public double deposit(double amount, Date depositDate) throws BankingException {
        throw new BankingException("Invalid  to deposit  for account name" + accountName);
    }
}
/*
 *  Derived class: CDAccount
 *
 *  Description:
 *     like a saving account, but the balance is "negative" (you owe
  the bank money, so a deposit will reduce the amount of the loan);
  you can't withdraw (i.e., loan more money) but of course you can
  deposit (i.e., pay off part of the loan).
*/
class LoanAccount extends Account implements FullFunctionalAccount {

    LoanAccount(String s, double firstDeposit) {
        accountType = "LoanAccount";
        accountName = s;
        accountBalance = firstDeposit;
        accountInterestRate = 0.12;
        openDate = new Date();
        lastInterestDate = openDate;
    }

    LoanAccount(String s, double firstDeposit, Date firstDate) {
        accountType = "LoanAccount";
        accountName = s;
        accountBalance = firstDeposit;
        accountInterestRate = 0.12;
        lastInterestDate = openDate = firstDate;
    }

    public double withdraw(double amount, Date withdrawDate) throws BankingException {
        //check extra fee
        if ((accountBalance - amount) < 0) {
            throw new BankingException("Underfraft from checking account name:" + accountName);
        } else { //success
            accountBalance -= amount;
            return (accountBalance);
        }
    }

    public double computeInterest(Date interestDate) throws BankingException {
        if (interestDate.before(lastInterestDate)) {
            throw new BankingException("Invalid date to compute interest for account name" + accountName);
        }
        double interestEarned = 0;
        System.out.println("Interest earned is -" + interestEarned);
        lastInterestDate = interestDate;
        accountBalance -= interestEarned;
        return (accountBalance);
    }

    public double deposit(double amount, Date depositDate) throws BankingException {
        accountBalance += amount;
        return (accountBalance);
    }
}