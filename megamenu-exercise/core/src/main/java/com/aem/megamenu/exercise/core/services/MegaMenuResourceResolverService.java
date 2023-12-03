package com.aem.megamenu.exercise.core.services;

import org.apache.sling.api.resource.ResourceResolver;
/**
 * Service to deal with resource resolver resolution
 *
 * Created on December 2, 2023
 *
 * @author Prasanth
 */
public interface MegaMenuResourceResolverService {
    
    ResourceResolver getMegaMenuResourceResolver();
}
