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

    public Map<String, List<PageDataModel>> getPageHierarchyData() throws LoginException;

    public String testString();

}