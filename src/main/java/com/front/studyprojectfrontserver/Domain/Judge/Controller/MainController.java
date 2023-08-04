package com.front.studyprojectfrontserver.Domain.Judge.Controller;

import com.front.studyprojectfrontserver.Domain.Judge.Adapter.ClassAdapter;
import com.front.studyprojectfrontserver.Domain.Judge.Adapter.JudgeAdapter;
import com.front.studyprojectfrontserver.Domain.Judge.Dto.ClassResponseDto;
import com.front.studyprojectfrontserver.Domain.Judge.Dto.ProblemRequestDto;
import com.front.studyprojectfrontserver.Domain.Judge.Dto.ProblemResponseDto;
import com.front.studyprojectfrontserver.Domain.Member.Dto.SignUpResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import javax.validation.Valid;
@Controller
@RequestMapping("/judge")
@RequiredArgsConstructor
public class MainController {
    private final ClassAdapter classAdapter;
    private final JudgeAdapter judgeAdapter;

    /**
     * 현재 수강중인 분반 정보를 불러옵니다.
     * @param authentication : 인증 정보
     * @param model : model
     * @return judge/class
     * @author : 황시준
     * @since  : 1.0
     */
    @GetMapping("/class")
    public String myJudge(Authentication authentication, Model model){
        ResponseEntity<ClassResponseDto> getCurrentMemberInfo = classAdapter.getClassInfo(
            authentication.getPrincipal().toString()
        );

        model.addAttribute("className", getCurrentMemberInfo.getBody().getClassName());
        model.addAttribute("classSubject", getCurrentMemberInfo.getBody().getSubjectId());
        return "judge/class";
    }

    @GetMapping("/createProblem")
    public ModelAndView createProblem(Authentication authentication,
                                      @Valid ProblemRequestDto problemRequestDto,
                                      BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return new ModelAndView("judge/createProblemF");
        }
        ProblemResponseDto problemResponseDto = judgeAdapter.createProblem(problemRequestDto);
        ModelAndView modelAndView = new ModelAndView("judge/createProblemS");
        modelAndView.addObject("response", problemResponseDto);
        return modelAndView;
    }
}
