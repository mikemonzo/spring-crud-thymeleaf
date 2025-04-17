package com.example.spring_crud_thymeleaf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.spring_crud_thymeleaf.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

}
