/*
 * #%L
 * Webmotion in test
 * 
 * $Id$
 * $HeadURL$
 * %%
 * Copyright (C) 2011 Debux
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as 
 * published by the Free Software Foundation, either version 3 of the 
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public 
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */
package org.debux.webmotion.test;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;

/**
 * A book
 * 
 * @author jruchaud
 */
public class Book {
    
    static interface BookCommentValidation {}
    
    @NotNull(groups = {Default.class, BookCommentValidation.class})
    protected String isbn;
    
    @Size(min = 8)
    protected String title;

    @NotNull(groups = {BookCommentValidation.class})
    protected String comment;
    
    protected int numberPages;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getNumberPages() {
        return numberPages;
    }

    public void setNumberPages(int numberPages) {
        this.numberPages = numberPages;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
