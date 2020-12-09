package org.rest_chat.model;

import java.util.Objects;

public class Person {

    private String username;
    private String password;
    private String url;

    public Person() {
    }

    public static Person of(String name, String password, String port) {
        Person p = new Person();
        p.username = name;
        p.password = password;
        p.url = "http://localhost:" + port + "/client/message";
        return p;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(username, person.username) &&
                Objects.equals(url, person.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, url);
    }

    @Override
    public String toString() {
        return "Person{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
