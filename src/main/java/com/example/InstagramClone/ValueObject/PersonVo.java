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
public class PersonVo {
    String name;
    String username;
    String email;
    List<Image> picture;
}
