package com.jgw.supercodeplatform;

import java.text.DecimalFormat;

public class MainTest {

    public static void main(String[] args) {
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        System.out.println("------>"+decimalFormat.format( 0.5));
    }

}
