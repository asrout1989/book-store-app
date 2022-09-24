package com.bookstore.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.bookstore.dto.BookDto;
import com.bookstore.model.Book;

@Mapper(componentModel = "spring")
public interface BookMapper {
	static BookMapper getInstance() {
		return Mappers.getMapper(BookMapper.class);
	}

	
	  BookDto bookToBookDto(Book book);
	  
	  Book bookDtoToBook(BookDto bookDto);
	 

}
