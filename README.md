# Vending Machine Test

Design a Vending Machine which:

 - Accepts coin values 1, 5, 10, 20, 50
 - Allows the user to select products Coke(27), Pepsi(35), Dr Pepper(45)
 - Allows the user to take a refund by canceling the request.
 - Return selected product and remaining change if any
 - Returns change in the lowest number of coins possible
 
Complete the implementation of the `ProductVendingMachine` so that it passes the tests included in the codebase. Feel free to add more tests if desired.
 
```java
public class ProductVendingMachine {

    public void addStock(Map<Product, Integer> stock) {

    }

    public PurchaseTransaction selectProduct(Product product) {
        return null;
    }

    public CompletedTransaction completeTransaction(PurchaseTransaction transaction) {
        return null;
    }

}
```

# Running the application

```
./gradlew test
```

# Topics for further discussion

 - What problems does concurrency add to this system?
 - What problems would need to be solved in a distributed system?
 