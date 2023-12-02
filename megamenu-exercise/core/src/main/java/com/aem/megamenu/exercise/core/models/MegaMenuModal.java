package com.aem.megamenu.exercise.core.models;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;

import com.aem.megamenu.exercise.core.services.GetPageHierarchyDataService;

@Model(adaptables = SlingHttpServletRequest.class)
public class MegaMenuModal {

    @Inject
    private GetPageHierarchyDataService getPageHierarchyDataService;

    public String testData() {
        return getPageHierarchyDataService.testString();
    }

    public Map<String, List<PageDataModel>> getPageHierarchyData(){
        return getPageHierarchyDataService.getPageHierarchyData();
    }
}
