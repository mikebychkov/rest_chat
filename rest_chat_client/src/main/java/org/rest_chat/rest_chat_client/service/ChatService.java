package org.rest_chat.rest_chat_client.service;

import org.rest_chat.model.Message;
import org.rest_chat.model.Person;
import org.rest_chat.model.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Scanner;
import java.util.Set;

@Service
public class ChatService implements Runnable {

    private final RestTemplate rest;

    private final String srvURL_msg = "http://localhost:9000/server/message";
    private final String srvURL_rooms = "http://localhost:9000/server/rooms";

    @Value("${server.port}")
    private String port;

    private Scanner sc = new Scanner(System.in);

    private Person person;

    @Autowired
    public ChatService(RestTemplate rest) {
        this.rest = rest;
        new Thread(this, "CHAT SERVICE THREAD").start();
    }

    @Override
    public void run() {
        initDialog();
    }

    private void initDialog() {
        System.out.println("\n\n#### Enter your chat name:\n");
        String name = sc.nextLine();
        person = Person.of(name, port);
        Room resentRoom = null;
        boolean exit = false;
        while(!exit) {

            // PICK A ROOM
            while(resentRoom == null) {
                Set<Room> rooms = getRooms();
                System.out.println("ROOM LIST:");
                System.out.println(rooms);
                System.out.println("\n#### Type room name to continue or 'new:<name>' to create new one:");
                String roomName = sc.nextLine();
                if(roomName.isEmpty()) continue;
                if (roomName.startsWith("new:")) {
                    resentRoom = Room.of(roomName.replace("new:", ""));
                    continue;
                }
                for(Room room : rooms) {
                    if(roomName.equals(room.getName())) {
                        resentRoom = room;
                        break;
                    }
                }
            }

            // TYPE A MESSAGE
            String message = "";
            while(message.isEmpty()) {
                System.out.println("\n#### Type your message: ('room' to change a group; 'exit' to exit)");
                message = sc.nextLine();
                if("room".equals(message)) {
                    resentRoom = null;
                    break;
                }
                if("exit".equals(message)) {
                    exit = true;
                    break;
                }
            }
            if(resentRoom == null) continue;
            if(exit) continue;

            // SEND A MESSAGE
            sendMessage(Message.of(resentRoom, person, message));
        }
    }

    private void sendMessage(Message message) {
        rest.postForObject(srvURL_msg, message, Message.class);
    }

    private Set<Room> getRooms() {
        Set<Room> rooms = rest.exchange(srvURL_rooms,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Set<Room>>(){}
        ).getBody();
        return rooms;
    }
}
