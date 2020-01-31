package com.kkwonsy.jpasample.controller;

import com.kkwonsy.jpasample.domain.Address;
import com.kkwonsy.jpasample.domain.Member;
import com.kkwonsy.jpasample.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping("members/new")
    public String create(@Valid MemberForm memberForm, BindingResult result) {
        //tip 왜 Member를 안쓰고 memberForm을 쓰나? 지저분해지잖아? 디테일한 차이가 있어서 따로 관리하는게 좋다 (ex: isEmpty)

        // tip BindingResult를 활용하면 validation 처리에 좋다
        if (result.hasErrors()) {
            return "members/createMemberForm";
        }

        Address address = new Address(memberForm.getCity(), memberForm.getStreet(), memberForm.getZipcode());

        Member member = new Member();
        member.setName(memberForm.getName());
        member.setAddress(address);

        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model) {
        // tip 실무에서는 Member entity를 그대로 뿌리기보다 MemberForm 같은걸 쓰는게 맞다
        // tip API를 만들때에는 절대 엔티티를 외부로 반환해서는 안된다. (내부 스펙이기 때문에, 그리고 password가 포함되어 있을 수도 있다)
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }
}
