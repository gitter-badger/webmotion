/*
 * #%L
 * WebMotion server
 * $Id:$
 * $HeadURL:$
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
package org.debux.webmotion.server.websocket;

import javax.servlet.ServletContext;

/**
 * Interface use to manage inbound connection for a websocket.
 * 
 * @author julien
 */
public interface WebSocketInbound {
    
    /* WebSocketInbound name to store attributes */
    public static final String ATTRIBUTE_WEBSOCKET = "wm.websocket";
            
    /**
     * Set the outbound to send message for the client.
     * @param outbound 
     */
    void setOutbound(WebSocketOutbound outbound);
    
    /**
     * Set the current context.
     * @param request 
     */
    void setServletContext(ServletContext servletContext);
    
    /**
     * @return current context.
     */
    ServletContext getServletContext();
    
    /**
     * Call when the client send a message.
     * @param message 
     */
    void receiveTextMessage(String message);
    
    /**
     * Call when the client send a message.
     * @param message 
     */
    void receiveDataMessage(byte[] bytes);
    
    /**
     * Call when the client open a new connection.
     */
    void onOpen();
    
    /**
     * Call when the client close the current connection.
     */
    void onClose();
}
