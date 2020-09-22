package com.wisrc.shipment.basic.webapp;


import com.wisrc.merchandise.utils.Crypto;
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
public class CryptoTest {
    @Test
    public void SHA256() {
        System.out.println(Crypto.sha("fsadfewfe", "gdsfewq"));
        System.out.println(Crypto.sha("fdfwqfewqef", "gasewqef"));
        System.out.println(Crypto.sha("wfrsdfqwereqwr", "fewasfew"));
        System.out.println(Crypto.sha("gasefewqef", "feqwerasdfew"));
        System.out.println(Crypto.sha("000000", "0000"));
        System.out.println(Crypto.sha("0000", "000000"));
    }
}
