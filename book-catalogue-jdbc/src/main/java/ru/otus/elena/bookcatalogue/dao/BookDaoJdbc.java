
package ru.otus.elena.bookcatalogue.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.elena.bookcatalogue.domain.Author;
import ru.otus.elena.bookcatalogue.domain.Book;


    
@Repository("bookDao")
public class BookDaoJdbc implements BookDao {
    
    private final NamedParameterJdbcOperations jdbcOperations;

    public BookDaoJdbc(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.jdbcOperations = namedParameterJdbcOperations;
    }
    

    @Override
    public int count() {
        return jdbcOperations.queryForObject("select count(*)from book", new HashMap<String,String>(1),Integer.class);
    }

    @Override
    public long insert(Book book) {
        String bookQuery="insert into BOOK values (null,:name,:genre)";
        final KeyHolder bKeyHolder=new GeneratedKeyHolder();       
        final HashMap<String,Object>bookParams=new HashMap<>(2);
        bookParams.put("name",book.getName());
        bookParams.put("genre", book.getGenre());        
        MapSqlParameterSource parameters = new MapSqlParameterSource().addValues(bookParams);
        jdbcOperations.update(bookQuery, parameters, bKeyHolder);
        long id = bKeyHolder.getKey().longValue();
        book.setId(id);
        final String authorQuery = "insert into author values(null,:authorname,:book_id)";        
        final HashMap<String, Object> authorParams = new HashMap<>(3);
        book.getAuthors().forEach(author -> {
            authorParams.clear();
            authorParams.put("authorname",author.getAuthorName());          
            authorParams.put("book_id", id);
            MapSqlParameterSource authorParameters = new MapSqlParameterSource().addValues(authorParams);
            final KeyHolder aKeyHolder=new GeneratedKeyHolder();
            jdbcOperations.update(authorQuery, authorParameters,aKeyHolder);
            long authorId=aKeyHolder.getKey().longValue();
            author.setId(authorId);
        });
        return id;
    }

    @Override
    public Book getById(long id) {
        try {
            List<Author> listAuthors = getAuthors(id);
            final HashMap<String, Object> params = new HashMap<>(1);
            params.put("id", id);
            String query = "select*from book where id = :id";
            Book book = jdbcOperations.queryForObject(query, params, new BookMapper());
            book.getAuthors().addAll(listAuthors);
            return book;
        } catch (DataAccessException dae) {
            return null;
        }
    }

    @Override
    public List<Book> getAll() {     
        List<Book>listBook= jdbcOperations.query("select * from book", new BookMapper());
        listBook.forEach(book->{
            book.getAuthors().addAll(getAuthors(book.getId()));
        });
        return listBook;
    }
    
    
    private List<Author> getAuthors(long bookId) {
        final HashMap<String, Object> params = new HashMap<>(1);
        params.put("book_id", bookId);
        String query = "select*from author where book_id = :book_id";        
        return jdbcOperations.query(query, params, new AuthorMapper());
    }

    @Override
    public int delete(long id) {
        String query="delete from book where id =:id";
        final HashMap<String, Object> params = new HashMap<>(1);
        params.put("id", id);
        int updatedBook=jdbcOperations.update(query, params);
        query="delete from author where book_id = :id";
        int updatedAuthor=jdbcOperations.update(query, params);
        return updatedBook+updatedAuthor;
    }

    @Override
    public List<Book> getByName(String name) {
        String query="select*from book where name = :name";
        final HashMap<String, Object> params = new HashMap<>(1);
        params.put("name", name);
        List<Book> listBook = jdbcOperations.query(query, params, new BookMapper());
        listBook.forEach(book -> {
            book.getAuthors().addAll(getAuthors(book.getId()));
        });
        return listBook;
    }

    @Override
    public List<Book> getByGenre(String genre) {
        String query = "select*from book where genre = :genre";
        final HashMap<String, Object> params = new HashMap<>(1);
        params.put("genre", genre);
        List<Book> listBook = jdbcOperations.query(query, params, new BookMapper());
        listBook.forEach(book -> {
            book.getAuthors().addAll(getAuthors(book.getId()));
        });
        return listBook;
    }


    private static class BookMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            String genre=resultSet.getString("genre");
            return new Book(name, genre, new ArrayList<Author>(), id);
        }
    }
    private static class AuthorMapper implements RowMapper<Author>{

        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            long id=resultSet.getLong("id");
            String authorName=resultSet.getString("authorname");
            return new Author(authorName,id);
        }
        
    }

}

