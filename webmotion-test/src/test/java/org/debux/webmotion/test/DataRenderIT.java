/*
 * #%L
 * Webmotion in action
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
package org.debux.webmotion.test;

import java.io.IOException;
import java.net.URISyntaxException;
import org.apache.http.client.fluent.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

/**
 * Test data render.
 * 
 * @author julien
 */
public class DataRenderIT extends AbstractIT {

    private static final Logger log = LoggerFactory.getLogger(DataRenderIT.class);
    
    @Test
    public void xml() throws IOException, URISyntaxException {
        Request request = createRequest("/xml")
                .Get();
        
        String result = executeRequest(request);
        AssertJUnit.assertTrue(result, result.contains("John"));
        AssertJUnit.assertTrue(result, result.contains("azerty"));
        AssertJUnit.assertTrue(result, result.contains("77"));
    }
    
    @Test
    public void json() throws IOException, URISyntaxException {
        Request request = createRequest("/json")
                .Get();
        
        String result = executeRequest(request);
        AssertJUnit.assertTrue(result, result.contains("{\"name\":\"John\",\"passwd\":\"azerty\",\"age\":77}"));
    }
    
    @Test
    public void jsonp() throws IOException, URISyntaxException {
        Request request = createRequest("/jsonp")
                .Get();
        
        String result = executeRequest(request);
        AssertJUnit.assertTrue(result, result.contains("test({\"name\":\"John\",\"passwd\":\"azerty\",\"age\":77});"));
    }
    
    @Test
    public void stringTemplate() throws IOException, URISyntaxException {
        Request request = createRequest("/template")
                .Get();
        
        String result = executeRequest(request);
        AssertJUnit.assertTrue(result, result.contains("bla bla bla ..."));
    }
    
    @Test
    public void rss() throws IOException, URISyntaxException {
        Request request = createRequest("/news/rss")
                .Get();
        
        String result = executeRequest(request);
        AssertJUnit.assertTrue(result, result.contains("<rss"));
    }
    
    @Test
    public void atom() throws IOException, URISyntaxException {
        Request request = createRequest("/news/atom")
                .Get();
        
        String result = executeRequest(request);
        AssertJUnit.assertTrue(result, result.contains("<feed"));
    }
    
}
