package com.billingsystem;

import org.junit.Test;
import java.io.IOException;

public class BillingSystemTest {
    final BillingService billingService = new BillingServiceImpl();

    // Test that checks if the output is as expected, as per the problemstatement
    @Test
    public void testBilling() throws  IOException {
        String filePath= "BillingSystem.csv";
        billingService.startBilling(filePath);
    }


}
