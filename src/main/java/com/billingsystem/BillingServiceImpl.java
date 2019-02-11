package com.billingsystem;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class BillingServiceImpl implements BillingService{

    private static final String PROPERTIES = "price.properties" ;
    private static final String BLUE_MAX = "bluePen_moreQuantity_price" ;
    private static final String RED_MAX = "redPen_moreQuantity_price" ;
    private static final String BLUE_MIN = "bluePen_lessQuantity_price" ;
    private static final String RED_MIN = "redPen_lessQuantity_price" ;

    private static double discountPercent = 15;
    private static String byCommma = ",";
    private static String newLine = "\r\n";

    /*
    * The initial method that triggers the logic to calculate billing and then generating file
    *
    * @param String input filepath containing the order
    *
    **/
    public void startBilling(String filePath) throws IOException, FileNotFoundException {
        Writer writer = null;
        try {
            ArrayList<Item> orderFromFile = getOrdersFromFile(filePath);
            calculateBil(orderFromFile);
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(getOutputFileName(filePath)), "utf-8"));
            writer.write("Item name,Item quantity,Tarrif,Subtotal" + newLine);
            long start = System.currentTimeMillis();
            for (Item item : orderFromFile) {
                writer.write(getResultToAppend(item));
            }

            writer.write(Double.toString(checkDiscount(ItemTotal.totalPriceOfTheOrder)));
            long stop = System.currentTimeMillis();
            System.out.println("Duration in ms: " + (stop - start));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            writer.close();
        }
    }

    /*
    * To construct output filename by locating the location of input file
    *
    * @param String input filepath containing the order
    * @return String containing the name of outputfile.
    *
    **/
    private static String getOutputFileName(String filePath) {
        StringBuilder resultFile = new StringBuilder();
        //String outputFilePath = BillingServiceImpl.class.getClassLoader().getResource(filePath).getFile();
        //resultFile.append(outputFilePath.substring(0, outputFilePath.lastIndexOf("."))).append("_Result").append(outputFilePath.substring(outputFilePath.lastIndexOf("."), outputFilePath.length()));
        resultFile.append(filePath.substring(0, filePath.lastIndexOf("."))).append("_Result").append(filePath.substring(filePath.lastIndexOf("."), filePath.length()));
        return resultFile.toString();
    }

   /*
   * Constructs the data by appending the details of an item as per the given requirement
   *
   * @param item the item details
   * @return String containing details of each item in the order.
   *
   **/
    private static String getResultToAppend(Item item) {
        StringBuilder result = new StringBuilder();
        result.append(item.getItemName()).append(byCommma).append(item.getQuantity()).append(byCommma).append(item.getTarrif()).append(byCommma).append(item.getTotalPrice()).append(newLine);
        return result.toString();
    }

   /*
   * To calculate the cost of each item in the order and the total cost of all items in the order
   *
   * @param list of items..
   * @return List of items after applying drools rules.
   *
   **/
    private static List<Item> calculateBil(List<Item> items) {
        try {
            KieSession kSession = getKieSession();
            ItemTotal itemTotal = new ItemTotal(0.0);
            items.stream().forEach(item -> {
                kSession.insert(item);
                kSession.insert(itemTotal);
            });
            kSession.fireAllRules();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return items;
    }

    /*
    * Makes drools working memory ready and also assigns values to the global constants
    *
    * @return KieSession drools session.
    *
    * **/
    private static KieSession getKieSession() {
        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer();
        KieSession kSession = kContainer.newKieSession("ksession-rules");
        getGlobalValues(kSession);

        return kSession;
    }

   /*
   *Reads the prices from properties file and adds to the global drools variables
   *
   * @return KieSession drools session.
   *
   * **/
    private static void getGlobalValues(KieSession kSession) {
        Properties properties = new Properties();
        try {
            properties.load(getResourceAsStream(PROPERTIES));
            for (String key : properties.stringPropertyNames()) {
                if (key.equals(BLUE_MAX)) {
                    kSession.setGlobal(BLUE_MAX, Double.valueOf(properties.getProperty(BLUE_MAX)));
                } else if (key.equals(RED_MAX)) {
                    kSession.setGlobal(RED_MAX, Double.valueOf(properties.getProperty(RED_MAX)));
                } else if (key.equals(BLUE_MIN)) {
                    kSession.setGlobal(BLUE_MIN, Double.valueOf(properties.getProperty(BLUE_MIN)));
                } else if (key.equals(RED_MIN)) {
                    kSession.setGlobal(RED_MIN, Double.valueOf(properties.getProperty(RED_MIN)));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    * Method that reads contents of file and adds it to Item  class
    *
    * @param filePath path to the file.
    * @return ArrayList the list ofItems.
    *
    **/
    public static ArrayList<Item> getOrdersFromFile(String filePath) {
        ArrayList<Item> result = new ArrayList<Item>();
        BufferedReader br = null;
        String line = "";
        try {
            br = new BufferedReader(new InputStreamReader(getResourceAsStream(filePath), Charset.forName("utf-8")));

            while ((line = br.readLine()) != null) {
                String[] lineValues = line.split(byCommma);
                if (lineValues != null) {
                    Item item = new Item(lineValues[0].trim(), Integer.parseInt(lineValues[1].trim()));
                    result.add(item);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }

    private static InputStream getResourceAsStream(String filePath) {
        return BillingServiceImpl.class.getClassLoader().getResourceAsStream(filePath);
    }

    private static double checkDiscount(double totalPriceOfTheOrder) {
        double discountedPrice = totalPriceOfTheOrder;
      /**  if(totalPriceOfTheOrder > 100)
        {
            double discount = discountPercent/100;
            discountedPrice =((1-discount)*totalPriceOfTheOrder);
        }**/
        return discountedPrice;
    }

}

