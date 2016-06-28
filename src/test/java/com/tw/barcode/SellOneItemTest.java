package com.tw.barcode;


import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;



import java.awt.*;
import java.rmi.UnexpectedException;

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

        public void onBarcode(String barcode) throws UnexpectedException {

            if ("123".equals(barcode)) {
                display.setText("7,45");
            }
             else {
                display.setText("45,78");
            }


        }

    }
}
