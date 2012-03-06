<!--
  #%L
  Webmotion website
  
  $Id$
  $HeadURL$
  %%
  Copyright (C) 2011 Debux
  %%
  This program is free software: you can redistribute it and/or modify
  it under the terms of the GNU Lesser General Public License as 
  published by the Free Software Foundation, either version 3 of the 
  License, or (at your option) any later version.
  
  This program is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU General Lesser Public License for more details.
  
  You should have received a copy of the GNU General Lesser Public 
  License along with this program.  If not, see
  <http://www.gnu.org/licenses/lgpl-3.0.html>.
  #L%
  -->
 <%@ page contentType="text/html" pageEncoding="UTF-8" %>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
 
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>WebMotion</title>
        <meta name="description" content="">
        <meta name="author" content="">

        <link rel="icon" type="image/png" href="<c:url value="/img/favicon.png"/>">
        <link rel="shortcut icon" type="image/x-icon" href="<c:url value="/img/favicon.ico"/>">
        
        <!-- Le HTML5 shim, for IE6-8 support of HTML elements --> 
        <!--[if lt IE 9]>
          <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
        <![endif]--> 
        
        <!-- Le styles --> 
        <link rel="stylesheet" href="<c:url value="/bootstrap/css/bootstrap.css"/>">
        <link rel="stylesheet" href="<c:url value="/prettify/prettify.css"/>" type="text/css"/>
        <link rel="stylesheet" href="<c:url value="/css/style.css"/>">
        
        <script src="http://twitter.github.com/bootstrap/assets/js/jquery.js"></script> 
        <script type="text/javascript" src="<c:url value="/prettify/prettify.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/bootstrap/js/bootstrap.js"/>"></script>

        <style type="text/css">
            .tab-pane {
                clear: both;
                padding-left: 5px;
                padding-top: 10px;
            }
        </style>
        
        <script type="text/javascript">
            <c:url value="/main" var="main_url"/>
            <c:url value="/documentation" var="documentation_url"/>
            <c:url value="/showcase/hello" var="showcase_url"/>
            <c:url value="/download" var="download_url"/>
            <c:url value="/contacts" var="contacts_url"/>

            jQuery(document).ready(function () {
                $('#showcase').addClass("active");
                
                $("#main>a").attr("href", "${main_url}")
                $("#documentation>a").attr("href", "${documentation_url}")
                $("#showcase>a").attr("href", "${showcase_url}")
                $("#download>a").attr("href", "${download_url}")
                $("#contacts>a").attr("href", "${contacts_url}")
                
                var end = /[^/]+$/g;
                var id = '#' + end.exec(window.location.pathname)[0];
                $(id).addClass("label select");
                
                var tab = $(id).parents("div");
                tab.addClass("active");
                $('#tab_' + tab[0].id).addClass("active");
                
                var value = $(id).text();
                $('#subtitle').text(value);
            });
        </script>
    </head>

    <body onload="prettyPrint()">
                
        <div class="navbar navbar-fixed-top">
            <div class="navbar-inner">
                <div class="container-fluid">
                    <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse"> 
                        <span class="icon-bar"></span> 
                        <span class="icon-bar"></span> 
                        <span class="icon-bar"></span> 
                    </a> 
                    <a class="brand" href="<c:url value="/"/>">WebMotion</a>

                    <div class="nav-collapse">
                        <jsp:include page="/menu?sub=include" />
                        
                        <ul class="nav pull-right">
                            <li><a href="<c:url value="/?language=fr"/>">fr</a></li>
                            <li><a href="<c:url value="/?language=en"/>">en</a></li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>

        <div class="container-fluid">
            <div class="page-header page-header-icon" style="border: none;">
                <h1 style="background: url('<c:url value="/img/My_Computer.png"/>') no-repeat;">Démonstration <small><span id="subtitle"></span></small></h1>
            </div>

            <div class="row-fluid">
                <div class="span2 well" style="padding: 5px; margin-bottom: 10px; min-width: 200px;">
                    <ul class="nav nav-pills" style="margin: 0px; border-bottom: 1px solid #DEE6ED;">
                        <li id="tab_mapping"><a href="#mapping" data-toggle="tab">Mapping</a></li>
                        <li id="tab_render"><a href="#render" data-toggle="tab">Render</a></li>
                        <li id="tab_misc"><a href="#misc" data-toggle="tab">Misc</a></li>
                    </ul>

                    <div class="tab-content">
                        <div id="mapping" class="tab-pane fade in">
                            <ul>
                                <li><a id="hello" href="./hello">Hello world !</a></li>
                                <li><strong>Action</strong>
                                    <ul>
                                        <li><a id="act" href="./act">Action</a></li>
                                        <li><a id="view" href="./view">View</a></li>
                                        <li><a id="url" href="./url">Url</a></li>
                                        <li><a id="helloParameters" href="./helloParameters">Parameters</a></li>
                                        <li><a id="helloDefaultParameters" href="./helloDefaultParameters">Default parameters</a></li>
                                        <li><a id="pattern" href="./pattern">Pattern parameters</a></li>
                                        <li><a id="login" href="./login">Renamed parameters</a></li>
                                        <li><a id="select" href="./select">Parameters static</a></li>
                                        <li><a id="dynamic" href="./dynamic">Dynamic action</a></li>
                                        <li><a id="text" href="./text">Dynamic view</a></li>
                                        <li><a id="helloView" href="./helloView">Dynamic jsp view</a></li>
                                        <li><a id="wikipedia" href="./wikipedia">Dynamic url</a></li>
                                        <li><a id="form" href="./form">Method</a></li>
                                        <li><a id="media" href="./media">Multiple method</a></li>
                                        <li><a id="readme" href="./readme">Static resources</a></li>
                                    </ul>
                                </li>
                                <li><strong>Filter</strong>
                                    <ul>
                                        <li><a id="filter" href="./filter">Do process</a></li>
                                        <li><a id="condition" href="./condition">Render</a></li>
                                        <li><a id="decorator" href="./decorator">Default parameters</a> <span class="label label-info">New</span></li>
                                    </ul>
                                </li>
                                <li><strong>Error</strong>
                                    <ul>
                                        <li><a id="code" href="./code">Code http</a></li>
                                        <li><a id="exception" href="./exception">Exception</a></li>
                                        <li><a id="notfound" href="./notfound">All</a></li>
                                        <li><a id="npe" href="./npe">View</a></li>
                                    </ul>
                                </li>
                                <li><strong>Extension</strong>
                                    <ul>
                                        <li><a id="blog" href="./blog">Action</a></li>
                                        <li><a id="spring" href="./spring">Spring</a></li>
                                        <li><a id="stats" href="./stats">Pattern</a> <span class="label label-info">New</span></li>
                                    </ul>
                                </li>
                            </ul>
                        </div>
                        <div id="render" class="tab-pane fade in">
                            <ul>
                                <li><strong>Basic</strong>
                                    <ul>
                                        <li><a id="index" href="./index">View</a></li>
                                        <li><a id="helloModel" href="./helloModel">Model</a></li>
                                        <li><a id="save" href="./save">Url</a></li>
                                        <li><a id="first" href="./first">Action</a></li>
                                        <li><a id="internal" href="./internal">Action url</a> <span class="label label-info">New</span></li>
                                        <li><a id="content" href="./content">Content</a></li>
                                        <li><a id="stream" href="./stream">Stream</a></li>
                                        <li><a id="application" href="./application">Download</a> <span class="label label-info">New</span></li>
                                        <li><a id="load" href="./load">Reload page</a></li>
                                        <li><a id="forbidden" href="./forbidden">Error</a></li>
                                        <li><a id="nocontent" href="./nocontent">Status</a></li>
                                    </ul>
                                </li>
                                <li><strong>Data</strong>
                                    <ul>
                                        <li><a id="xml" href="./xml">XML</a></li>
                                        <li><a id="json" href="./json">JSON</a></li>
                                        <li><a id="jsonp" href="./jsonp">JSONP</a></li>
                                        <li><a id="template" href="./template">StringTemplate</a> <span class="label label-info">New</span></li>
                                    </ul>
                                </li>
                            </ul>
                        </div>
                        <div id="misc" class="tab-pane fade in">
                            <ul>
                                <li><strong>Action</strong>
                                    <ul>
                                        <li><a id="page" href="./page">Include</a></li>
                                        <li><a id="calc" href="./calc">AJAX</a></li>
                                        <li><a id="message" href="./message">Flash message</a></li>
                                        <li><a id="shop" href="./shop">Validation</a></li>
                                        <li><a id="async" href="./async">Async</a> <span class="label label-info">New</span></li>
                                        <li><a id="listener" href="./listener">Server listener</a> <span class="label label-info">New</span></li>
                                    </ul>
                                </li>
                                <li><strong>File</strong>
                                    <ul>
                                        <li><a id="file" href="./file">File</a></li>
                                        <li><a id="upload" href="./upload">File with progress</a></li>
                                    </ul>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>

                <div class="span9">
                    <ul class="nav nav-tabs">
                        <li class="active"><a href="#demo" data-toggle="tab">Démo</a></li>
                        <c:forEach var="file" items="${files}" varStatus="status">
                                <li><a href="#${status.index}" data-toggle="tab">${file.name}</a></li>
                        </c:forEach>
                    </ul>

                    <div class="tab-content">

                        <div class="tab-pane active fade in" id="demo">
                            <c:forEach var="path" items="${path_demo}">
                                <div class="alert alert-info" style="margin-bottom: 5px;">
                                    http://serverName:port/contextPath/<strong>${fn:substringAfter(path, '/showcase/action/')}</strong>
                                </div>
                                <iframe src="<c:url value="${path}"/>" style="margin-bottom: 20px; width: 100%; height: auto;background-color: #F4F7FB;border: 1px solid #DEE6ED;-webkit-border-radius: 3px;-moz-border-radius: 3px;border-radius: 3px;">
                                </iframe>
                            </c:forEach>
                        </div>

                        <c:forEach var="file" items="${files}" varStatus="status">
                            <div id="${status.index}" class="tab-pane fade in">
                                <div class="alert alert-info" style="margin-bottom: 5px;">
                                    <strong>Path :</strong> ${file.path}
                                </div>
                                <pre class="prettyprint" style="background-color: #F4F7FB;border: 1px solid #DEE6ED;padding: 8.5px;">
${file.content}
                                </pre>
                            </div>
                        </c:forEach>
                    </div>
                </div>

            </div>
        </div>
        
        <hr/>
        <footer style="text-align: center">
            <p>Powered by WikiMotion and WebMotion</p>
        </footer>

    </body>
</html>