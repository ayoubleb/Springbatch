package com.docaposte.toubkal.b2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
        B2_Factory b2Factory = new B2_Factory();

        IB2 ph = b2Factory.getControl("PH");
        IB2 cp = b2Factory.getControl("CP");


        ph.createControl();
        cp.createControl();
    }
}