package com.example.spring_crud_thymeleaf.controller;

import com.example.spring_crud_thymeleaf.model.Book;
import com.example.spring_crud_thymeleaf.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/books/view/{id}")
    public String findById(Model model, @PathVariable Long id) {
        model.addAttribute("book", bookRepository.findById(id).get());
        return "book-view";
    }

    @GetMapping("/books/form")
    public String getEmptyForm(Model model) {
        model.addAttribute("book", new Book());
        return "book-form";
    }

    @GetMapping("/books/edit/{id}")
    public String getFormWithBook(Model model, @PathVariable Long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.findById(id).ifPresent(b -> model.addAttribute("book", b));
            return "book-form";
        } else {
            return "redirect:/books/form";
        }

    }

    @PostMapping("/books")
    public String create(@ModelAttribute Book book) {
        if (book.getId() != null) {
            // actualización
            bookRepository.findById(book.getId()).ifPresent(b -> {
                b.setTitle(book.getTitle());
                b.setAuthor(book.getAuthor());
                b.setPrice(book.getPrice());
                bookRepository.save(b);
            });
        } else {
            // creación
            bookRepository.save(book);
        }
        return "redirect:/books";
    }

    @GetMapping("/books/delete/{id}")
    public String deleteById(@PathVariable Long id) {
        if (bookRepository.existsById(id))
            bookRepository.deleteById(id);
        return "redirect:/books";
    }
}
