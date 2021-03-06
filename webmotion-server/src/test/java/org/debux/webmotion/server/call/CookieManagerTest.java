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
package org.debux.webmotion.server.call;

import org.debux.webmotion.server.tools.ReflectionUtilsTest;
import org.debux.webmotion.server.tools.HttpUtils;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.debux.webmotion.server.*;
import org.debux.webmotion.server.call.CookieManager.CookieEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Test on CookieManager.
 * 
 * @author julien
 */
public class CookieManagerTest {
   
    private static final Logger log = LoggerFactory.getLogger(ReflectionUtilsTest.class);

    protected CookieManager manager;
    protected CookieManager securedManager;
    
    public static class MyObject {
        protected String name;

        public MyObject(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
    
    @BeforeMethod
    public void setUp() {
        manager = new CookieManager(new HttpContext() {
            @Override
            public HttpServletRequest getRequest() {
                return new HttpServletRequestDummy() {
                    @Override
                    public Cookie[] getCookies() {
                        return new Cookie[]{
                            new Cookie("name", "value")
                        };
                    }
                };
            }

            @Override
            public HttpServletResponse getResponse() {
                return new HttpServletResponseDummy() {
                    @Override
                    public void addCookie(Cookie cookie) {
                        // Do nothing
                    }
                };
            }
        });
        
        securedManager = new CookieManager(new HttpContext() {
            @Override
            public HttpServletRequest getRequest() {
                return new HttpServletRequestDummy() {
                    @Override
                    public Cookie[] getCookies() {
                        return new Cookie[]{
                            new Cookie("name", "value")
                        };
                    }
                };
            }

            @Override
            public HttpServletResponse getResponse() {
                return new HttpServletResponseDummy() {
                    @Override
                    public void addCookie(Cookie cookie) {
                        // Do nothing
                    }
                };
            }

            @Override
            public ServerContext getServerContext() {
                return new ServerContext() {
                    @Override
                    public String getSecret() {
                        return HttpUtils.generateSecret();
                    }
                };
            }
        }, "me", true, true);
    }
    
    @Test
    public void testGet() {
        CookieEntity cookie = manager.get("name");
        String value = cookie.getValue();
        AssertJUnit.assertEquals("value", value);
    }
    
    @Test
    public void testRemove() {
        manager.remove("name");
        CookieEntity cookie = manager.get("name");
        AssertJUnit.assertNull(cookie);
    }
    
    @Test
    public void testAdd() {
        CookieEntity cookie = manager.create("other", "other value");
        manager.add(cookie);
        
        cookie = manager.get("other");
        String value = cookie.getValue();
        AssertJUnit.assertEquals("other value", value);
    }
    
    @Test
    public void testSecure() {
        CookieEntity cookie = securedManager.create("other", "other value");
        securedManager.add(cookie);
        
        cookie = securedManager.get("other");
        String value = cookie.getValue();
        AssertJUnit.assertEquals("other value", value);
    }
    
    @Test
    public void testObject() {
        CookieEntity cookie = manager.create("my_object", new MyObject("test"));
        String json = cookie.getValue();
        AssertJUnit.assertEquals("{\"name\":\"test\"}", json);
        
        MyObject value = cookie.getValue(MyObject.class);
        AssertJUnit.assertEquals("test", value.getName());
    }

    @Test
    public void testCollection() {
        List<MyObject> objects = Arrays.asList(
                                        new MyObject("test1"),
                                        new MyObject("test2"),
                                        new MyObject("test3")
                                    );
                
        CookieEntity cookie = manager.create("my_object", objects);
        String json = cookie.getValue();
        AssertJUnit.assertEquals("[{\"name\":\"test1\"},{\"name\":\"test2\"},{\"name\":\"test3\"}]", json);
        
        Collection<MyObject> values = cookie.getValues(MyObject.class);
        AssertJUnit.assertEquals(3, values.size());
    }

}
