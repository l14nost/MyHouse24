package lab.space.my_house_24.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lab.space.my_house_24.entity.Message;
import lab.space.my_house_24.model.message.MessageMainPageRequest;
import lab.space.my_house_24.model.message.MessageRequestForSend;
import lab.space.my_house_24.model.message.MessageResponseForMain;
import lab.space.my_house_24.service.MessageService;
import lab.space.my_house_24.util.ErrorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("messages")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;


    @GetMapping({"/",""})
    public ModelAndView messageMainPage(){
        return new ModelAndView("admin/pages/message/message-main");
    }

    @PostMapping("/get-all-message")
    public ResponseEntity getAllMessage(@RequestBody MessageMainPageRequest mainPageRequest) {
        return ResponseEntity.ok().body(messageService.findAllForMessageMain(mainPageRequest));
    }


    @GetMapping("/message-card/{id}")
    public ModelAndView messageCardPage(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("admin/pages/message/message-card");
        modelAndView.addObject("id", id);
        return modelAndView;
    }

    @GetMapping("/get-message-by-id/{id}")
    public ResponseEntity getMessageById(@PathVariable Long id) {
        try{
            return ResponseEntity.ok().body(messageService.findByIdForCard(id));
        }
        catch (EntityNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete-message/{id}")
    public ResponseEntity deleteMessage(@PathVariable Long id) {
        messageService.deleteMessage(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/add-message")
    public ModelAndView addMessagePage() {
        return new ModelAndView("admin/pages/message/message-add");
    }


    @PostMapping("/add-message")
    public ResponseEntity addMessage(@RequestBody @Valid MessageRequestForSend messageRequestForSend, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(ErrorMapper.mapErrors(result));
        }
        messageService.sendMessage(messageRequestForSend);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete-messages")
    public ResponseEntity deleteMessages(@RequestParam List<Long> ids) {
        for (Long id : ids) {
            messageService.deleteMessage(id);
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/add-message-debt")
    public ModelAndView addMessageDebtPage(){
        return new ModelAndView("admin/pages/message/message-add");
    }

    @MessageMapping("/main")
    @SendTo("/topic/refresh")
    public String change(){
        return "change";
    }
}
