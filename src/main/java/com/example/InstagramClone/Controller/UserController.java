package com.example.InstagramClone.Controller;

import com.example.InstagramClone.DataAccessObject.FollowerAndFollowereeDAO;
import com.example.InstagramClone.DataAccessObject.PersonDAO;
import com.example.InstagramClone.Models.Person;
import com.example.InstagramClone.Services.UserService;
import com.example.InstagramClone.ValueObject.PersonVo;
import com.example.InstagramClone.ValueObject.UserFriendMapping;
import com.example.InstagramClone.ValueObject.UserTimeLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public ResponseEntity<?> hello(){
        return new ResponseEntity<>("hello world",HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/getUser",method = RequestMethod.GET)
    public ResponseEntity<?> getUser(@RequestBody FollowerAndFollowereeDAO followerAndFollowereeDAO){
        Person person = userService.getPerson(followerAndFollowereeDAO.getFollower());
        PersonVo personVo = new PersonVo();
        personVo.setName(person.getName());
        personVo.setUsername(person.getUsername());
        personVo.setEmail(person.getEmail());
        return new ResponseEntity<>(personVo,HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/addPerson",method = RequestMethod.POST)
    public ResponseEntity<?> addUser(@RequestBody PersonDAO personDAO){
        System.out.println(personDAO.getPerson());
        Person person = userService.addPerson(personDAO.getPerson());
        PersonVo personVo = new PersonVo();
        personVo.setName(person.getName());
        personVo.setUsername(person.getUsername());
        personVo.setEmail(person.getEmail());

        return new ResponseEntity<>( personVo,HttpStatus.CREATED);
    }

    @RequestMapping(value = "/addFriend",method = RequestMethod.POST)
    public ResponseEntity<?> addFriend(@RequestBody FollowerAndFollowereeDAO followerAndFollowereeDAO){
        Person user = userService.getPerson(followerAndFollowereeDAO.getFollower());
        Person friend = userService.getPerson(followerAndFollowereeDAO.getFolloweree());
        UserFriendMapping userAndFriendMapping = userService.AddFriend(user,friend);
        return new ResponseEntity<>(userAndFriendMapping,HttpStatus.CREATED);
    }

    @RequestMapping(value = "/getTimeline/{email}",method = RequestMethod.GET)
    public ResponseEntity<?> getTimeline(@PathVariable("email") String email){
        UserTimeLine userTimeLine = userService.generateTimeline(email);
        return new ResponseEntity<>(userTimeLine,HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/uploadImage",method=RequestMethod.POST,consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> uploadImage(@RequestPart("email") String email, @RequestPart("image") MultipartFile multipartFile) throws IOException {
        PersonVo person = userService.uploadImage(email,multipartFile);
        return new ResponseEntity<>(person,HttpStatus.ACCEPTED);

    }
}
