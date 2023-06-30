package com.front.studyprojectfrontserver.Domain.Member.Controller;

import com.front.studyprojectfrontserver.Domain.Member.Dto.LoginRequestDto;
import com.front.studyprojectfrontserver.Domain.Member.Dto.SignUpRequestDto;
import com.front.studyprojectfrontserver.Domain.Member.Dto.SignUpResponseDto;
import com.front.studyprojectfrontserver.Domain.Member.Service.inter.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;
    @GetMapping("/signup")
    public ModelAndView signupForm(@ModelAttribute(name = "member") SignUpRequestDto member) {
        return new ModelAndView("user/signup");
    }

    @GetMapping("/loginF")
    public ModelAndView loginForm() {
        return new ModelAndView("user/loginF");
    }

    @GetMapping("/loginform")
    public ModelAndView loginForm(@ModelAttribute(name = "member") LoginRequestDto loginRequestDto) {
        return new ModelAndView("user/login");
    }

    @PostMapping("/signup")
    public ModelAndView signUp(@ModelAttribute(name="member") @Valid SignUpRequestDto signupRequestDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return new ModelAndView("user/signupF");
        }
        SignUpResponseDto response = memberService.signUp(signupRequestDto);
        ModelAndView modelAndView = new ModelAndView("user/signupS");
        modelAndView.addObject("response", response);
        return modelAndView;
    }

    @GetMapping("/logoutS")
    public String logoutS(){
        return "user/logoutS";
    }
}
