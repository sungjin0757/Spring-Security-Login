package login.basic.controller;

import login.basic.domain.Member;
import login.basic.dto.LoginFormDto;
import login.basic.dto.SignupFormDto;
import login.basic.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/loginForm")
    public String login(Model model){

        model.addAttribute("loginFormDto",new LoginFormDto());

        return "login";
    }

    @GetMapping("/join")
    public String joinForm(Model model){

        model.addAttribute("signupFormDto",new SignupFormDto());
        return "signup";
    }

    @PostMapping("/join")
    public String join(@ModelAttribute SignupFormDto form){

        Member member=new Member(form.getEmail(),form.getPassword(),form.getName());
        member.updateRole("ROLE_USER");
        memberService.join(member);

        return "redirect:/login";
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


}
