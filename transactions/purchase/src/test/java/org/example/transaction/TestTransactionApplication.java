package org.example.transaction;

import org.example.purchase.PurchaseApplication;
import org.springframework.boot.SpringApplication;

public class TestTransactionApplication {

    public static void main(String[] args) {
        SpringApplication.from(PurchaseApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
