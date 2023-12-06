package org.example.dao;

import org.example.model.Authors;

import java.util.List;

public interface AuthorDAO {


        void addAuthor(Authors author);
        Authors getAuthorById(int id);
        List<Authors> getAllAuthors();
        void updateAuthor(Authors author);
        void deleteAuthor(int id);




}
