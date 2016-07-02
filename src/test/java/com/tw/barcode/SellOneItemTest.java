package com.tw.barcode;


import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;


public class SellOneItemTest {

    private Display display;
    private Sale sale;

    @Before
    public void setUp() throws Exception {
        display = new Display();
        sale = new Sale(display, new Catalog(new HashMap<String, String>() {{
            put("123", "7,45");
            put("456", "45,78");
        }}));
    }

    @Test
    public void ItemFound() throws Exception {
        sale.onBarcode("123");
        assertEquals("7,45", display.getText());


    }

    @Test
    public void secondItemFound() throws Exception {
        sale.onBarcode("456");
        assertEquals("45,78", display.getText());

    }

    @Test
    public void emptyBarcodeScan() throws Exception {
        final Display display = new Display();
        final Sale sale = new Sale(display, new Catalog(Collections.<String, String>emptyMap()));

        sale.onBarcode("");
        assertEquals("Scanning Error!", display.getText());

    }

    @Test
    public void productNotFound() throws Exception {

        sale.onBarcode("9999");
        assertEquals("PRODUCT NOT FOUND: 9999", display.getText());

    }

    private class Display {

        private String text;

        public String getText() {
            return text;
        }

        public void displayEmptyBarcodeMsg() {
            this.text = "Scanning Error!";
        }

        private void displayPrice(String priceAsText) {
            this.text = priceAsText;
        }

        public void displayProductNotFoundMsg(String barcode) {
            this.text = "PRODUCT NOT FOUND: " + barcode;
        }

        public void getBarcodeThenDisplayPrice(String barcode, Sale sale) {

            final String priceAsText = sale.catalog.findPrice(barcode);
            displayPrice(priceAsText);
        }
    }

    private class Sale {
        private Display display;
        private Catalog catalog;

        public Sale(Display display, Catalog catalog) {
            this.display = display;
            this.catalog = catalog;
        }


        public void onBarcode(String barcode) {
            if ("".equals(barcode)) {
                display.displayEmptyBarcodeMsg();
                return;
            }

            final String priceAsText = catalog.findPrice(barcode);
            if (priceAsText==null) {
                display.displayProductNotFoundMsg(barcode);

            } else{
                display.getBarcodeThenDisplayPrice(barcode, this);

            }

        }

    }

    public  class Catalog {
        private final Map<String, String> pricesByBarcode;

        private Catalog(Map<String, String> pricesByBarcode) {
            this.pricesByBarcode = pricesByBarcode;
        }

        public String findPrice(String barcode) {
            return pricesByBarcode.get(barcode);
        }
    }
}
