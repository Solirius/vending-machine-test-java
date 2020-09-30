package com.solirius.vending;

public enum Coin {
    One(1),
    Five(5),
    Ten(10),
    Twenty(20),
    Fifty(50);

    public final int value;

    private Coin(int value) {
        this.value = value;
    }
}
