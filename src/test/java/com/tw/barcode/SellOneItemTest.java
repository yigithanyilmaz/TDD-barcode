package com.tw.barcode;


import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;


public class SellOneItemTest {
    @Test
    public void ItemFound() throws Exception {
        final Display display = new Display();
        final Sale sale = new Sale(display, new HashMap<String, String>() {{
            put("123", "7,45");
            put("456", "45,78");
        }});

        sale.onBarcode("123");
        assertEquals("7,45", display.getText());


    }

    @Test
    public void secondItemFound() throws Exception {
        final Display display = new Display();
        final Sale sale = new Sale(display, new HashMap<String, String>() {{
            put("123", "7,45");
            put("456", "45,78");
        }});

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

        final Display display = new Display();
        final Sale sale = new Sale(display, new HashMap<String, String>() {{
            put("123", "7,45");
            put("456", "45,78");
        }});

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
                display.setText("Scanning Error!");
                return;

            }

            if (pricesByBarcode.containsKey(barcode)) {
                display.setText(pricesByBarcode.get(barcode));
            } else{
                display.setText("PRODUCT NOT FOUND: " + barcode);}


        }
    }
}
