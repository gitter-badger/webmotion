/*
 * #%L
 * Webmotion server
 * 
 * $Id$
 * $HeadURL$
 * %%
 * Copyright (C) 2011 - 2015 Debux
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
package org.debux.webmotion.server;

import org.debux.webmotion.server.mapping.Rule;

/**
 * Represents a error during the user request process.
 * 
 * @author jruchaud
 */
public class WebMotionException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    protected Rule rule;

    public Rule getRule() {
        return rule;
    }

    public WebMotionException(String message, Throwable cause, Rule rule) {
        super(message, cause);
        this.rule = rule;
    }

    public WebMotionException(String message, Rule rule) {
        super(message);
        this.rule = rule;
    }
    
    public WebMotionException(String message, Throwable cause) {
        super(message, cause);
    }

    public WebMotionException(String message) {
        super(message, null);
    }
    
}
