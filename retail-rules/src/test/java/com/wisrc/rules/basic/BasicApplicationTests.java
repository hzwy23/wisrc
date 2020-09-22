package com.wisrc.rules.basic;

import com.wisrc.rules.webapp.utils.Toolbox;
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
