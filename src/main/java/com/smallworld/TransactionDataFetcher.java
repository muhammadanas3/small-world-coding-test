package com.smallworld;

import com.smallworld.model.Transaction;
import com.smallworld.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionDataFetcher {

    private final TransactionService transactionService;

    /**
     * Returns the sum of the amounts of all transactions
     */
    public double getTotalTransactionAmount() {
        log.info("getTotalTransactionAmount()");

        double amount = 0.0;
        List<Transaction> transactionList = transactionService.getAllTransactions();
        for (Transaction transaction : transactionList) {
            amount += Objects.isNull(transaction.getAmount()) ? 0.0 : transaction.getAmount();
        }
        return amount;
    }

    /**
     * Returns the sum of the amounts of all transactions sent by the specified client
     */
    public double getTotalTransactionAmountSentBy(String senderFullName) {
        log.info("getTotalTransactionAmountSentBy()");

        double amount = 0.0;
        if (StringUtils.isEmpty(senderFullName)) {
            return amount;
        }
        List<Transaction> transactionList = transactionService.getAllTransactions();
        for (Transaction transaction : transactionList) {
            if (senderFullName.equals(transaction.getSenderFullName())) {
                amount += Objects.isNull(transaction.getAmount()) ? 0.0 : transaction.getAmount();
            }
        }
        return amount;
    }

    /**
     * Returns the highest transaction amount
     */
    public double getMaxTransactionAmount() {
        log.info("getMaxTransactionAmount()");

        List<Transaction> transactionList = transactionService.getAllTransactions();
        if (CollectionUtils.isEmpty(transactionList)) {
            return 0.0;
        }
        double maxAmount = Double.MIN_VALUE;
        for (Transaction transaction : transactionList) {
            double amount = transaction.getAmount();
            if (amount > maxAmount) {
                maxAmount = amount;
            }
        }
        return maxAmount;
    }

    /**
     * Counts the number of unique clients that sent or received a transaction
     */
    public long countUniqueClients() {
        log.info("countUniqueClients()");

        List<Transaction> transactionList = transactionService.getAllTransactions();
        List<String> nameList = new ArrayList<>();
        for (Transaction transaction : transactionList) {
            if (!nameList.contains(transaction.getSenderFullName())) {
                nameList.add(transaction.getSenderFullName());
            }
        }
        return nameList.size();
    }

    /**
     * Returns whether a client (sender or beneficiary) has at least one transaction with a compliance
     * issue that has not been solved
     */
    public boolean hasOpenComplianceIssues(String clientFullName) {
        log.info("hasOpenComplianceIssues()");

        if (StringUtils.isEmpty(clientFullName)) {
            return Boolean.FALSE;
        }
        List<Transaction> transactionList = transactionService.getAllTransactions();
        for (Transaction transaction : transactionList) {
            if ((clientFullName.equals(transaction.getSenderFullName()) ||
                    clientFullName.equals(transaction.getBeneficiaryFullName())) &&
                    Boolean.FALSE.equals(transaction.getIssueSolved())) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    /**
     * Returns all transactions indexed by beneficiary name
     */
    public Map<String, Object> getTransactionsByBeneficiaryName() {
        log.info("getTransactionsByBeneficiaryName()");

        List<Transaction> transactionList = transactionService.getAllTransactions();
        if (CollectionUtils.isEmpty(transactionList)) {
            return Collections.emptyMap();
        }
        Map<String, List<Transaction>> transactionMap = new HashMap<>();
        for (Transaction transaction : transactionList) {
            if (transactionMap.containsKey(transaction.getBeneficiaryFullName())) {
                transactionMap.get(transaction.getBeneficiaryFullName()).add(transaction);
            } else {
                List<Transaction> transactions = new ArrayList<>();
                transactions.add(transaction);
                transactionMap.put(transaction.getBeneficiaryFullName(), transactions);
            }
        }
        return new HashMap<>(transactionMap);
    }

    /**
     * Returns the identifiers of all open compliance issues
     */
    public Set<Integer> getUnsolvedIssueIds() {
        log.info("getUnsolvedIssueIds()");

        List<Transaction> transactionList = transactionService.getAllTransactions();
        Set<Integer> identifierIds = new HashSet<>();
        for (Transaction transaction : transactionList) {
            if (Boolean.FALSE.equals(transaction.getIssueSolved())) {
                identifierIds.add(transaction.getIssueId());
            }
        }
        return identifierIds;
    }

    /**
     * Returns a list of all solved issue messages
     */
    public List<String> getAllSolvedIssueMessages() {
        log.info("getAllSolvedIssueMessages()");

        List<Transaction> transactionList = transactionService.getAllTransactions();
        List<String> messages = new ArrayList<>();
        for (Transaction transaction : transactionList) {
            if (Boolean.TRUE.equals(transaction.getIssueSolved())) {
                messages.add(transaction.getIssueMessage());
            }
        }
        return messages;
    }

    /**
     * Returns the 3 transactions with highest amount sorted by amount descending
     */
    public List<Object> getTop3TransactionsByAmount() {
        log.info("getAllSolvedIssueMessages()");

        List<Transaction> transactionList = transactionService.getAllTransactions();
        transactionList.sort(Collections.reverseOrder(Transaction::compareTo));
        List<Transaction> topTransactions = transactionList.subList(0, Math.min(transactionList.size(), 3));
        return Collections.singletonList(topTransactions);
    }

    /**
     * Returns the sender with the most total sent amount
     */
    public Optional<Object> getTopSender() {
        log.info("getTopSender()");

        List<Transaction> transactionList = transactionService.getAllTransactions();
        if (CollectionUtils.isEmpty(transactionList)) {
            return Optional.empty();
        }
        NavigableMap<String, Double> amountMap = new TreeMap<>();
        for (Transaction transaction : transactionList) {
            if (amountMap.containsKey(transaction.getSenderFullName())) {
                amountMap.put(transaction.getSenderFullName(), amountMap.get(transaction.getSenderFullName()) + transaction.getAmount());
            } else {
                amountMap.put(transaction.getSenderFullName(), transaction.getAmount());
            }
        }
        return Optional.ofNullable(amountMap.lastEntry());
    }

}
