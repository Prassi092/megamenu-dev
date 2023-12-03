package com.aem.megamenu.exercise.core.services;

import java.util.List;
import java.util.Map;

import org.apache.sling.api.resource.LoginException;

import com.aem.megamenu.exercise.core.models.PageDataModel;

/**
 * Service to get a Page's Hierarchy Related details..
 *
 * Created on December 02, 2023
 *
 * @author Prasanth
 */
public interface GetPageHierarchyDataService {

    /**
     * Method to get the Page HierarchyData from the configured Root Node, to form the Mega Menu options..
     * @return Map of Page Hierarchy data
     * @throws LoginException
     */
    public Map<PageDataModel, List<PageDataModel>> getPageHierarchyData() throws LoginException;

    /**
     * Method to get the configured 
     * Header String of the Mega Menu component..
     * @return Header message
     */
    public String getHeaderMsg();

}