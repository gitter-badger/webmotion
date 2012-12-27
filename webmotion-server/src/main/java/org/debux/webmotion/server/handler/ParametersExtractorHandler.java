/*
 * #%L
 * Webmotion server
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

import org.debux.webmotion.server.call.Call;
import org.debux.webmotion.server.mapping.ActionRule;
import org.debux.webmotion.server.mapping.FilterRule;
import org.debux.webmotion.server.mapping.Mapping;
import org.debux.webmotion.server.mapping.FragmentUrl;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.debux.webmotion.server.WebMotionHandler;
import org.debux.webmotion.server.WebMotionUtils;
import org.debux.webmotion.server.call.Call.ParameterTree;
import org.debux.webmotion.server.call.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Extract parameter in request, to map name on request and name on define in 
 * mapping.
 * 
 * @author julien
 */
public class ParametersExtractorHandler extends AbstractHandler implements WebMotionHandler {

    private static final Logger log = LoggerFactory.getLogger(ParametersExtractorHandler.class);

    @Override
    public void handle(Mapping mapping, Call call) {
        
        // Not action found in extension ?
        ActionRule actionRule = (ActionRule) call.getRule();
        if (actionRule == null) {
            return;
        }
        
        // Contains all parameters
        Map<String, Object> tmp = new LinkedHashMap<String, Object>();
        ParameterTree parameterTree = call.getParameterTree();
        
        // Add default parameters
        List<FilterRule> filterRules = call.getFilterRules();
        for (FilterRule filterRule : filterRules) {
            Map<String, String[]> defaultParameters = filterRule.getDefaultParameters();
            tmp.putAll(defaultParameters);
        }
        
        Map<String, String[]> defaultParameters = actionRule.getDefaultParameters();
        tmp.putAll(defaultParameters);
        
        // Add extract parameters
        Map<String, Object> extractParameters = call.getExtractParameters();
        tmp.putAll(extractParameters);
        
        // Retrieve the good name for parameters give in mapping
        HttpContext context = call.getContext();
        String url = context.getUrl();
        List<String> path = WebMotionUtils.splitPath(url);
        
        List<FragmentUrl> ruleUrl = actionRule.getRuleUrl();
        int position = 0;
        for (FragmentUrl expression : ruleUrl) {
            String name = expression.getName();
            
            if (!StringUtils.isEmpty(name)) {
                String value = path.get(position);
                
                String[] currentValues = (String[]) tmp.get(name);
                if (currentValues == null) {
                    tmp.put(name, new String[]{value});
                } else {
                    tmp.put(name, ArrayUtils.add(currentValues, value));
                }
            }
            position ++;
        }
        
        List<FragmentUrl> ruleParameters = actionRule.getRuleParameters();
        for (FragmentUrl expression : ruleParameters) {
            String name = expression.getName();
            String param = expression.getParam();
            
            if(!StringUtils.isEmpty(name)) {
                String[] values = (String[]) extractParameters.get(param);
                if (values != null) {
                    
                    String[] currentValues = (String[]) tmp.get(name);
                    if (currentValues == null) {
                        tmp.put(name, values);
                    } else {
                        tmp.put(name, ArrayUtils.addAll(currentValues, values));
                    }
                    tmp.put(name + "." + param, values);
                }
            }
        }
        
        // Transform dot by map
        Map<String, ParameterTree> tree = parameterTree.getTree();
        for (Map.Entry<String, Object> entry : tmp.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            
            // Manage object.property=value
            Map<String, ParameterTree> map = tree;
            
            String[] split = key.split("\\.");
            
            for (position = 0; position < split.length; position++) {

                String name = split[position];
                ParameterTree next = map.get(name);
                if (next == null) {
                    next = new ParameterTree();
                    map.put(name, next);
                }
                    
                if (position == split.length - 1) {
                    next.setValue(value);
                    
                } else {
                    map = next.getTree();
                    if (map == null) {
                        map = new LinkedHashMap<String, ParameterTree>();
                        next.setTree(map);
                    }
                }
            }
        }
    }
}
