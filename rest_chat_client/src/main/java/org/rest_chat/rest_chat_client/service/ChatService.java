package org.rest_chat.rest_chat_client.service;

import org.rest_chat.model.Message;
import org.rest_chat.model.Person;
import org.rest_chat.model.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Scanner;
import java.util.Set;

@Service
public class ChatService implements Runnable {

    private final RestTemplate rest;
    private final String srvLogin = "http://localhost:9000/login";
    private final String srvSignUp = "http://localhost:9000/server/users/sign-up";
    private final String srvMsg = "http://localhost:9000/server/message";
    private final String srvRooms = "http://localhost:9000/server/rooms";
    private final Scanner sc = new Scanner(System.in);
    private final String port;

    @Autowired
    public ChatService(RestTemplate rest, @Value("${server.port}") String port) {
        this.rest = rest;
        this.port = port;
        new Thread(this, "CHAT SERVICE THREAD").start();
    }

    @Override
    public void run() {
        initDialog();
    }

    private void initDialog() {
        System.out.println("\n\n#### Enter your chat name:\n");
        String name = sc.nextLine();
        System.out.println("\n\n#### Enter your chat password:\n");
        String password = sc.nextLine();
        Person person = Person.of(name, password, port);
        List<String> securityToken = registerPersonToServer(person);
        Room resentRoom = null;
        boolean exit = false;
        while (!exit) {
            if (resentRoom == null) {
                resentRoom = pickRoom(securityToken);
            }
            String message = typeMessage();
            if ("room".equals(message)) {
                resentRoom = null;
                continue;
            }
            if ("exit".equals(message)) {
                exit = true;
                continue;
            }
            sendMessage(Message.of(resentRoom, person, message), securityToken);
        }
        System.out.println("#### Chat service exit...");
    }

    private Room pickRoom(List<String> securityToken) {
        Room resentRoom = null;
        while (resentRoom == null) {
            Set<Room> rooms = getRooms(securityToken);
            System.out.println("ROOM LIST:");
            System.out.println(rooms);
            System.out.println("\n#### Type room name to continue or 'new:<name>' to create new one:");
            String roomName = sc.nextLine();
            if (roomName.isEmpty()) {
                continue;
            }
            if (roomName.startsWith("new:")) {
                resentRoom = Room.of(roomName.replace("new:", ""));
                continue;
            }
            for (Room room : rooms) {
                if (roomName.equals(room.getName())) {
                    resentRoom = room;
                    break;
                }
            }
        }
        return resentRoom;
    }

    private String typeMessage() {
        String message = "";
        while (message.isEmpty()) {
            System.out.println("\n#### Type your message: ('room' to change a group; 'exit' to exit)");
            message = sc.nextLine();
        }
        return message;
    }

    private List<String> registerPersonToServer(Person person) {
        rest.postForObject(srvSignUp, person, Person.class);
        return getSecurityToken(person);
    }

    private List<String> getSecurityToken(Person person) {
        ResponseEntity<Void> response = rest.postForEntity(srvLogin, person, Void.class);
        List<String> securityToken = response.getHeaders().get("Authorization");
        System.out.println("\n\n" + securityToken + "\n\n");
        return securityToken;
    }

    private void sendMessage(Message message, List<String> securityToken) {
        HttpEntity<Message> request = new HttpEntity<>(message, getHeaders(securityToken));
        rest.postForEntity(srvMsg, request, Void.class);
    }

    private Set<Room> getRooms(List<String> securityToken) {
        HttpEntity<Void> request = new HttpEntity<>(null, getHeaders(securityToken));
        Set<Room> rooms = rest.exchange(srvRooms,
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<Set<Room>>() { }
        ).getBody();
        return rooms;
    }

    private HttpHeaders getHeaders(List<String> securityToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", securityToken);
        return headers;
    }
}
