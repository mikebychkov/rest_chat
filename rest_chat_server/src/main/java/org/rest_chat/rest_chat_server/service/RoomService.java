package org.rest_chat.rest_chat_server.service;

import org.rest_chat.model.Person;
import org.rest_chat.model.Room;

import java.util.Set;

public interface RoomService {

    Room getRoomByName(String name);

    void initRoom(Room room);

    void addPersonToRoom(Room room, Person person);

    void removePersonFromRoom(Room room, Person person);

    Set<Person> getRoomPersons(Room room);

    Set<Room> getRooms();
}
