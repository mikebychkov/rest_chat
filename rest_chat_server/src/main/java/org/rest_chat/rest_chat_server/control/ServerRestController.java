package org.rest_chat.rest_chat_server.control;

import org.rest_chat.model.Message;
import org.rest_chat.model.Person;
import org.rest_chat.model.Room;
import org.rest_chat.rest_chat_server.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/server")
public class ServerRestController {

    private final RestTemplate rest;
    private final RoomService rooms;

    @Autowired
    public ServerRestController(RestTemplate rest, RoomService rooms) {
        this.rest = rest;
        this.rooms = rooms;
    }

    @PostMapping("/message")
    public Message postMessage(@RequestBody Message message) {
        Room room = message.getRoom();
        rooms.initRoom(room);
        rooms.addPersonToRoom(room, message.getPerson());
        notifyRoom(message);
        return message;
    }

    private void notifyRoom(Message message) {
        for (Person person : rooms.getRoomPersons(message.getRoom())) {
            if (person.equals(message.getPerson())) {
                continue;
            }
            rest.postForObject(person.getUrl(), message, HttpStatus.class);
        }
    }
}
