package com.example.InstagramClone.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Person{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String username;
    private String email;

    @OneToMany
    @JoinColumn
    private Set<Person> friends;

    @OneToMany(cascade = {CascadeType.ALL})
    @JoinColumn
    private List<Image> images;

    public void addFriends(Person user){
        friends.add(user);
    }

    public void deletePerson(Person user) {
        friends.remove(user);
    }

    public List<Person> getAllPerson(){
        return new ArrayList<>(friends);
    }

    public void addImage(Image image){
        images.add(image);
    }

    public void deleteImage(Image image) {
        images.remove(image);
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", friends=" + friends +
                ", images=" + images +
                '}';
    }
}
