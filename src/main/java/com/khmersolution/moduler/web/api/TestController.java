package com.khmersolution.moduler.web.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Vannaravuth Yo
 * Date : 8/29/17, 9:36 PM
 * Email : ravuthz@gmail.com
 */

@RestController
public class TestController {

    @RequestMapping("/test")
    public String getTest() {
        return "test";
    }

}
