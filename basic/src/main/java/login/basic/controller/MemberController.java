package login.basic.controller;

import login.basic.dto.LoginFormDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MemberController {

    @GetMapping("/login")
    public String login(Model model){

        model.addAttribute("loginForm",new LoginFormDto());
        return "login";
    }

    @GetMapping("user")
    @ResponseBody
    public String user(){
        return "user";
    }

    @GetMapping("admin")
    @ResponseBody
    public String admin(){
        return "admin";
    }

    @GetMapping("join")
    @ResponseBody
    public String join(){
        return "join";
    }
}
