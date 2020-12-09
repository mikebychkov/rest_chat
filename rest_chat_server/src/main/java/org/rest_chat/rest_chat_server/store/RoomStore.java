package org.rest_chat.rest_chat_server.store;

import org.rest_chat.model.Person;
import org.rest_chat.model.Room;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RoomStore {

    private ConcurrentHashMap<Room, Set<Person>> rooms = new ConcurrentHashMap<>();

    public Room getRoomByName(String name) {
        for(Room room : rooms.keySet()) {
            if(name.equals(room.getName())) {
                return room;
            }
        }
        return null;
    }

    public void initRoom(Room room) {
        if(!rooms.keySet().contains(room)) {
            rooms.put(room, new HashSet<>());
        }
    }

    public void addPersonToRoom(Room room, Person person) {
        rooms.get(room).add(person);
    }

    public void removePersonFromRoom(Room room, Person person) {
        rooms.get(room).remove(person);
    }

    public Set<Person> getRoomPersons(Room room) {
        return rooms.get(room);
    }

    public Set<Room> getRooms() {
        return rooms.keySet();
    }
}
