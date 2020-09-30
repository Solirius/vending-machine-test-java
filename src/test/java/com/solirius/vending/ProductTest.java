package com.solirius.vending;

import org.junit.Test;

import static org.junit.Assert.*;

public class ProductTest {

    @Test
    public void testGetName() {
        Product drPepper = new Product("Dr Pepper", 50);

        assertEquals("Dr Pepper", drPepper.name);
    }

    @Test
    public void testGetValue() {
        Product drPepper = new Product("Dr Pepper", 50);

        assertEquals(50, drPepper.value);
    }

}