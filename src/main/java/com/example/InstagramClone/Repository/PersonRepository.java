package com.example.InstagramClone.Repository;

import com.example.InstagramClone.Models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person,Long> {

    Person getByEmail(String email);
}
