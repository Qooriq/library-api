package com.java.akdev.libraryapi.mapper;

import com.java.akdev.libraryapi.config.MapperConfigurationn;
import com.java.akdev.libraryapi.dto.BookDto;
import com.java.akdev.libraryapi.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfigurationn.class)
public interface BookMapper {

    BookDto toBookDto(Book book);

    Book toBook(BookDto bookDto);

    void map(@MappingTarget Book to, BookDto from);
}