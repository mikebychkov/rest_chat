package org.rest_chat.model;

import java.util.Objects;

public class Message {

    private Room room;
    private Person person;
    private String text;

    public Message() {
    }

    public static Message of(Room room, Person person, String text) {
        Message m = new Message();
        m.setRoom(room);
        m.setPerson(person);
        m.setText(text);
        return m;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Message message = (Message) o;
        return Objects.equals(room, message.room)
                && Objects.equals(person, message.person)
                && Objects.equals(text, message.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(room, person, text);
    }

    @Override
    public String toString() {
        return "Message{"
                + "room=" + room
                + ", person=" + person
                + ", text='" + text + '\''
                + '}';
    }
}
