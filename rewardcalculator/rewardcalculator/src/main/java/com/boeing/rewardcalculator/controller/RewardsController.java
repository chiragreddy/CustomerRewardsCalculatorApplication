package com.boeing.rewardcalculator.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.boeing.rewardcalculator.model.Transaction;
import com.boeing.rewardcalculator.service.RewardsService;

@RestController
public class RewardsController {

	@Autowired
    private RewardsService rewardsService;

    @Value("${transaction.file.path}")
    private String filePath;

    /**
     * Creates rest endpoint for getting Total Rewards for a specific Customer over a 3 month period - /rewards/customerName
     * @param customerName
     * @return map containing customer names and their reward points
     * @throws IOException
     */
    @GetMapping("/rewards/{customerName}")
    public int getTotalRewardsForCustomer(@PathVariable("customerName") String customerName) throws IOException {
        List<Transaction> transactionHistory = readTransactionsFromFile(filePath, customerName);
        int totalPoints = 0;
        for (Transaction transaction : transactionHistory) {
            totalPoints += rewardsService.calculatePoints(transaction.getAmount());
        }
        return totalPoints;
    }
    
    /**
     * Creates rest endpoint for getting Rewards for a specific Customer in a specific month - /rewards/customerName/month
     * @param customerName
     * @param month
     * @return map containing customer names and their reward points
     * @throws IOException 
     */
    @GetMapping("/rewards/{customerName}/{month}")
    public int getRewardsForCustomerByMonth(@PathVariable("customerName") String customerName, @PathVariable("month") String month) throws IOException{
        
        List<Transaction> transactionHistory = readTransactionsFromFile(filePath, customerName);
        int totalPoints = 0;
        for (Transaction transaction : transactionHistory) {
            if(transaction.getMonth().equalsIgnoreCase(month)) {
                totalPoints += rewardsService.calculatePoints(transaction.getAmount());
            }
        }
        return totalPoints;
    }
    
    /**
     * Read Transaction Records from the dataset.txt file
     * @param filePath
     * @param customerName
     * @return list of transaction records
     * @throws IOException 
     */
    private List<Transaction> readTransactionsFromFile(String filePath, String customerName) throws IOException {
        List<Transaction> transactionHistory = new ArrayList<>();
        File file = new File(filePath);
        BufferedReader br = new BufferedReader(new FileReader(file.getAbsolutePath()));
        String line;
        while ((line = br.readLine()) != null) {
            String[] transactionData = line.split(",");
            String name = transactionData[0];
            String month = transactionData[1];
            double amount = Double.parseDouble(transactionData[2]);
            if(name.equalsIgnoreCase(customerName)) {
                transactionHistory.add(new Transaction(name, month, amount));
            }
        }
        return transactionHistory;
    }
    
    /**
     * Creates rest endpoint for getting Total Rewards for all Customers in a 3 month period - /rewards
     * @return map containing customer names and their reward points
     * @throws IOException
     */
    @GetMapping("/rewards")
    public Map<String, Integer> getRewardsForAllCustomers() throws IOException {
    	
        Map<String, Integer> rewards = new HashMap<>();
        File file = new File(filePath);
        BufferedReader br = new BufferedReader(new FileReader(file.getAbsolutePath()));
        String line;
        while ((line = br.readLine()) != null) {
            String[] transactionData = line.split(",");
            String name = transactionData[0];
            double amount = Double.parseDouble(transactionData[2]);
            if(rewards.containsKey(name)) {
                int totalPoints = rewards.get(name);
                totalPoints += rewardsService.calculatePoints(amount);
                rewards.put(name, totalPoints);
            } else {
                rewards.put(name, rewardsService.calculatePoints(amount));
            }
        }
        return rewards;
    }
    
    /**
     * Creates rest endpoint for getting transaction history of all customers from the dataset text file - /transactions
     * @return list of transaction records all customers which includes customer name, month and transaction amount
     * @throws IOException
     */
    @GetMapping("/transactions")
    public List<Transaction> getAllTransactions() throws IOException {
        List<Transaction> transactions = new ArrayList<>();
        File file = new File(filePath);
        BufferedReader br = new BufferedReader(new FileReader(file.getAbsolutePath()));
        String line;
        while ((line = br.readLine()) != null) {
            String[] transactionData = line.split(",");
            String name = transactionData[0];
            String month = transactionData[1];
            double amount = Double.parseDouble(transactionData[2]);
            transactions.add(new Transaction(name, month, amount));
        }
        return transactions;
    }
	
}