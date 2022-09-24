package com.bookstore.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties
@NoArgsConstructor
public class CheckoutBooksRequest implements Serializable {

	List<BookDto> bookList;
	String promotionCode;

}
