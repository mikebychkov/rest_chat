package org.rest_chat.rest_chat_client.control;

import org.rest_chat.model.Message;
import org.rest_chat.rest_chat_client.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/client")
public class ClientRestController {

    private ChatService chatService;

    @Autowired
    public ClientRestController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/message")
    public HttpStatus getMessage(@RequestBody Message message) {
        System.out.println("\n\n");
        System.out.format("#### %s <%s> : %s", message.getRoom().getName(), message.getPerson().getUsername(), message.getText());
        System.out.println("\n\n");
        return HttpStatus.OK;
    }
}
