package hello.hellospring.controller;

import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/*
@Controller -> 컴포넌트를 사용하게 되면 스프링에서
스프링 컨테이너는 MemberController 객체를 생성해서 넣어둠 & 스프링이 맡아서 관리
=> 스프링 빈이 관리된다고 표현
* */
@Controller
public class MemberController {
    // new 객체를 생성하지 않고, 스프링 컨테이너에 등록하여 받아서 쓰는 형태로 구현함
    private final MemberService memberService;

    // @Autowired : 스프링이 스프링 컨테이너에 있는 MemberService를 연결시킴
    @Autowired
    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }

    @GetMapping(value="members/new")
    public String CreateForm() {
        return "members/createMemberForm";
    }

    @PostMapping(value="members/new")
    public String create(MemberForm form){
        Member member = new Member();
        member.setName(form.getName());
        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping(value="/members")
    public String list(Model model){
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/membersList";
    }
}