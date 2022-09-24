package com.bookstore.service;

import java.util.List;

import com.bookstore.dto.BookDto;
import com.bookstore.dto.CheckoutBooksRequest;
import com.bookstore.dto.CheckoutResponse;
import com.bookstore.dto.PromotionDetails;

public interface BookService {
	List<BookDto> fetchAllBooks();
	BookDto getBookById(long id) throws Exception;
	BookDto createBook(BookDto book) throws Exception;
	BookDto updateBook(BookDto book);
	void deleteBookById(long id);
	void deleteAllBooks();
	PromotionDetails createPromotion(PromotionDetails promotion) throws Exception;
	List<PromotionDetails> fetchAllPromotions() throws Exception;
	CheckoutResponse checkoutBooks(CheckoutBooksRequest checkoutBooks) throws Exception;
}
