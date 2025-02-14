package com.java.akdev.libraryapi.mapper;

import com.java.akdev.libraryapi.config.MapperConfigurationn;
import com.java.akdev.libraryapi.dto.create.BookTrackerCreateDto;
import com.java.akdev.libraryapi.dto.read.BookTrackerReadDto;
import com.java.akdev.libraryapi.entity.BookTracker;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfigurationn.class)
public interface BookTrackerMapper {

    BookTrackerReadDto toBookTrackerReadDto(BookTracker bt);

    BookTracker toBookTracker(BookTrackerCreateDto btDto);

    void map(@MappingTarget BookTracker to, BookTrackerCreateDto from);
}
