package com.solirius.vending;

import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class ProductVendingMachineTest {
    Product coke = new Product("Coke", 27);
    Product pepsi = new Product("Pepsi", 40);
    Product drPepper = new Product("Dr Pepper", 45);

    @Test
    public void buyCoke() {
        Map<Product, Integer> stock = new HashMap<>();
        stock.put(coke, 3);

        ProductVendingMachine machine = new ProductVendingMachine();
        machine.addStock(stock);

        PurchaseTransaction transaction = machine.selectProduct(coke);

        assertFalse(transaction.addCoin(Coin.Twenty));
        assertTrue(transaction.addCoin(Coin.Ten));

        CompletedTransaction result = machine.completeTransaction(transaction);

        List<Coin> coins = result.getReturnedCoins();

        assertEquals(3, coins.size());
        assertEquals(1, coins.stream().filter(c -> c == Coin.One).count());

        int totalChangeValue = coins.stream().map(c -> c.value).reduce((a, b) -> a + b).get();

        assertEquals(3, totalChangeValue);
        assertEquals(coke, result.getProduct());
    }

    @Test
    public void returnOptimalNumberOfCoins() {
        Map<Product, Integer> stock = new HashMap<>();
        stock.put(coke, 3);

        ProductVendingMachine machine = new ProductVendingMachine();
        machine.addStock(stock);

        PurchaseTransaction transaction = machine.selectProduct(coke);

        assertTrue(transaction.addCoin(Coin.Fifty));

        CompletedTransaction result = machine.completeTransaction(transaction);

        List<Coin> coins = result.getReturnedCoins();

        assertEquals(4, coins.size());
        assertEquals(1, coins.stream().filter(c -> c == Coin.Twenty).count());
        assertEquals(3, coins.stream().filter(c -> c == Coin.One).count());

        int totalChangeValue = coins.stream().map(c -> c.value).reduce((a, b) -> a + b).get();

        assertEquals(3, totalChangeValue);
        assertEquals(coke, result.getProduct());
    }

    @Test
    public void returnsOverPayment() {
        Map<Product, Integer> stock = new HashMap<>();
        stock.put(pepsi, 3);

        ProductVendingMachine machine = new ProductVendingMachine();
        machine.addStock(stock);

        PurchaseTransaction transaction = machine.selectProduct(pepsi);

        assertTrue(transaction.addCoin(Coin.Fifty));
        assertTrue(transaction.addCoin(Coin.Fifty));

        CompletedTransaction result = machine.completeTransaction(transaction);

        List<Coin> coins = result.getReturnedCoins();

        assertEquals(4, coins.size());
        assertEquals(3, coins.stream().filter(c -> c == Coin.Twenty).count());
        assertEquals(1, coins.stream().filter(c -> c == Coin.Five).count());

        int totalChangeValue = coins.stream().map(c -> c.value).reduce((a, b) -> a + b).get();

        assertEquals(65, totalChangeValue);
        assertEquals(pepsi, result.getProduct());

    }

    @Test
    public void tryToCompleteWithInsufficientFunds() {
        Map<Product, Integer> stock = new HashMap<>();
        stock.put(drPepper, 3);

        ProductVendingMachine machine = new ProductVendingMachine();
        machine.addStock(stock);

        PurchaseTransaction transaction = machine.selectProduct(drPepper);

        assertFalse(transaction.addCoin(Coin.Ten));
        assertFalse(transaction.addCoin(Coin.Twenty));
        assertFalse(transaction.addCoin(Coin.Five));
        assertFalse(transaction.addCoin(Coin.One));
        assertFalse(transaction.addCoin(Coin.One));

        CompletedTransaction result = machine.completeTransaction(transaction);

        List<Coin> coins = result.getReturnedCoins();

        assertEquals(5, coins.size());
        assertEquals(1, coins.stream().filter(c -> c == Coin.Ten).count());
        assertEquals(1, coins.stream().filter(c -> c == Coin.Twenty).count());
        assertEquals(1, coins.stream().filter(c -> c == Coin.Five).count());
        assertEquals(2, coins.stream().filter(c -> c == Coin.One).count());

        int totalChangeValue = coins.stream().map(c -> c.value).reduce((a, b) -> a + b).get();

        assertEquals(37, totalChangeValue);
        assertNull(result.getProduct());
    }

    @Test(expected = SoldOutException.class)
    public void buyCokeUntilSoldOut() {
        Map<Product, Integer> stock = new HashMap<>();
        stock.put(coke, 1);

        ProductVendingMachine machine = new ProductVendingMachine();
        machine.addStock(stock);

        PurchaseTransaction transaction = machine.selectProduct(coke);

        assertFalse(transaction.addCoin(Coin.Twenty));
        assertTrue(transaction.addCoin(Coin.Ten));

        machine.completeTransaction(transaction);

        machine.selectProduct(coke);
    }

    @Test(expected = SoldOutException.class)
    public void buyNonExistentProduct() {
        Map<Product, Integer> stock = new HashMap<>();
        stock.put(coke, 1);

        ProductVendingMachine machine = new ProductVendingMachine();
        machine.addStock(stock);

        machine.selectProduct(pepsi);
    }

}