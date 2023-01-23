package com.boeing.rewardcalculator.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.boeing.rewardcalculator.service.RewardsService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {RewardsController.class, RewardsService.class})
@WebMvcTest(RewardsController.class)
/**
 * 
 * Testing the RewardsController class
 *
 */
public class RewardsControllerTest {

	@Autowired
    private MockMvc mockMvc;

    @MockBean
    private RewardsService rewardsService;
	
    /**
     * Test the testGetTotalRewardsForCustomer method of the RewardsController for endpoint /rewards/{customerName}
     * Verifying reward points for customer by invoking the above endpoint
     */
    @Test
    public void testGetTotalRewardsForCustomer() throws Exception {
    	 // Given
        String customerName = "John";
        double purchaseAmount = 120.0;
        int expectedPoints = 90;

        when(rewardsService.calculatePoints(purchaseAmount)).thenReturn(expectedPoints);

        // When
        mockMvc.perform(get("/rewards/{customerName}", customerName)
                .param("purchaseAmount", String.valueOf(purchaseAmount)))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(expectedPoints)));
  
    }
   
    /**
     * Test the testGetRewardsForCustomerByMonth method of the RewardsController for endpoint /rewards/{customerName}/{month}
     * Verifying reward points for customer by month by invoking the above endpoint when the transaction = 100
     */
    @Test
    public void testGetRewardsForCustomerByMonth() throws Exception {
        // Given
        String customerName = "Joe";
        String month = "January";
        int expectedPoints = 50;
        when(rewardsService.calculatePoints(anyDouble())).thenReturn(expectedPoints);

        // When
        mockMvc.perform(get("/rewards/{customerName}/{month}", customerName, month))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(expectedPoints)));
    }
    
    /**
     * Test the getRewardsForAllCustomers method of the RewardsController for endpoint /rewards
     * Verifying reward points for customer by month by invoking the above endpoint when the transaction = 100
     */
    @Test
    public void testGetRewardsForAllCustomers() throws Exception {
        // Map for expected rewards
        Map<String, Double> expectedRewards = new HashMap<>();
        expectedRewards.put("John", 270.0);
        expectedRewards.put("Joe", 150.0);
        expectedRewards.put("Jane", 190.0);
        
        when(rewardsService.calculatePoints(anyDouble())).thenAnswer(invocation -> {
            double amount = (double) invocation.getArguments()[0];
            return (amount > 100) ? 2 * (int) (amount - 100) + 50 : (int) (amount - 50);
        });
        
        String filePath = "src/main/resources/dataset.txt";
        File file = new File(filePath);
        InputStream inputStream = new FileInputStream(file.getAbsolutePath());
        byte[] byteArray = IOUtils.toByteArray(inputStream);

        // When
        MvcResult result = mockMvc.perform(get("/rewards").content(byteArray))
                .andExpect(status().isOk())
                .andReturn();
        // Map to store all reward points of cutomers obtained by invoking endpoint /rewards
        Map<String, Double> rewards = new ObjectMapper().readValue(result.getResponse().getContentAsString(), new TypeReference<Map<String, Double>>() {});

        // Then
        assertThat(rewards).isEqualTo(expectedRewards);
    }
	
	
}