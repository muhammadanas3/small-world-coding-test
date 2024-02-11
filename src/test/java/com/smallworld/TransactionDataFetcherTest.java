package com.smallworld;

import com.smallworld.service.TransactionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
class TransactionDataFetcherTest {
    @InjectMocks
    private TransactionDataFetcher transactionDataFetcher;

    @Mock
    TransactionService transactionService;

    @Test
    void testGetTotalTransactionAmount_WhenTransactionExist() {
        Mockito.when(transactionService.getAllTransactions()).thenReturn(MockTransactionFactory.getAllTransactions(false));
        double amount = transactionDataFetcher.getTotalTransactionAmount();
        Assertions.assertEquals(100299.4, amount);
    }

    @Test
    void testGetTotalTransactionAmount_WhenTransactionDoNotExist() {
        Mockito.when(transactionService.getAllTransactions()).thenReturn(MockTransactionFactory.getAllTransactions(true));
        double amount = transactionDataFetcher.getTotalTransactionAmount();
        Assertions.assertEquals(0.0, amount);
    }

    @Test
    void testGetTotalTransactionAmountSentBy_WhenTransactionExist() {
        Mockito.when(transactionService.getAllTransactions()).thenReturn(MockTransactionFactory.getAllTransactions(false));
        double amount = transactionDataFetcher.getTotalTransactionAmountSentBy("Tom Shelby");
        Assertions.assertEquals(100149.2, amount);
    }

    @Test
    void testGetTotalTransactionAmountSentBy_WhenTransactionDoesNotExist() {
        Mockito.when(transactionService.getAllTransactions()).thenReturn(MockTransactionFactory.getAllTransactions(true));
        double amount = transactionDataFetcher.getTotalTransactionAmountSentBy("Tom Shelby");
        Assertions.assertEquals(0.0, amount);
    }

    @Test
    void testGetTotalTransactionAmountSentBy_WhenTransactionExistAndSenderDoesNotExist() {
        Mockito.when(transactionService.getAllTransactions()).thenReturn(MockTransactionFactory.getAllTransactions(false));
        double amount = transactionDataFetcher.getTotalTransactionAmountSentBy("no sender");
        Assertions.assertEquals(0.0, amount);
    }

    @Test
    void testGetMaxTransactionAmount_WhenTransactionExist() {
        Mockito.when(transactionService.getAllTransactions()).thenReturn(MockTransactionFactory.getAllTransactions(false));
        double amount = transactionDataFetcher.getMaxTransactionAmount();
        Assertions.assertEquals(99999.0, amount);
    }

    @Test
    void testGetMaxTransactionAmount_WhenTransactionDoesNotExist() {
        Mockito.when(transactionService.getAllTransactions()).thenReturn(MockTransactionFactory.getAllTransactions(true));
        double amount = transactionDataFetcher.getMaxTransactionAmount();
        Assertions.assertEquals(0.0, amount);
    }

    @Test
    void testCountUniqueClients_WhenTransactionExist() {
        Mockito.when(transactionService.getAllTransactions()).thenReturn(MockTransactionFactory.getAllTransactions(false));
        double count = transactionDataFetcher.countUniqueClients();
        Assertions.assertEquals(3, count);
    }

    @Test
    void testCountUniqueClients_WhenTransactionDoesNotExist() {
        Mockito.when(transactionService.getAllTransactions()).thenReturn(MockTransactionFactory.getAllTransactions(true));
        double count = transactionDataFetcher.countUniqueClients();
        Assertions.assertEquals(0, count);
    }

    @Test
    void testHasOpenComplianceIssues_WhenTransactionExist() {
        Mockito.when(transactionService.getAllTransactions()).thenReturn(MockTransactionFactory.getAllTransactions(false));
        boolean hasIssues = transactionDataFetcher.hasOpenComplianceIssues("unknown");
        Assertions.assertEquals(Boolean.TRUE, hasIssues);
    }

    @Test
    void testHasOpenComplianceIssues_WhenTransactionDoesNotExist() {
        Mockito.when(transactionService.getAllTransactions()).thenReturn(MockTransactionFactory.getAllTransactions(true));
        boolean hasIssues = transactionDataFetcher.hasOpenComplianceIssues("unknown");
        Assertions.assertEquals(Boolean.FALSE, hasIssues);
    }

    @Test
    void testHasOpenComplianceIssues_WhenTransactionExistAndClientNotExist() {
        Mockito.when(transactionService.getAllTransactions()).thenReturn(MockTransactionFactory.getAllTransactions(false));
        boolean hasIssues = transactionDataFetcher.hasOpenComplianceIssues("new client");
        Assertions.assertEquals(Boolean.FALSE, hasIssues);
    }

    @Test
    void testGetTransactionsByBeneficiaryName_WhenTransactionDoesNotExist() {
        Mockito.when(transactionService.getAllTransactions()).thenReturn(MockTransactionFactory.getAllTransactions(true));
        Map<String, Object> actualMap = transactionDataFetcher.getTransactionsByBeneficiaryName();
        Map<String, Object> expectedMap = new HashMap<>();
        Assertions.assertEquals(expectedMap, actualMap);
    }
}