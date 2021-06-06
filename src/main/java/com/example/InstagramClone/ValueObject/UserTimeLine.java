package com.example.InstagramClone.ValueObject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserTimeLine {
    private UserTimelineDataVO user;
    private List<UserTimelineDataVO> friends;
}
