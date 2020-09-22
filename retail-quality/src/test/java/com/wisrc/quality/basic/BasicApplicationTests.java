package com.wisrc.quality.basic;

import com.wisrc.quality.webapp.utils.Toolbox;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BasicApplicationTests {

    @Test
    public void contextLoads() {
        System.out.println(Toolbox.randomUUID());
    }

}
