package com.example.InstagramClone.ValueObject;

import com.example.InstagramClone.Models.Image;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserTimelineDataVO {
    private String name;
    private String username;
    private String email;
    List<Image> picture;
}
