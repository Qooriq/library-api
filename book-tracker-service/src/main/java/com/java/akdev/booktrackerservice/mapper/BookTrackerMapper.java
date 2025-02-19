package com.java.akdev.booktrackerservice.mapper;

import com.java.akdev.booktrackerservice.config.MapperConfiguration;
import com.java.akdev.booktrackerservice.dto.create.BookTrackerCreateDto;
import com.java.akdev.booktrackerservice.dto.read.BookTrackerReadDto;
import com.java.akdev.booktrackerservice.entity.BookTracker;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfiguration.class)
public interface BookTrackerMapper {

    BookTrackerReadDto toBookTrackerReadDto(BookTracker bt);

    BookTracker toBookTracker(BookTrackerCreateDto btDto);

    void map(@MappingTarget BookTracker to, BookTrackerCreateDto from);
}
