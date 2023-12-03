package com.aem.megamenu.exercise.core.services.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aem.megamenu.exercise.core.services.MegaMenuResourceResolverService;

/**
 * @inheritDoc
 */
@Component(service = MegaMenuResourceResolverService.class, immediate = true)
public class MegaMenuResourceResolverServiceImpl implements MegaMenuResourceResolverService{

    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    private static final Logger LOG = LoggerFactory.getLogger(MegaMenuResourceResolverServiceImpl.class);

    @Override
    public ResourceResolver getMegaMenuResourceResolver() {
        LOG.debug("Method Entry :: getMegaMenuResourceResolver of MegaMenuResourceResolverServiceImpl class");
       
        ResourceResolver resourceResolver = null;
        try {

            Map<String, Object> param = new HashMap<>();
            param.put(ResourceResolverFactory.SUBSERVICE, "componentAccessSubService");
            resourceResolver = resourceResolverFactory.getServiceResourceResolver(param);
            
        } catch (LoginException e) {
            LOG.error("Exception occurred while getting resource resolver for Keystore User : {}",
                    e.getMessage());
        }

        LOG.debug("Method Exit :: getMegaMenuResourceResolver of MegaMenuResourceResolverServiceImpl class");
        return resourceResolver;
        
    }
    
}
