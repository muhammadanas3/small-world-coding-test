package com.smallworld.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Transaction implements Comparable<Transaction> {

    private Integer mtn;
    private Double amount;
    private String senderFullName;
    private Integer senderAge;
    private String beneficiaryFullName;
    private Integer beneficiaryAge;
    private Integer issueId;
    private Boolean issueSolved;
    private String issueMessage;

    @Override
    public int compareTo(Transaction other) {
        return Double.compare(this.amount, other.amount);
    }
}
