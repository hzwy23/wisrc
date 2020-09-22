package com.wisrc.shipment.basic.webapp;

import com.wisrc.merchandise.dao.sql.BasicPlatformSQL;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Controller
@EnableFeignClients(
        basePackages = {"com"}
)
public class BasicPlatformSQLTest {

    @Test
    public void platformSqlTest() {
        BasicPlatformSQL abc = new BasicPlatformSQL();
        System.out.println(abc.search("Amazon", "1"));
    }
}
