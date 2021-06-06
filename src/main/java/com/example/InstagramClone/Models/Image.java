package com.example.InstagramClone.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private String name;
    private String type;

    @Lob
    private byte[] picture;

    private Instant uploaded;
    private Instant modified;

    @OneToMany(cascade = {CascadeType.ALL})
    @JoinColumn
    private List<Comment> comments;

    public void addComments(Comment comment){
        comments.add(comment);
    }

    public void removeComment(Comment comment){
        comments.remove(comment);
    }

    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", picture=" + Arrays.toString(picture) +
                ", comments=" + comments +
                '}';
    }
}
