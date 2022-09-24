package com.bookstore.dto;

import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class BookDto implements Serializable {
	private long id;
	private String name;
	private String description;
	private String auther;
	private String type;
	private double price;
	private long isbn;

}
