
package ru.otus.elena.bookcatalogue.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;
import ru.otus.elena.bookcatalogue.domain.Book;

@SuppressWarnings("JpaQlInspection")
@Transactional
@Repository
public class BookDaoJpa implements BookDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public long count() {
        Query query=em.createQuery("select count (b) from Book b");
        return (long)query.getSingleResult();
    }

    @Override
    public int insert(Book book) {
        if (book.getId() == 0) {
            em.persist(book);
        } else {
            em.merge(book);
        }
        return book.getId();
    }

    @Override
    public Book getById(int id) {
        return em.find(Book.class, id);
    }

    @Override
    public List<Book> getAll() {
        TypedQuery<Book> query = em.createQuery("select b from Book b", Book.class);
        return query.getResultList();
    }

    @Override
    public int delete(int id) {
        try {
            Book book = em.find(Book.class, id);
            em.remove(book);
            return 1;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }

    @Override
    public List<Book> getByName(String name) {
        TypedQuery<Book>query=em.createQuery("select e from Book e where e.name=:name",Book.class);
        query.setParameter("name",name);
        return query.getResultList(); 
    }

    @Override
    public List<Book> getByGenre(String genre) {
        TypedQuery<Book>query=em.createQuery("select e from Book e where e.genre=:genre",Book.class);
        query.setParameter("genre",genre);
        return query.getResultList();
    }
    
}
