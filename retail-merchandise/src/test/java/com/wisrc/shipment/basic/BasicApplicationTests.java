package com.wisrc.shipment.basic;

import com.wisrc.merchandise.dao.MskuInfoEpitaphDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
public class BasicApplicationTests {
    @Autowired
    private MskuInfoEpitaphDao mskuInfoEpitaphDao;

    @Test
    public void main() {
        long startTime = System.nanoTime();   //获取开始时间


        long endTime = System.nanoTime(); //获取结束时间

        System.out.println("程序运行时间： " + (endTime - startTime) + "ns");

        System.out.println("finish");
    }

    @Test
    public void main2() {
        System.out.println("start");


        System.out.println("finish");
    }
}



