
package com.bookstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.dto.BookDto;
import com.bookstore.dto.CheckoutBooksRequest;
import com.bookstore.dto.CheckoutResponse;
import com.bookstore.dto.PromotionDetails;
import com.bookstore.service.BookService;

@RestController
@RequestMapping("/api/book")
public class BookController {

	@Autowired
	BookService bookService;

	@GetMapping("/fetchAll")
	public ResponseEntity<List<BookDto>> fetchAllBooks() {
		try {
			List<BookDto> books = bookService.fetchAllBooks();
			if (books.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(books, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/getById/{id}")
	public ResponseEntity<BookDto> getBookById(@PathVariable("id") long id) {
		try {
			BookDto book = bookService.getBookById(id);
			if (book != null) {
				return new ResponseEntity<>(book, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/create")
	public ResponseEntity<Object> createBook(@RequestBody BookDto book) {
		try {
			BookDto bookDto = bookService.createBook(book);
			return new ResponseEntity<>(bookDto, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/update")
	public ResponseEntity<BookDto> updateBook(@RequestBody BookDto book) {
		BookDto bookDto = bookService.updateBook(book);
		if (bookDto != null) {
			return new ResponseEntity<>(bookDto, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/deleteById/{id}")
	public ResponseEntity<HttpStatus> deleteBookById(@PathVariable("id") long id) {
		try {
			bookService.deleteBookById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/deleteAll")
	public ResponseEntity<HttpStatus> deleteAllBooks() {
		try {
			bookService.deleteAllBooks();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@PostMapping("/promotion/create")
	public ResponseEntity<Object> createPromotion(@RequestBody PromotionDetails promotion) {
		try {
			PromotionDetails promotionDto = bookService.createPromotion(promotion);
			return new ResponseEntity<>(promotionDto, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@GetMapping("/promotion/fetchAll")
	public ResponseEntity<List<PromotionDetails>> fetchAllPromotions() {
		try {
			List<PromotionDetails> promotions = bookService.fetchAllPromotions();
			if (promotions.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(promotions, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/checkout")
	public ResponseEntity<CheckoutResponse> checkoutBooks(@RequestBody CheckoutBooksRequest checkoutBooks) {
		try {
			CheckoutResponse checkoutResponse = bookService.checkoutBooks(checkoutBooks);
			return new ResponseEntity<>(checkoutResponse, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


}
