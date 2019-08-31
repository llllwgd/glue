package com.llllwgd.glue.example;

import com.llllwgd.glue.core.GlueFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExampleApplicationTests {

    @Test
    public void contextLoads() throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("telephone", "123");
        Object glue = GlueFactory.glue("demo_project.DemoGlueHandler02", null);
        System.out.println(glue);

    }

}
