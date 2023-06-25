package com.smallworld;

import com.smallworld.model.Transaction;

import java.util.ArrayList;
import java.util.List;

public class MockTransactionFactory {

    public static List<Transaction> getAllTransactions(boolean isEmpty) {

        if (isEmpty) {
            return new ArrayList<>();
        }

        Transaction transaction1 = new Transaction();
        transaction1.setMtn(663458);
        transaction1.setAmount(99999.0);
        transaction1.setSenderFullName("Tom Shelby");
        transaction1.setSenderAge(22);
        transaction1.setBeneficiaryFullName("Alfie Solomons");
        transaction1.setBeneficiaryAge(33);
        transaction1.setIssueId(1);
        transaction1.setIssueSolved(false);
        transaction1.setIssueMessage("Looks like money laundering");

        Transaction transaction2 = new Transaction();
        transaction2.setMtn(1284564);
        transaction2.setAmount(150.2);
        transaction2.setSenderFullName("unknown");
        transaction2.setSenderAge(22);
        transaction2.setBeneficiaryFullName("Arthur Shelby");
        transaction2.setBeneficiaryAge(60);
        transaction2.setIssueId(2);
        transaction2.setIssueSolved(false);
        transaction2.setIssueMessage("Never gonna give you up");

        Transaction transaction3 = new Transaction();
        transaction3.setMtn(1284564);
        transaction3.setAmount(150.2);
        transaction3.setSenderFullName("Tom Shelby");
        transaction3.setSenderAge(22);
        transaction3.setBeneficiaryFullName("Arthur Shelby");
        transaction3.setBeneficiaryAge(60);
        transaction3.setIssueId(3);
        transaction3.setIssueSolved(false);
        transaction3.setIssueMessage("Looks like money laundering");

        Transaction transaction4 = new Transaction();
        transaction4.setMtn(1284564);
        transaction4.setAmount(0.0);
        transaction4.setSenderFullName("xyz");
        transaction4.setSenderAge(22);
        transaction4.setBeneficiaryFullName("abc");
        transaction4.setBeneficiaryAge(60);
        transaction4.setIssueId(null);
        transaction4.setIssueSolved(true);
        transaction4.setIssueMessage("test transaction");

        return List.of(transaction1, transaction2, transaction3, transaction4);
    }
}
