package com.aem.megamenu.exercise.core.services;

import java.util.List;
import java.util.Map;

import com.aem.megamenu.exercise.core.models.PageDataModel;

/**
 * Service to get a Page's Hierarchy Related details..
 *
 * Created on December 02, 2023
 *
 * @author Prasanth
 */
public interface GetPageHierarchyDataService {

    Map<String, List<PageDataModel>> getPageHierarchyData();

    String testString();

}