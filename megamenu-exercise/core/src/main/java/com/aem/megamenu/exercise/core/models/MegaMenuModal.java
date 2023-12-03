package com.aem.megamenu.exercise.core.models;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aem.megamenu.exercise.core.services.GetPageHierarchyDataService;
import java.util.Collections;

@Model(adaptables = SlingHttpServletRequest.class)
public class MegaMenuModal {

    @Inject
    private GetPageHierarchyDataService getPageHierarchyDataService;

    private static final Logger LOG = LoggerFactory.getLogger(MegaMenuModal.class);

    public String getHeaderMessage() {
        return getPageHierarchyDataService.getHeaderMsg();
    }

    public Map<String, List<PageDataModel>> getMap(){
        try {
            return getPageHierarchyDataService.getPageHierarchyData();
        } catch (LoginException e) {
            LOG.error("Login Exception Faced while trying to read the Page Hierarchy properties !");
        }
        return Collections.emptyMap();
    }
}
