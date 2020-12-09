package org.rest_chat.rest_chat_server.control;

import org.rest_chat.model.Person;
import org.rest_chat.model.Room;
import org.rest_chat.rest_chat_server.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/server")
public class RoomRestController {

    private final RoomService rooms;

    @Autowired
    public RoomRestController(RoomService rooms) {
        this.rooms = rooms;
    }

    @GetMapping("/rooms")
    public Set<Room> getRooms() {
        return rooms.getRooms();
    }

    @DeleteMapping("/rooms/{roomName}")
    public HttpStatus leaveRoom(@PathVariable String roomName, @RequestBody Person person) {
        Room room = rooms.getRoomByName(roomName);
        if(room == null) {
            return HttpStatus.NOT_FOUND;
        }
        rooms.removePersonFromRoom(room, person);
        return HttpStatus.OK;
    }
}
