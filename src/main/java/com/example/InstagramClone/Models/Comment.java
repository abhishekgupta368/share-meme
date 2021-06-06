package com.example.InstagramClone.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Comment{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String userComment;
    Instant commentTime;

    @OneToMany
    @JoinColumn
    List<Person> commenter;

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", userComment='" + userComment + '\'' +
                ", commentTime=" + commentTime +
                '}';
    }
}
