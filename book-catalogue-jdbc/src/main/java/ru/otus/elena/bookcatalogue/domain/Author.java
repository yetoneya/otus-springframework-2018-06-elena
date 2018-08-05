
package ru.otus.elena.bookcatalogue.domain;

import ru.otus.elena.bookcatalogue.dataset.DataSet;

public class Author extends DataSet{
    private String authorName;

    public Author(String authorName) {
        super(0);
        this.authorName = authorName;
    }

    public Author(String authorName,long id) {
        super(id);
        this.authorName = authorName;     
    }

    public String getAuthorName() {
        return authorName;
    }


    @Override
    public long getId() {
        return super.getId(); 
    }

    @Override
    public String toString() {
        return "author = "+authorName;
    }
    
    
}
