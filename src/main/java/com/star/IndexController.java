package com.star;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 刘乾坤
 * @Date 2021/8/19
 * @Description
 */
@Controller
public class IndexController {
    @RequestMapping("/")
    public String welcome(){
        return "login.html";
    }
}
