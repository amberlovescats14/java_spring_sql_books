package com.freecodecamp.api.repos;

import com.freecodecamp.api.model.Genre;
import org.springframework.data.repository.CrudRepository;

public interface GenreRepo extends CrudRepository<Genre, Long> {

}
