package com.billingsystem;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface BillingService {

    /*
    * The initial method that triggers the logic to calculate billing and then generating file
    *
    * @param String input filepath containing the order
    *
    **/
    public void startBilling(String filePath) throws IOException, FileNotFoundException ;
}

