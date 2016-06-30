package com.tw.barcode;


import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static javax.swing.UIManager.put;
import static org.junit.Assert.*;


public class SellOneItemTest {
    @Test
    public void ItemFound() throws Exception {
        final Display display = new Display();
        final Sale sale = new Sale(display);

        sale.onBarcode("123");
        assertEquals("7,45", display.getText());


    }

    @Test
    public void secondItemFound() throws Exception {
        final Display display = new Display();
        final Sale sale = new Sale(display);

        sale.onBarcode("456");
        assertEquals("45,78", display.getText());

    }

    @Test
    public void emptyBarcodeScan() throws Exception {
        final Display display = new Display();
        final Sale sale = new Sale(display);

        sale.onBarcode("");
        assertEquals("Scanning Error!", display.getText());

    }

    @Test
    public void productNotFound() throws Exception {

        final Display display = new Display();
        final Sale sale = new Sale(display);

        sale.onBarcode("9999");
        assertEquals("PRODUCT NOT FOUND: 9999", display.getText());

    }

    private class Display {

        private String text;

        public void setText(String text) {
            this.text = text;
        }

        public String getText(){
            return text;
        }
    }

    private class Sale {
        private Display display;

        public Sale(Display display) {
            this.display = display;
        }

        public void onBarcode(String barcode) {
            if ("".equals(barcode)) {
                display.setText("Scanning Error!");

            } else {
                final Map<String, String> pricesByBarcode = new HashMap<String, String>() {{
                    put("123", "7,45");
                    put("456", "45,78");
                }};

                if ("123".equals(barcode))
                    display.setText(pricesByBarcode.get(barcode));
                else if ("456".equals(barcode))
                    display.setText(pricesByBarcode.get(barcode));
                else
                    display.setText("PRODUCT NOT FOUND: " + barcode);


            }

        }
    }}
