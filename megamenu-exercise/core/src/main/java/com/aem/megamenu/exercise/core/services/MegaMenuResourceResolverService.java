package com.aem.megamenu.exercise.core.services;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
/**
 * Service to deal with resource resolver resolution
 *
 * Created on December 2, 2023
 *
 * @author Prasanth
 */
public interface MegaMenuResourceResolverService {
    
    /**
     * Method to provide the Service Resource Resolver,
     * for the component specific SubService with access to resources.
     * @return Service Resource Resolver
     * @throws LoginException
     */
    ResourceResolver getMegaMenuResourceResolver() throws LoginException;
}
