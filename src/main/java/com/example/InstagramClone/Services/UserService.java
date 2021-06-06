package com.example.InstagramClone.Services;

import com.example.InstagramClone.Models.Image;
import com.example.InstagramClone.Models.Person;
import com.example.InstagramClone.Repository.CommentRepository;
import com.example.InstagramClone.Repository.ImageRepository;
import com.example.InstagramClone.Repository.PersonRepository;
import com.example.InstagramClone.ValueObject.PersonVo;
import com.example.InstagramClone.ValueObject.UserFriendMapping;
import com.example.InstagramClone.ValueObject.UserTimeLine;
import com.example.InstagramClone.ValueObject.UserTimelineDataVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@Service
public class UserService {
    @Autowired
    PersonRepository personRepository;

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    CommentRepository commentRepository;

    public Person addPerson(Person person){
        Person newUser = new Person();
        newUser.setName(person.getName());
        newUser.setUsername(person.getUsername());
        newUser.setEmail(person.getEmail());
        Person user = personRepository.save(newUser);
        return user;
    }

    public Person getPerson(String email){
        Person user1 = personRepository.getByEmail(email);
        return user1;
    }

    public UserFriendMapping AddFriend(Person follower, Person followeree){
        follower.addFriends(followeree);
        Person user = personRepository.save(follower);

        PersonVo person1 = new PersonVo();
        person1.setName(follower.getName());
        person1.setUsername(follower.getUsername());
        person1.setEmail(follower.getEmail());

        PersonVo person2 = new PersonVo();
        person2.setName(followeree.getName());
        person2.setUsername(followeree.getUsername());
        person2.setEmail(followeree.getEmail());
        return new UserFriendMapping(person1,person2);
    }
    public UserTimeLine generateTimeline(String email){
        Person person = personRepository.getByEmail(email);

        UserTimelineDataVO user1 = new UserTimelineDataVO();
        user1.setName(person.getName());
        user1.setUsername(person.getUsername());
        user1.setEmail(person.getEmail());

        List<Person> tempFriends = person.getAllPerson();
        List<UserTimelineDataVO> friends = new ArrayList<>();

        for(int i=0;i<tempFriends.size();i++){
            Person tempData = tempFriends.get(i);
            UserTimelineDataVO followee = new UserTimelineDataVO();
            followee.setName(tempData.getName());
            followee.setUsername(tempData.getUsername());
            followee.setEmail(tempData.getEmail());
            friends.add(followee);
        }
        return new UserTimeLine(user1,friends);
    }

    public PersonVo uploadImage(String email, MultipartFile multipartFile) throws IOException {
        Person user = personRepository.getByEmail(email);
        List<Image> imageList = user.getImages();

        Image imageModel = new Image();

        imageModel.setName(multipartFile.getName());
        imageModel.setType(multipartFile.getContentType());
        imageModel.setPicture(compressBytes(multipartFile.getBytes()));
        imageModel.setUploaded(Instant.now());
        imageModel.setModified(Instant.now());
        Image image = imageRepository.save(imageModel);
        imageList.add(image);
        user.setImages(imageList);

        personRepository.save(user);

        PersonVo personVo = new PersonVo();
        personVo.setName(user.getName());
        personVo.setEmail(user.getEmail());
        personVo.setUsername(user.getUsername());
        personVo.setPicture(user.getImages());
        return personVo;
    }

    public byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException e) {

        }
        System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);
        return outputStream.toByteArray();
    }	// uncompress the image bytes before returning it to the angular application
    public static byte[] decompressBytes(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (IOException ioe) {

        } catch (DataFormatException e) {

        }
        return outputStream.toByteArray();
    }
}
