package org.rest_chat.model;

import java.util.Objects;

public class Person {

    private String name;
    private String url;

    public Person() {
    }

    public static Person of(String name, String port) {
        Person p = new Person();
        p.name = name;
        p.url = "http://localhost:" + port + "/client/message";
        return p;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        return Objects.equals(name, person.name) &&
                Objects.equals(url, person.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, url);
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
