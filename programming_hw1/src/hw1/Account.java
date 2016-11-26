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
    double transactionCountsInThisMonth = 0;
    Date lastTransactionDate;

    SavingAccount(String s, double firstDeposit) {
        accountName = s;
        accountBalance = firstDeposit;
        accountInterestRate = 0.12;
        openDate = new Date();
        lastInterestDate = lastTransactionDate = openDate;

    }

    SavingAccount(String s, double firstDeposit, Date firstDate) {
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
        else if (transactionCountsInThisMonth > 3) // if it is not
            extraFee = 1;
        accountBalance += amount - extraFee;
        lastTransactionDate = depositDate;
        transactionCountsInThisMonth++;
        return (accountBalance);
    }

    private boolean checkLastUseInSameMonth(Date transactionDate) {
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
        else if (transactionCountsInThisMonth > 3) // if it is not
            extraFee = 1;
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
    Date expiredDate;// next year

    CDAccount(String s, double firstDeposit) {
        accountName = s;
        accountBalance = firstDeposit;
        accountInterestRate = 0.3;
        openDate = new Date();
        expiredDate = openDate;
        expiredDate.setYear(expiredDate.getYear() + 1);//next year same time

    }

    CDAccount(String s, double firstDeposit, Date firstDate) {
        accountName = s;
        accountBalance = firstDeposit;
        accountInterestRate = 0.3;
        openDate = firstDate;
        expiredDate = openDate;
        expiredDate.setYear(expiredDate.getYear() + 1);//next year same time
    }

    public double withdraw(double amount, Date withdrawDate) throws BankingException {
        int extraFee = 0;//use transaction extra fee if it isn's pass 1 year
        //check extra fee
        if (!withdrawDate.after(expiredDate))
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
            throw new BankingException("Invalid date to compute interest for account name" + accountName);
        }
        double interestEarned = 0;
        if (interestDate.after(expiredDate))
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