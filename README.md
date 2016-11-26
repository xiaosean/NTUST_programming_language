# NTUST_programming_language
HW1:
IDE:Intellij
SDK:1.8 java version "1.8.0_102"

Description
----
Java programming assignment                                 CS4001310
=====================================================================

Your job is to design and implement a simple simulation of a banking
system in Java. 

Note: the CS4001310 web pages contain a *sample* program with partial
implementation that can be used as the basis of your solution.

Problem description
-------------------

All bank accounts can perform the following basic operations:

- deposit: Deposit money into the account.

- withdraw: withdraw money from the account.

- name: Return the account's name.

- balance: Return the account's balance.

- compute_interest: Compute the interest accrues since the last time
  it was computed, and add it to the account balance.
  
There are 4 different kinds of accounts:

- Checking: interest is computed daily; there's no fee for 
  withdrawals; there is a minimum balance of $1000.
  
- Saving: monthly interest; fee of $1 for every transaction, except
  the first three per month are free; no minimum balance.
  
- CD: monthly interest; fixed amount and duration (e.g., you can open
  1 12-month CD for $5000; for the next 12 months you can't deposit
  anything and withdrawals cost a  $250 fee); at the end of the 
  duration the interest payments stop and you can withdraw w/o fee.
  
- Loan: like a saving account, but the balance is "negative" (you owe
  the bank money, so a deposit will reduce the amount of the loan);
  you can't withdraw (i.e., loan more money) but of course you can 
  deposit (i.e., pay off part of the loan).
  
You can assume that the interest rate of an account is determined when
it is created, and that it will not change.  You can use a simple
interest calculation that ignores compounding; e.g., if an account has
12% yearly interest you can use 12%/12 = 1% as the monthly rate and
12%/365 as the daily rate even though this is incorrect (of course).

If a method encounters an error condition (such as trying to withdraw
too much money from an account) it should use an exception to signal
the error to the caller.

Also write a simple test program that creates at lease one account of 
each type and uses each method at least once.  The test should also 
exercise the error cases.

Grading criteria
----------------

Grading will be based on the following criteria:

- Design (40%): flexibility, elegance / simplicity, reusability
  encapsulation, extensibility, proper use of inheritance & subtyping.
- Implementation (30%): correctness, completeness, coding style
  (meaningful variable names and indentation, etc.), clarity
- Documentation (20%): documentation (comments) of your interfaces,
  classes, and methods.
  
Some examples of *bad* design are

- putting functionality in the wrong place (e.g., implementing
  deposit() in the Application class.
  
- Not using dynamic dispatch where it should be used (e.g., writing
  code like "if (accouny.type == CheckingAcount) ....").
  
- Using meaningless names for classes or variables (e.g., Account1).

Deliverables
------------

You need to turn in the followings on the class FTP server:

- Your Java program files.  
- A text file that shows some test outputs.
- A one-page description of your program that includes a graphical
  illustration of your inheritance hierarchy.