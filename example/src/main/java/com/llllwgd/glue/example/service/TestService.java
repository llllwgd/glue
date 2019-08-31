package com.llllwgd.glue.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: llllwgd
 * Description:
 * Date: 2019-09-01
 * Time: 1:15
 */
@Service
public class TestService {

    @Autowired

    public String test() {
        return "123";
    }

}
