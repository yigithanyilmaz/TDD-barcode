package com.tw.barcode;


import org.junit.Test;
import static org.junit.Assert.*;



import java.awt.*;

public class SellOneItemTest {
    @Test
    public void sellingItemFound() throws Exception {
        final Display display = new Display();
        final Sale sale = new Sale();

        sale.onBarcode("123456");
        assertEquals("7,45",display.getText());


    }

    private class Sale {
        public void onBarcode(String barcode){

        }

    }

    private class Display {
        public String getText(){
          return null;
        }
    }
}
