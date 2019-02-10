Steps followed

The system shall generate an invoice for a basket of items being purchased.
2. The invoice shall list each order in separate invoice lines displaying the following information.
a. Item name
b. Item quantity
c. Tariff
d. Subtotal
3. The invoice shall contain the total price.
4. The system shall support different pricing models defined by human-readable rules.
5. Input to the system will be a CSV file, each line will have the following format:
item_name, quantity
6. Output from the system should be a CSV file containing the invoice:
One invoice line per row: item_name, item_quantity, tariff, subtotal
Final row: total

Technical details
•	Create an interface and expose a method that triggers billing.
•	In the implementation class, this method should read the input file.
•	Set the contents of each line to a model class having the name of the item, quantity, price of each item and the total price of all such items.
•	Once the model class is set with the data(item name, quantity) from the input file, prepare the drools session.
•	Set the global variable in the rules file by reading the standard initial prices from the properties file.
•	Pass the list of model class items to drools.
•	Drools file rules should handle the following
o	Determine the price of each item based on the type of item and the quantity. Refer to Billing System price calculation illustration.
o	Once the price is identified then set the total amount of each item cost.
o	Implement a rule that sums up the total cost of each item.
•	Once the price is calculated generate the details of each item, quantity, price of each item, total price of the items. In the end of file, add the total cost of all the items in the input file.

