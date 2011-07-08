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
package org.debux.webmotion.server.handler;

import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequestWrapper;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpSession;
import org.debux.webmotion.server.WebMotionHandler;
import org.debux.webmotion.server.WebMotionException;
import org.debux.webmotion.server.WebMotionUtils;
import org.debux.webmotion.server.call.Call;
import org.debux.webmotion.server.call.Executor;
import org.debux.webmotion.server.call.FileProgressListener;
import org.debux.webmotion.server.call.HttpContext;
import org.debux.webmotion.server.mapping.Config;
import org.debux.webmotion.server.mapping.Mapping;
import org.debux.webmotion.server.call.Render;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Render creator do the render for user. Apply the good redirect, foward or 
 * include on response.
 * 
 * @author jruchaud
 */
public class RenderCreatorHandler implements WebMotionHandler {

    private static final Logger log = LoggerFactory.getLogger(RenderCreatorHandler.class);

    @Override
    public void handle(Mapping mapping, Call call) {
        try {
            Render render = call.getRender();
            if(render == null) {
                return;
            }
            
            Render.MimeType mine = render.getMimeType();
            if(Render.MIME_VIEW.equals(mine)) {
                renderView(mapping, call);
                
            } else if(Render.MIME_TEMPLATE.equals(mine)) {
                renderTemplate(mapping, call);
                
            } else if(Render.MIME_REFERER.equals(mine)) {
                renderReferer(mapping, call);
                
            } else if(Render.MIME_ACTION.equals(mine)) {
                renderAction(mapping, call);
                
            } else if(Render.MIME_URL.equals(mine)) {
                renderUrl(mapping, call);
                
            } else if(Render.MIME_XML.equals(mine)) {
                renderXml(mapping, call);
                
            } else if(Render.MIME_JSON.equals(mine)) {
                renderJson(mapping, call);
                
            } else if(Render.MIME_JSONP.equals(mine)) {
                renderJsonP(mapping, call);
                
            } else {
                renderContent(mapping, call);
            }

            if(call.isFileUploadRequest()) {
                HttpContext context = call.getContext();
                HttpSession session = context.getSession();
                if(session != null) {
                    session.removeAttribute(FileProgressListener.SESSION_ATTRIBUTE_NAME);
                }
            }
            
        } catch (IOException ioe) {
            throw new WebMotionException("Error during write the render in response", ioe);
            
        } catch (ServletException se) {
            throw new WebMotionException("Error on server when write the render in response", se);
        }
    }

    protected void renderView(Mapping mapping, Call call) throws IOException, ServletException {
        HttpContext context = call.getContext();
        HttpServletResponse response = context.getResponse();
        HttpServletRequest request = context.getRequest();
        
        addModel(call);
        
        String path = getActionPath(mapping, call);
        request.getRequestDispatcher(path).forward(request, response);
    }

    protected void renderTemplate(Mapping mapping, Call call) throws IOException, ServletException {
        HttpContext context = call.getContext();
        HttpServletResponse response = context.getResponse();
        HttpServletRequest request = context.getRequest();
        
        addModel(call);

        ServletRequestWrapper requestWrapper = new HttpServletRequestWrapper(request);
        ResponseWrapper responseWrapper = new ResponseWrapper(response);
        
        String path = getActionPath(mapping, call);
        request.getRequestDispatcher(path).include(requestWrapper, responseWrapper);
        
        String contentType = responseWrapper.getContentType();
        response.setContentType(contentType);

        PrintWriter out = context.getOut();
        String include = responseWrapper.getContent();
        out.write(include);
    }

    protected void renderReferer(Mapping mapping, Call call) throws IOException, ServletException {
        Render render = call.getRender();
        String content = render.getContent();
        HttpContext context = call.getContext();
        HttpServletResponse response = context.getResponse();
        HttpServletRequest request = context.getRequest();
        
        String path = context.getHeader(HttpContext.HEADER_REFERER);
        path = addModel(call, path);
        response.sendRedirect(path);
    }
    
    protected void renderAction(Mapping mapping, Call call) throws IOException, ServletException {
        Render render = call.getRender();
        String content = render.getContent();
        HttpContext context = call.getContext();
        HttpServletResponse response = context.getResponse();
        HttpServletRequest request = context.getRequest();
        
        String path = context.getBaseUrl() + "/"
                + WebMotionUtils.unCapitalizeClass(content).replaceAll("\\.", "/");
        
        path = addModel(call, path);
        response.sendRedirect(path);
    }

    protected void renderContent(Mapping mapping, Call call) throws IOException, ServletException {
        Render render = call.getRender();
        String content = render.getContent();
        HttpContext context = call.getContext();
        HttpServletResponse response = context.getResponse();
        HttpServletRequest request = context.getRequest();
        
//        String mineType = render.getMimeType();
//        response.setContentType(mineType);

        PrintWriter out = context.getOut();
        out.print(content);
    }

    protected void renderUrl(Mapping mapping, Call call) throws IOException, ServletException {
        Render render = call.getRender();
        String content = render.getContent();
        HttpContext context = call.getContext();
        HttpServletResponse response = context.getResponse();
        HttpServletRequest request = context.getRequest();
        
        response.sendRedirect(content);
    }

    protected void renderXml(Mapping mapping, Call call) throws IOException, ServletException {
        Render render = call.getRender();
        HttpContext context = call.getContext();
        HttpServletResponse response = context.getResponse();
        
        Map<String, Object> model = render.getModel();
        Object object = model;
        if (model != null && model.size() == 1) {
            object = model.values().toArray()[0];
        }

        XStream xstream = new XStream();
        String xml = xstream.toXML(object);
        
        PrintWriter out = context.getOut();
        out.print(xml);
        
        String mineType = render.getMimeType().toString();
        response.setContentType(mineType);
    }

    protected void renderJson(Mapping mapping, Call call) throws IOException, ServletException {
        Render render = call.getRender();
        String content = render.getContent();
        HttpContext context = call.getContext();
        HttpServletResponse response = context.getResponse();
        HttpServletRequest request = context.getRequest();
        
        Map<String, Object> model = render.getModel();
        Object object = model;
        if(model != null && model.size() == 1) {
            object = model.values().toArray()[0];
        }
        
        Gson gson = new Gson();
        String json = gson.toJson(object);
        
        PrintWriter out = context.getOut();
        out.print(json);
        
        String mineType = render.getMimeType().toString();
        response.setContentType(mineType);
    }

    protected void renderJsonP(Mapping mapping, Call call) throws IOException, ServletException {
        Render render = call.getRender();
        String content = render.getContent();
        HttpContext context = call.getContext();
        HttpServletResponse response = context.getResponse();
        HttpServletRequest request = context.getRequest();
        
        Map<String, Object> model = render.getModel();
        Object object = model;
        if(model != null && model.size() == 1) {
            object = model.values().toArray()[0];
        }
        
        Gson gson = new Gson();
        String json = gson.toJson(object);
        
        PrintWriter out = context.getOut();
        out.print(content + "(" + json + ");");
        
        String mineType = render.getMimeType().toString();
        response.setContentType(mineType);
    }
    
    protected String getActionPath(Mapping mapping, Call call) {
        Config config = mapping.getConfig();
        String packageName = config.getPackageViews().replaceAll("\\.", "/");
        
        String subPackageName = "";
        String pageName = "";
        
        Executor executor = call.getExecutor();
        if(executor != null) {
            String packageActions = config.getPackageActions();
            String packageFilters = config.getPackageFilters();
            String packageErrors = config.getPackageErrors();

            subPackageName = executor.getClazz().getName();
            subPackageName = subPackageName.replace(packageActions, "");
            subPackageName = subPackageName.replace(packageFilters, "");
            subPackageName = subPackageName.replace(packageErrors, "");
            
            subPackageName = WebMotionUtils.unCapitalizeClass(subPackageName);
            subPackageName = subPackageName.replaceAll("\\.", "/");
        }

        Render render = call.getRender();
        pageName = render.getContent();
        
        String path = "/" + packageName + "/" + subPackageName + "/" + pageName;
        log.info("path = " + path);
        return path;
    }

    /**
     * Add model in attribute
     * @param render
     * @param request 
     */
    protected void addModel(Call call) {
        HttpContext context = call.getContext();
        HttpServletRequest request = context.getRequest();
        Render render = call.getRender();
        
        Map<String, Object> model = render.getModel();
        if(model != null) {
            for (Map.Entry<String, Object> entry : model.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                request.setAttribute(key, value);
            }
        }
    }

    /**
     * Add model in parameter
     * @param call
     * @param url
     * @return 
     */
    protected String addModel(Call call, String url) {
        Render render = call.getRender();
        
        Map<String, Object> model = render.getModel();
        if(model != null) {

            String separator = "?";
            if(url.contains("?")) {
                separator = "&";
            }
            
            for (Map.Entry<String, Object> entry : model.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();

                url += separator + key + "=" + value;
                separator = "&";
            }
        }
        return url;
    }
    
    /**
     * Wrap response to get content. Use to manage template with AJAX call.
     */
    public class ResponseWrapper extends HttpServletResponseWrapper {
        
        protected ByteArrayOutputStream stream;

        public ResponseWrapper(HttpServletResponse response) {
            super(response);
        }
            
        @Override
        public ServletOutputStream getOutputStream() throws IOException {
            return new ServletOutputStreamWrapper(stream);
        }

        @Override
        public PrintWriter getWriter() throws IOException {
            return new PrintWriter(stream);
        }

        @Override
        public void flushBuffer() throws IOException {
            stream.flush();
        }
        
        public String getContent() {
            return stream.toString();
        }
    }

    public class ServletOutputStreamWrapper extends ServletOutputStream {
        protected OutputStream outputStream;

        public ServletOutputStreamWrapper(OutputStream outputStream) {
            this.outputStream = outputStream;
        }

        @Override
        public void write(int b) throws IOException {
            outputStream.write(b);
        }
    }

}