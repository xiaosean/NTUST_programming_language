package hw1;

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

    // public methods for every bank accounts
    public String name() {
        return (accountName);
    }

    public double balance() {
        return (accountBalance);
    }

    public double deposit(double amount) throws BankingException {
        accountBalance += amount;
        return (accountBalance);
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
    private boolean checkDate(Date transactionDate,int month){
        if((openDate.getMonth()-transactionDate.getMonth() > month || (openDate.getDate()-transactionDate.getDate() == month && (openDate.getDay()-transactionDate.getDay() > 0){
            return true;
        }
        return false;
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
        accountName = s;
        accountBalance = firstDeposit;
        accountInterestRate = 0.12;
        openDate = new Date();
        lastInterestDate = openDate;
    }

    CheckingAccount(String s, double firstDeposit, Date firstDate) {
        accountName = s;
        accountBalance = firstDeposit;
        accountInterestRate = 0.12;
        openDate = firstDate;
        lastInterestDate = openDate;
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
    SavingAccount(String s, double firstDeposit) {
        accountName = s;
        accountBalance = firstDeposit;
        accountInterestRate = 0.12;
        openDate = new Date();
        lastInterestDate = openDate;
    }
    SavingAccount(String s, double firstDeposit, Date firstDate) {
        accountName = s;
        accountBalance = firstDeposit;
        accountInterestRate = 0.12;
        openDate = firstDate;
        lastInterestDate = openDate;
    }
    public double withdraw(double amount, Date withdrawDate) throws BankingException {
        int extraFee = 0;//use transaction extra fee if the first three per month
        if(checkDate(withdrawDate, 3)) // if it is not
            extraFee = 1;
        if ((accountBalance - amount - extraFee) < 0 ) {
            throw new BankingException("Underfraft from checking account name:" + accountName);
        } else {
            accountBalance -= amount + extraFee;
            return (accountBalance);
        }

    }
    public double computeInterest(Date interestDate) throws BankingException {
        if (interestDate.before(lastInterestDate)) {
            throw new BankingException("Invalid date to compute interest for account name" + accountName);
        }

        int numberOfDays = (int) ((interestDate.getTime()
                - lastInterestDate.getTime())
                / 86400000.0);
        System.out.println("Number of days since last interest is " + numberOfDays);
        double interestEarned = (double) numberOfDays / 365.0 * accountInterestRate * accountBalance;
        System.out.println("Interest earned is " + interestEarned);
        lastInterestDate = interestDate;
        accountBalance += interestEarned;
        return (accountBalance);
    }
}