package org.rest_chat.model;

import java.util.Objects;

public class Room {

    private String name;

    public Room() {
    }

    public static Room of(String name) {
        Room r = new Room();
        r.setName(name);
        return r;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return Objects.equals(name, room.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Room: " + name;
    }
}
