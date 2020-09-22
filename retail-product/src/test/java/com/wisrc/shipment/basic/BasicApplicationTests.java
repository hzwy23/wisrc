package com.wisrc.shipment.basic;

import com.wisrc.product.webapp.utils.Result;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootConfiguration
@SpringBootTest
public class BasicApplicationTests {

    @Test
    public void contextLoads() {
        System.out.println(Result.success());
    }

}
