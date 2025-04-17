package com.example.spring_crud_thymeleaf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.spring_crud_thymeleaf.model.Book;
import com.example.spring_crud_thymeleaf.repository.BookRepository;


@Controller
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/")
    public String index() {
        return "redirect:/books";
    }

    @GetMapping("/books")
    public String findAll(Model model) {
        model.addAttribute("books", bookRepository.findAll());
        return "book-list";
    }

    @GetMapping("/books/{id}")
    public String findById(Model model, @PathVariable Long id) {
        model.addAttribute("book", bookRepository.findById(id).orElse(null));
        return "book-view";
    }

    @GetMapping("/books/new")
    public String createForm(Model model) {
        model.addAttribute("book", new Book());
        return "book-form";
    }

    @GetMapping("/books/{id}/edit")
    public String editForm(Model model, @PathVariable Long id) {
        bookRepository.findById(id).ifPresentOrElse(book -> model.addAttribute("book", book),
                () -> model.addAttribute("book", new Book()));
        return "book-form";
    }

    @PostMapping("/books")
    public String create(@ModelAttribute Book book) {
        if (book.getId() == null) {
            bookRepository.save(book);
        } else {
            bookRepository.findById(book.getId()).ifPresent(existingBook -> {
                existingBook.setTitle(book.getTitle());
                existingBook.setAuthor(book.getAuthor());
                existingBook.setPrice(book.getPrice());
                bookRepository.save(existingBook);
            });
        }
        return "redirect:/books";
    }

    @GetMapping("/books/{id}/delete")
    public String delete(@PathVariable Long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
        }
        return "redirect:/books";
    }

}
