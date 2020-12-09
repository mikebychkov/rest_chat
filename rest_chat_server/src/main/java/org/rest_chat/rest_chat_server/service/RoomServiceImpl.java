package org.rest_chat.rest_chat_server.service;

import org.rest_chat.model.Person;
import org.rest_chat.model.Room;
import org.rest_chat.rest_chat_server.store.RoomStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomStore store;

    @Autowired
    public RoomServiceImpl(RoomStore store) {
        this.store = store;
    }

    @Override
    public Room getRoomByName(String name) {
        return store.getRoomByName(name);
    }

    @Override
    public void initRoom(Room room) {
        store.initRoom(room);
    }

    @Override
    public void addPersonToRoom(Room room, Person person) {
        store.addPersonToRoom(room, person);
    }

    @Override
    public void removePersonFromRoom(Room room, Person person) {
        store.removePersonFromRoom(room, person);
    }

    @Override
    public Set<Person> getRoomPersons(Room room) {
        return store.getRoomPersons(room);
    }

    @Override
    public Set<Room> getRooms() {
        return store.getRooms();
    }
}
