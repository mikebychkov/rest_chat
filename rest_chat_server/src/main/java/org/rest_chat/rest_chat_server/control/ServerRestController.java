package org.rest_chat.rest_chat_server.control;

import org.rest_chat.model.Message;
import org.rest_chat.model.Person;
import org.rest_chat.model.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/server")
public class ServerRestController {

    private final RestTemplate rest;

    private Map<Room, Set<Person>> rooms = new HashMap<>();

    @Autowired
    public ServerRestController(RestTemplate rest) {
        this.rest = rest;
    }

    @PostMapping("/message")
    public Message postMessage(@RequestBody Message message) {
        Room room = message.getRoom();
        initRoom(room);
        RegisterPersonToRoom(message.getPerson(), room);
        notifyRoom(message);
        return message;
    }

    private void initRoom(Room room) {
        if(!rooms.keySet().contains(room)) {
            rooms.put(room, new HashSet<>());
        }
    }

    private void RegisterPersonToRoom(Person person, Room room) {
        rooms.get(room).add(person);
    }

    private void notifyRoom(Message message) {
        for(Person person : rooms.get(message.getRoom())) {
            if(person.equals(message.getPerson())) continue;
            rest.postForObject(person.getUrl(), message, HttpStatus.class);
        }
    }

    @GetMapping("/rooms")
    public Set<Room> getRooms() {
        return rooms.keySet();
    }

    @DeleteMapping("/rooms/{name}")
    public HttpStatus leaveRoom(@PathVariable String name, @RequestBody Person person) {
        for(Map.Entry<Room, Set<Person>> ent : rooms.entrySet()) {
            if(ent.getKey().getName().equals(name)) {
                ent.getValue().remove(person);
            }
        }
        return HttpStatus.OK;
    }
}
