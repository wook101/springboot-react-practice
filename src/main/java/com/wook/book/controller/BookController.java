package com.wook.book.controller;

import com.wook.book.domain.Book;
import com.wook.book.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService){
        this.bookService = bookService;
    }

    @PostMapping("/book")
    public ResponseEntity<?> save(@RequestBody Book book){
        return new ResponseEntity<>(bookService.save(book),HttpStatus.OK);
    }

    @GetMapping("/book")
    public ResponseEntity<?> findAll(){
        return new ResponseEntity<>(bookService.selectAll(), HttpStatus.OK);
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        return new ResponseEntity<>(bookService.selectById(id),HttpStatus.OK);
    }

    @PutMapping("/book/{id}")
    public ResponseEntity<?> updateById(@PathVariable Long id, @RequestBody Book book){
        return new ResponseEntity<>(bookService.update(id,book),HttpStatus.OK);
    }

    @DeleteMapping("/book/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        return new ResponseEntity<>(bookService.delete(id),HttpStatus.OK);
    }


}
