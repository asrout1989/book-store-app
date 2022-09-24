package com.bookstore.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.dto.BookDto;
import com.bookstore.dto.CheckoutBooksRequest;
import com.bookstore.dto.CheckoutResponse;
import com.bookstore.dto.PromotionDetails;
import com.bookstore.mapper.BookMapper;
import com.bookstore.model.Book;
import com.bookstore.repository.BookRepository;

@Service
public class BookServiceImpl implements BookService {

	@Autowired
	BookRepository bookRepository;
	
	public static Set<PromotionDetails> promotionDetails = new HashSet<>();

	@Override
	public List<BookDto> fetchAllBooks() {

		List<Book> books = bookRepository.findAll();
		List<BookDto> bookDtoList = new ArrayList<>();
		if (!books.isEmpty()) {
			for (Book bookDB : books) {
				bookDtoList.add(BookMapper.getInstance().bookToBookDto(bookDB));
			}
		}
		return bookDtoList;
	}

	@Override
	public BookDto getBookById(long id) throws Exception {
		Book book = bookRepository.findById(id).orElse(null);
		if (book != null) {
			return BookMapper.getInstance().bookToBookDto(book);
		} else
			throw new Exception("Book Not Found");
	}

	@Override
	public BookDto createBook(BookDto bookDto) throws Exception {
		try {
			Book book = BookMapper.getInstance().bookDtoToBook(bookDto);
			Book bookDb = bookRepository.save(book);
			return BookMapper.getInstance().bookToBookDto(bookDb);
		} catch (Exception e) {
			throw new Exception("Unable to save data.");
		}
	}

	@Override
	public BookDto updateBook(BookDto bookDto) {
		if (bookDto != null) {
			Book bookDb = bookRepository.findById(bookDto.getId()).orElse(null);
			if (bookDb != null) {
				Book bookRequest = BookMapper.getInstance().bookDtoToBook(bookDto);
				bookRequest.setId(bookDb.getId());
				bookRepository.save(bookRequest);
			}
		}

		return bookDto;
	}

	@Override
	public void deleteBookById(long id) {
		bookRepository.deleteById(id);

	}

	@Override
	public void deleteAllBooks() {
		bookRepository.deleteAll();

	}

	@Override
	public PromotionDetails createPromotion(PromotionDetails promotion) throws Exception {
		try {
			if (promotionDetails != null && promotion != null)
				promotionDetails.add(promotion);
			return promotion;
		} catch (Exception e) {
			throw new Exception("Unable to save data.");
		}
	}
	
	
	private PromotionDetails getPromotion(String type, String promotionCode) throws Exception {
		try {
			PromotionDetails promoDetails = null;
			if (promotionDetails != null && type != null && promotionCode != null) {
				promoDetails =  promotionDetails.stream().filter(promo -> promotionCode.equalsIgnoreCase(promo.getPromotionCode())
						&& type.equalsIgnoreCase(promo.getType())).findFirst().orElse(null);
			}
				return promoDetails;
		} catch (Exception e) {
			throw new Exception("Unable to fetch promotion data.");
		}
	}

	@Override
	public List<PromotionDetails> fetchAllPromotions() throws Exception {
		return new ArrayList<>(promotionDetails);
	}

	@Override
	public CheckoutResponse checkoutBooks(CheckoutBooksRequest checkoutBooks) throws Exception {
		double totalSum = 0.0;
		if (checkoutBooks != null && checkoutBooks.getBookList() != null && !checkoutBooks.getBookList().isEmpty()) {

			for (BookDto bookDto : checkoutBooks.getBookList()) {
				Book book = bookRepository.findById(bookDto.getId()).orElse(null);
				if (book != null) {
					if (checkoutBooks.getPromotionCode() != null && bookDto.getType() != null) {
						PromotionDetails details = getPromotion(bookDto.getType(), checkoutBooks.getPromotionCode());
						if (details != null) {
							double bookValue = book.getPrice()
									- (book.getPrice() * details.getDiscountPercentage() / 100);
							totalSum += bookValue;
						} else {
							totalSum += book.getPrice();
						}
					} else {
						totalSum += book.getPrice();
					}
				}
			}

		}
		CheckoutResponse response = new CheckoutResponse();
		response.setTotalPrice(totalSum);

		return response;
	}

}
