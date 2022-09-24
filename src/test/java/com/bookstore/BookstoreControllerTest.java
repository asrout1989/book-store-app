
package com.bookstore;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.bookstore.dto.BookDto;
import com.bookstore.dto.CheckoutBooksRequest;
import com.bookstore.dto.PromotionDetails;
import com.bookstore.model.Book;
import com.bookstore.repository.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@EnableAutoConfiguration
public class BookstoreControllerTest {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	BookRepository bookRepository;

	@Test
	public void fetchAllBooksTest() throws Exception {
		List<Book> bookList = new ArrayList<>();
		Book book = new Book();
		book.setId(1);
		book.setAuther("test Auther");
		book.setDescription("test description");
		book.setType("type1");
		bookList.add(book);
		when(bookRepository.findAll()).thenReturn(bookList);
		mockMvc.perform(get("/api/book/fetchAll").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andDo(print());
	}

	@Test
	public void getBookByIdTest() throws Exception {
		Book book = new Book();
		book.setId(1);
		book.setAuther("test Auther");
		book.setDescription("test description");
		book.setType("type1");
		when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
		mockMvc.perform(get("/api/book//getById/{id}", "1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andDo(print());
	}

	@Test
	public void createBookTest() throws Exception {
		Book book = new Book();
		book.setId(1);
		book.setAuther("test Auther");
		book.setDescription("test description");
		book.setType("type1");
		book.setPrice(200.00);
		book.setName("book1");

		BookDto bookDto = new BookDto();
		bookDto.setAuther("test Auther");
		bookDto.setDescription("test description");
		bookDto.setType("type1");
		bookDto.setPrice(200.00);
		bookDto.setName("book1");
		when(bookRepository.save(book)).thenReturn(book);

		ObjectMapper mapper = new ObjectMapper();
		mockMvc.perform(post("/api/book/create").content(mapper.writeValueAsString(bookDto))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andDo(print());
	}

	@Test
	public void updateBookTest() throws Exception {
		Book book = new Book();
		book.setId(1);
		book.setAuther("test Auther");
		book.setDescription("test description");
		book.setType("type1");
		book.setPrice(200.00);
		book.setName("book1");

		BookDto bookDto = new BookDto();
		bookDto.setId(1);
		bookDto.setAuther("test Auther");
		bookDto.setDescription("test description");
		bookDto.setType("type1");
		bookDto.setPrice(200.00);
		bookDto.setName("book1");
		when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
		when(bookRepository.save(book)).thenReturn(book);

		ObjectMapper mapper = new ObjectMapper();
		mockMvc.perform(put("/api/book/update").content(mapper.writeValueAsString(bookDto))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andDo(print());
	}

	@Test
	public void createPromotionTest() throws Exception {

		PromotionDetails promotionDetails = new PromotionDetails();
		promotionDetails.setType("type1");
		promotionDetails.setDiscountPercentage(20.00);
		promotionDetails.setPromotionCode("abcd");
		ObjectMapper mapper = new ObjectMapper();
		mockMvc.perform(post("/api/book/promotion/create").content(mapper.writeValueAsString(promotionDetails))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andDo(print());
	}

	@Test
	public void fetchAllPromotions() throws Exception {
		List<PromotionDetails> promotionList = new ArrayList<>();
		PromotionDetails promotionDetails = new PromotionDetails();
		promotionDetails.setType("type1");
		promotionDetails.setDiscountPercentage(20.00);
		promotionDetails.setPromotionCode("abcd");
		promotionList.add(promotionDetails);
		mockMvc.perform(get("/api/book/promotion/fetchAll").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andDo(print());
	}

	@Test
	public void checkoutBooksTest() throws Exception {
		Book book = new Book();
		book.setId(1);
		book.setAuther("test Auther");
		book.setDescription("test description");
		book.setType("type1");
		book.setPrice(200.00);
		book.setName("book1");

		CheckoutBooksRequest request = new CheckoutBooksRequest();
		List<BookDto> bookDtoList = new ArrayList<>();
		BookDto bookDto = new BookDto();
		bookDto.setId(1);
		bookDto.setType("type1");
		bookDto.setPrice(200.00);
		bookDto.setName("book1");
		bookDtoList.add(bookDto);
		request.setBookList(bookDtoList);
		request.setPromotionCode("abcd");

		when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

		ObjectMapper mapper = new ObjectMapper();
		mockMvc.perform(post("/api/book/checkout").content(mapper.writeValueAsString(request))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andDo(print());
	}

	@Test
	public void deleteBookByIdTest() throws Exception {
		mockMvc.perform(delete("/api/book/deleteById/{id}", "1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent()).andDo(print());
	}

	@Test
	public void deleteAllTest() throws Exception {
		mockMvc.perform(delete("/api/book/deleteAll", "1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent()).andDo(print());
	}

}
