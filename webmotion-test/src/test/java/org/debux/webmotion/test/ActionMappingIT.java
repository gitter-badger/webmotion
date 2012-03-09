/*
 * #%L
 * Webmotion in action
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

import java.io.IOException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

/**
 * Test on mapping section action.
 * 
 * @author julien
 */
public class ActionMappingIT extends AbstractIT {

    private static final Logger log = LoggerFactory.getLogger(ActionMappingIT.class);
    
    @Test
    public void action() throws IOException {
        String url = getAbsoluteUrl("act");
        HttpGet request = new HttpGet(url);
        
        String result = execute(request);
        AssertJUnit.assertTrue(result.contains("Execute action"));
    }
    
    @Test
    public void view() throws IOException {
        String url = getAbsoluteUrl("view");
        HttpGet request = new HttpGet(url);
        
        String result = execute(request);
        AssertJUnit.assertTrue(result.contains("Hello world !"));
    }
    
    @Test
    public void url() throws IOException {
        String url = getAbsoluteUrl("url");
        HttpGet request = new HttpGet(url);
        
        String result = execute(request);
        AssertJUnit.assertTrue(result.contains("WebMotion"));
    }
    
    @Test
    public void helloParametersYou() throws IOException {
        String url = getAbsoluteUrl("helloParameters?who=you");
        HttpGet request = new HttpGet(url);
        
        String result = execute(request);
        AssertJUnit.assertTrue(result.contains("Hello you !"));
    }
    
    @Test
    public void helloParametersMe() throws IOException {
        String url = getAbsoluteUrl("helloParameters?who=me");
        HttpGet request = new HttpGet(url);
        
        String result = execute(request);
        AssertJUnit.assertTrue(result.contains("Hello me !"));
    }
    
    @Test
    public void helloDefaultParameters() throws IOException {
        String url = getAbsoluteUrl("helloDefaultParameters");
        HttpGet request = new HttpGet(url);
        
        String result = execute(request);
        AssertJUnit.assertTrue(result.contains("Hello default !"));
    }
    
    @Test
    public void helloDefaultParametersOther() throws IOException {
        String url = getAbsoluteUrl("helloDefaultParameters?who=other");
        HttpGet request = new HttpGet(url);
        
        String result = execute(request);
        AssertJUnit.assertTrue(result.contains("Hello other !"));
    }
    
    @Test
    public void patternOnlyA() throws IOException {
        String url = getAbsoluteUrl("pattern/aaaa?value=aaaa");
        HttpGet request = new HttpGet(url);
        
        String result = execute(request);
        AssertJUnit.assertTrue(result.contains("The who aaaa and value aaaa contains only letter a"));
    }
    
    @Test
    public void patternNotOnlyA() throws IOException {
        String url = getAbsoluteUrl("pattern/baaa?value=baaaa");
        HttpGet request = new HttpGet(url);
        
        String result = execute(request);
        AssertJUnit.assertTrue(result.contains("The who baaa and value baaaa NOT contains only letter a"));
    }
    
    @Test
    public void login() throws IOException {
        String url = getAbsoluteUrl("login?user.name=john&user.passwd=azerty");
        HttpGet request = new HttpGet(url);
        
        String result = execute(request);
        AssertJUnit.assertTrue(result.contains("Login with user name : john and passwd azerty"));
    }
    
    @Test
    public void loginRenamed() throws IOException {
        String url = getAbsoluteUrl("login?name=john&passwd=azerty");
        HttpGet request = new HttpGet(url);
        
        String result = execute(request);
        AssertJUnit.assertTrue(result.contains("Login with user name : john and passwd azerty"));
    }
    
    @Test
    public void selectStatic() throws IOException {
        String url = getAbsoluteUrl("select?param=value");
        HttpGet request = new HttpGet(url);
        
        String result = execute(request);
        AssertJUnit.assertTrue(result.contains("Select with parameter !"));
    }
    
    @Test
    public void select() throws IOException {
        String url = getAbsoluteUrl("select");
        HttpGet request = new HttpGet(url);
        
        String result = execute(request);
        AssertJUnit.assertTrue(result.contains("Select without parameter !"));
    }
    
    @Test
    public void dynamicActionGet() throws IOException {
        String url = getAbsoluteUrl("dynamic/get");
        HttpGet request = new HttpGet(url);
        
        String result = execute(request);
        AssertJUnit.assertTrue(result.contains("Execute get action"));
    }
    
    @Test
    public void dynamicActionSet() throws IOException {
        String url = getAbsoluteUrl("dynamic/set");
        HttpGet request = new HttpGet(url);
        
        String result = execute(request);
        AssertJUnit.assertTrue(result.contains("Execute set action"));
    }
    
    @Test
    public void dynamicViewReadme() throws IOException {
        String url = getAbsoluteUrl("text?file=readme");
        HttpGet request = new HttpGet(url);
        
        String result = execute(request);
        AssertJUnit.assertTrue(result.contains("It is the readme"));
    }
    
    @Test
    public void dynamicViewChangelog() throws IOException {
        String url = getAbsoluteUrl("text?file=changelog");
        HttpGet request = new HttpGet(url);
        
        String result = execute(request);
        AssertJUnit.assertTrue(result.contains("It is the changelog"));
    }
    
    @Test
    public void dynamicViewJohn() throws IOException {
        String url = getAbsoluteUrl("helloView?name=John");
        HttpGet request = new HttpGet(url);
        
        String result = execute(request);
        AssertJUnit.assertTrue(result.contains("Hello John !"));
    }
    
    @Test
    public void dynamicViewJack() throws IOException {
        String url = getAbsoluteUrl("helloView?name=Jack");
        HttpGet request = new HttpGet(url);
        
        String result = execute(request);
        AssertJUnit.assertTrue(result.contains("Hello Jack !"));
    }
    
    @Test
    public void dynamicUrlTutu() throws IOException {
        String url = getAbsoluteUrl("wikipedia/tutu");
        HttpGet request = new HttpGet(url);
        
        String result = execute(request);
        AssertJUnit.assertTrue(result.contains("Tutu"));
    }
    
    @Test
    public void dynamicUrlTata() throws IOException {
        String url = getAbsoluteUrl("wikipedia/tata");
        HttpGet request = new HttpGet(url);
        
        String result = execute(request);
        AssertJUnit.assertTrue(result.contains("Tata"));
    }
    
    @Test
    public void methodGet() throws IOException {
        String url = getAbsoluteUrl("person");
        HttpGet request = new HttpGet(url);
        
        String result = execute(request);
        AssertJUnit.assertTrue(result.contains("Return the person"));
    }
    
    @Test
    public void methodPost() throws IOException {
        String url = getAbsoluteUrl("person");
        HttpPost request = new HttpPost(url);
        
        String result = execute(request);
        AssertJUnit.assertTrue(result.contains("Save the person"));
    }
    
    @Test
    public void multiMethodGet() throws IOException {
        String url = getAbsoluteUrl("video");
        HttpGet request = new HttpGet(url);
        
        String result = execute(request);
        AssertJUnit.assertTrue(result.contains("Get the media"));
    }
    
    @Test
    public void multiMethodPost() throws IOException {
        String url = getAbsoluteUrl("video");
        HttpPost request = new HttpPost(url);
        
        String result = execute(request);
        AssertJUnit.assertTrue(result.contains("Get the media"));
    }
    
    @Test
    public void staticResourceTexte() throws IOException {
        String url = getAbsoluteUrl("readme");
        HttpGet request = new HttpGet(url);
        
        String result = execute(request);
        AssertJUnit.assertTrue(result.contains("Dummy readme"));
    }
    
    @Test
    public void staticResourceImg() throws IOException {
        String url = getAbsoluteUrl("img");
        HttpGet request = new HttpGet(url);
        
        String result = execute(request);
        AssertJUnit.assertFalse(result.isEmpty());
    }
    
}
