package com.smallworld.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smallworld.model.Transaction;
import com.smallworld.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

@Slf4j
@Service
public class TransactionServiceImpl implements TransactionService {

    @Value("${datasource.json.file_path}")
    private String jsonFilePath;

    private static List<Transaction> transactionList;

    @PostConstruct
    private void postConstruct() throws FileNotFoundException {
        loadTransactionList();
    }

    @Override
    public List<Transaction> getAllTransactions() {
        log.info("getAllTransactions()");
        return transactionList;
    }

    private void loadTransactionList() throws FileNotFoundException {
        log.info("loadTransactionList()");
        ObjectMapper om = new ObjectMapper();
        try {
            File file = new File(jsonFilePath);
            transactionList = om.readValue(file, new TypeReference<>() {
            });
        } catch (Exception e) {
            throw new FileNotFoundException();
        }
    }
}
