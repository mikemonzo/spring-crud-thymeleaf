package com.example.spring_crud_thymeleaf;

import java.util.List;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.example.spring_crud_thymeleaf.model.Book;
import com.example.spring_crud_thymeleaf.repository.BookRepository;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SpringCrudThymeleafApplication {

	public static void main(String[] args) {
		ApplicationContext context =
				SpringApplication.run(SpringCrudThymeleafApplication.class, args);
		var repo = context.getBean(BookRepository.class);

		List<Book> books = List.of(new Book(null, "book1", "author1", 10.0),
				new Book(null, "book2", "author2", 15.0), new Book(null, "book3", "author3", 20.0),
				new Book(null, "book4", "author4", 25.0));
		repo.saveAll(books);
	}

}
