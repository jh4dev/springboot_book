package org.zerock.guestbook.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.guestbook.dto.GuestbookDTO;
import org.zerock.guestbook.dto.PageRequestDTO;
import org.zerock.guestbook.service.GuestbookService;

@Controller
@RequestMapping("/guestbook")
@Log4j2
@RequiredArgsConstructor
public class GuestbookController {

    private final GuestbookService guestbookService;

    @GetMapping({"/"})
    public String list() {

        log.info("list...........");

        return "/guestbook/list";
    }
    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model) {

        log.info("list................." + pageRequestDTO);

        model.addAttribute("result", guestbookService.getList(pageRequestDTO));
    }

    @GetMapping("/register")
    public void registGet() {
        log.info(">>>>>>>>>>> Register Get");

    }

    @PostMapping("/register")
    public String registerPost(GuestbookDTO guestbookDTO, RedirectAttributes redirectAttributes) {

        log.info(">>>>>>>>>>>>>> DTO : {}", guestbookDTO);

        Long gno = guestbookService.register(guestbookDTO);
        redirectAttributes.addFlashAttribute("msg", gno);

        return "redirect:/guestbook/list";
    }

    @GetMapping({"/read", "/modify"})
    public void read(long gno, @ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO, Model model) {

        log.info(">>>>>>>>>>>>>> gno : {}", gno);

        GuestbookDTO dto = guestbookService.read(gno);

        model.addAttribute("dto", dto);
    }

    @PostMapping("/modify")
    public String modfiy(GuestbookDTO guestbookDTO, @ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO, RedirectAttributes redirectAttributes) {

        log.info(">>>>>>>>>>>>>>>>>>> modify POST");
        log.info("dto : {}", guestbookDTO);

        guestbookService.modify(guestbookDTO);

        redirectAttributes.addFlashAttribute("page", pageRequestDTO.getPage());
        redirectAttributes.addFlashAttribute("gno", guestbookDTO.getGno());
        redirectAttributes.addFlashAttribute("type", pageRequestDTO.getType());
        redirectAttributes.addFlashAttribute("keyword", pageRequestDTO.getKeyword());

        return "redirect:/guestbook/read";
    }

    @PostMapping("/remove")
    public String remove(long gno, RedirectAttributes redirectAttributes) {

        log.info(">>>>>>>>>>>>>> gno : {}", gno);

        guestbookService.remove(gno);
        redirectAttributes.addFlashAttribute("msg", gno);

        return "redirect:/guestbook/list";
    }
}
