package login.basic.controller;

import login.basic.domain.Member;
import login.basic.dto.LoginFormDto;
import login.basic.dto.LoginPrincipalDetails;
import login.basic.dto.SignupFormDto;
import login.basic.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/test")
    @ResponseBody
    public Member testV1(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User principal = (User) authentication.getPrincipal();
        Member member = memberService.findByEmail(principal.getUsername());

        return member;
    }

//    @ResponseBody
//    @GetMapping("/test/login")
    public String loginTest(Authentication authentication){
        log.info(authentication.getPrincipal().toString());

        User user=(User)authentication.getPrincipal();
        Member member = memberService.findByEmail(user.getUsername());

        return member.getName();
    }

    @ResponseBody
//    @GetMapping("/test/login")
    public String loginTestV3(@AuthenticationPrincipal UserDetails user){

        Member member = memberService.findByEmail(user.getUsername());

        return member.getName();
    }

//    @GetMapping("/test/oauth")
    @ResponseBody
    public String testOauthLogin(Authentication authentication){
        OAuth2User oAuth2User=(OAuth2User) authentication.getPrincipal();

        log.info(oAuth2User.getAttributes().toString());

        return "oauth";

    }

//    @GetMapping("/test/oauth")
    @ResponseBody
    public String testOauthLoginV2(@AuthenticationPrincipal OAuth2User oAuth2User){
        String email = oAuth2User.getAttribute("email");

        log.info(oAuth2User.getAttributes().toString());
        log.info(email);
        return "oauth";

    }

    @GetMapping("/test/login")
    @ResponseBody
    public String testLoginFinal(@AuthenticationPrincipal LoginPrincipalDetails user){
        String email = user.getUsername();

        log.info(email);
        return "oauth";

    }


    @GetMapping("/loginForm")
    public String login(Model model){

        model.addAttribute("loginFormDto",new LoginFormDto());

        return "login";
    }

    @PostMapping("/loginFail")
    public String loginFail(@RequestParam String errorMsg,Model model,@ModelAttribute LoginFormDto form){
        log.info(errorMsg);
        model.addAttribute("loginFailMsg",errorMsg);

        return "login";
    }

    @GetMapping("/join")
    public String joinForm(@AuthenticationPrincipal LoginPrincipalDetails details, Model model){
        if(details==null){
            model.addAttribute("signupFormDto",new SignupFormDto());
        }
        else {
            SignupFormDto signupFormDto = new SignupFormDto();
            signupFormDto.setEmail(details.getUsername());
            signupFormDto.setPassword(details.getPassword());
            log.info(details.getPassword());
            signupFormDto.setName(details.getName());

            model.addAttribute("signupFormDto", signupFormDto);
        }

        return "signup";
    }

    @GetMapping("/oauth-join")
    public String oauthJoinForm(@AuthenticationPrincipal LoginPrincipalDetails details, Model model){

        if(memberService.findByEmail(details.getAttribute("email"))!=null)
        {
            return "index";
        }
        SignupFormDto signupFormDto = new SignupFormDto();
        signupFormDto.setEmail(details.getAttribute("email"));
        signupFormDto.setPassword(details.getAttribute("password"));
        signupFormDto.setName(details.getAttribute("name"));

        model.addAttribute("signupFormDto", signupFormDto);
        return "signup";
    }

    @PostMapping("/join")
    public String join(@ModelAttribute SignupFormDto form){

        log.info("join-post = {}",form.getPassword());
        Member member=new Member(form.getEmail(),form.getPassword(),form.getName());
        member.updateRole("ROLE_USER");
        memberService.join(member);

        return "redirect:/loginForm";
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
