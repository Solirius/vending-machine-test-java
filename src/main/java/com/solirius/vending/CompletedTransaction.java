package com.solirius.vending;

import java.util.List;

public interface CompletedTransaction {

    List<Coin> getReturnedCoins();
    Product getProduct();
    String getError();

}
