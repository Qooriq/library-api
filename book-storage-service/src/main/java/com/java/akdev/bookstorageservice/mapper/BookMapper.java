package com.java.akdev.bookstorageservice.mapper;

import com.java.akdev.bookstorageservice.config.MapperConfigurationn;
import com.java.akdev.bookstorageservice.dto.BookDto;
import com.java.akdev.bookstorageservice.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfigurationn.class)
public interface BookMapper {

    BookDto toBookDto(Book book);

    Book toBook(BookDto bookDto);

    void map(@MappingTarget Book to, BookDto from);
}