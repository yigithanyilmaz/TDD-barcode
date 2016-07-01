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
        sale = new Sale(display, new HashMap<String, String>() {{
            put("123", "7,45");
            put("456", "45,78");
        }});
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
        final Sale sale = new Sale(display, Collections.<String, String> emptyMap());

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

        public void setText(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }
    }

    private class Sale {
        private Display display;
        private Map<String, String> pricesByBarcode;

        public Sale(Display display, Map<String, String> map) {
            this.display = display;
            this.pricesByBarcode = map;
        }

        public void onBarcode(String barcode) {
            if ("".equals(barcode)) {
                displayEmptyBarcodeMsg();
                return;

            }

            if (pricesByBarcode.containsKey(barcode)) {
                getBarcodeThenDisplayPrice(barcode);
            } else{

                displayProductNotFoundMsg(barcode);
            }


        }

        private void displayProductNotFoundMsg(String barcode) {
            display.setText("PRODUCT NOT FOUND: " + barcode);
        }

        private void getBarcodeThenDisplayPrice(String barcode) {

            final String priceAsText = findPrice(barcode);
            displayPrice(priceAsText);
        }

        private void displayPrice(String priceAsText) {
            display.setText(priceAsText);
        }

        private String findPrice(String barcode) {
            return pricesByBarcode.get(barcode);
        }

        private void displayEmptyBarcodeMsg() {
            display.setText("Scanning Error!");
        }
    }
}
