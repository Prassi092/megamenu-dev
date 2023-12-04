package com.aem.megamenu.exercise.core.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aem.megamenu.exercise.core.models.PageDataModel;
import com.aem.megamenu.exercise.core.services.GetPageHierarchyDataService;
import com.aem.megamenu.exercise.core.services.MegaMenuResourceResolverService;
import com.aem.megamenu.exercise.core.services.config.GetPageHierarchyDataServiceConfig;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageFilter;

/**
 * @inheritDoc
 */
@Component(service = GetPageHierarchyDataService.class, immediate = true, property = {
        "service.description=Get the underlying Page Hierarchy and its properties" } )
@Designate(ocd = GetPageHierarchyDataServiceConfig.class)
public class GetPageHierarchyDataServiceImpl implements GetPageHierarchyDataService {

    private GetPageHierarchyDataServiceConfig pageConfig;
    private static final Logger LOG = LoggerFactory.getLogger(GetPageHierarchyDataServiceImpl.class);

    public static final String HIDE_IN_MEGA_MENU = "hideInMegaMenu";
    public static final String HIDE_CHILDREN_IN_MEGA_MENU = "hideChildrenInMegaMenu";

    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    @Reference
    private MegaMenuResourceResolverService megaMenuResourceResolverService;

    @Activate
    @Modified
    public void configure(GetPageHierarchyDataServiceConfig config) {
        this.pageConfig = config;
    }

    @Override
    public Map<PageDataModel, List<PageDataModel>> getPageHierarchyData() throws LoginException {

        LOG.debug("Method Entry :: getPageHierarchyData of GetPageHierarchyDataServiceImpl class");

        //Formatting the Source Node..
        String sourcePath = getFormattedSourcePath();
        System.out.println("SourcePath is : "+ sourcePath);
        
        // Fetching the Resource Resolver
        ResourceResolver resourceResolver = megaMenuResourceResolverService.getMegaMenuResourceResolver();
        if(resourceResolver != null){
            Resource resource = resourceResolver.getResource(sourcePath);
            System.out.println("Resource is null ? " + (null == resource));
            if(resource != null){
                // Get the PageManager from the ResourceResolver
               // PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
               // System.out.println("pageManager is null ? " + (null == pageManager));
                //Page page = pageManager.getContainingPage(resource);
                Page page = resource.adaptTo(Page.class);
                System.out.println("Page is null ? " + (null == page));

                clearResolver(resourceResolver);

                LOG.debug("Method Exit :: getPageHierarchyData of GetPageHierarchyDataServiceImpl class");
                return buildPageHierarchyDataMap(page);

                
            }else{
                LOG.error("Unable to identify resource at :: {}", sourcePath);
                System.out.println("Res is null");
            }

            clearResolver(resourceResolver);
        }else{
            LOG.error("Unable to get a Resource Resolver");
        }

        LOG.debug("Method Exit :: getPageHierarchyData of GetPageHierarchyDataServiceImpl class");
        return Collections.emptyMap();
    }

    @Override
    public String getHeaderMsg() {
        return pageConfig.headerMsg();
    }

    /**
     * Method to release the Resolver
     * @param resourceResolver
     */
    private void clearResolver(ResourceResolver resourceResolver) {
        if(resourceResolver.isLive()){
            resourceResolver.close();
        }
    }

    /**
     * Method to return the formatted three level hierarchy of Root path..
     * @return Root Node path for Mega Menu
     */
    private String getFormattedSourcePath(){
        LOG.debug("Method Entry :: getFormattedSourcePath of GetPageHierarchyDataServiceImpl class");

        StringBuilder sourcePath = new StringBuilder();
        String rootPath = pageConfig.sourcePath();
        String[] pageLevels = rootPath.split("/");
        System.out.println("pageLevels : " + Arrays.toString(pageLevels));

        if(pageLevels.length > 3){
            for(int i=1;i<4;i++){
                sourcePath.append("/"+pageLevels[i]);
            }
        }else{
            LOG.info("Source path - {}, doesn't have enough Page levels to populate Mega Menu", rootPath);
            System.out.println("String unexpect");
            sourcePath = new StringBuilder(rootPath);
        }
        LOG.debug("Method Exit :: getFormattedSourcePath of GetPageHierarchyDataServiceImpl class");
        return sourcePath.toString() ;
    }
    
    /**
     * @param currentPage
     * @return
     */
    private boolean isHideInMegaMenu(Page currentPage){

        LOG.debug("Method Entry :: isHideInMegaMenu of GetPageHierarchyDataServiceImpl class");
        
        boolean isHide = false;

        ValueMap propertiesMap = currentPage.getProperties();
        if(propertiesMap.containsKey(HIDE_IN_MEGA_MENU)){
            isHide = Boolean.parseBoolean(propertiesMap.get(HIDE_IN_MEGA_MENU, String.class));
        }

        LOG.debug("Method Exit :: isHideInMegaMenu of GetPageHierarchyDataServiceImpl class");
        return isHide;
    }

    /**
     * @param currentPage
     * @return
     */
    private boolean isHideChildrenInMegaMenu(Page currentPage){

        LOG.debug("Method Entry :: isHideChildrenInMegaMenu of GetPageHierarchyDataServiceImpl class");
        
        boolean isHide = false;

        ValueMap propertiesMap = currentPage.getProperties();
        if(propertiesMap.containsKey(HIDE_CHILDREN_IN_MEGA_MENU)){
            isHide = Boolean.parseBoolean(propertiesMap.get(HIDE_CHILDREN_IN_MEGA_MENU, String.class));
        }

        LOG.debug("Method Exit :: isHideChildrenInMegaMenu of GetPageHierarchyDataServiceImpl class");
        return isHide;
    }

    /**
     * Method to build a Map of Hierarchy data for a given page..
     * 
     * @param page
     * @return Map of Paren to Childeren page
     */
    private Map<PageDataModel, List<PageDataModel>> buildPageHierarchyDataMap(Page page){
        LOG.debug("Method Entry :: buildPageHierarchyDataMap of GetPageHierarchyDataServiceImpl class");
        
        Map<PageDataModel, List<PageDataModel>> pageDataMap = new HashMap<>();

        // Getting an iterator of immediate child pages (depth = 1)
        Iterator<Page> childPages = page.listChildren(new PageFilter());
        while(childPages.hasNext()){

            // Pointing to first Menu page
            Page menuPage = childPages.next();

            PageDataModel menuModel = new PageDataModel();
            menuModel.setTitle(menuPage.getPageTitle());
            menuModel.setPath(menuPage.getPath());
            System.out.println("MENU ::  " + menuModel.toString());
            
            List<PageDataModel> subMenuList = new ArrayList<>();
                    
            
            // if(!isHideInMegaMenu(menuPage) && (!isHideChildrenInMegaMenu(menuPage))){
            //     pageDataMap.put(menuModel, getSubMenuModelList(menuPage, subMenuList));
            // }

            // Checking the boolean Page properties for MENU.. 
            if(!isHideInMegaMenu(menuPage)){

                //Populate Sub-menu only when Menu is false for isHideChildren property..
                pageDataMap.put(menuModel, 
                    (!isHideChildrenInMegaMenu(menuPage)) ? getSubMenuModelList(menuPage, subMenuList) : null);
            }
        }
        LOG.debug("Method Exit :: buildPageHierarchyDataMap of GetPageHierarchyDataServiceImpl class");
        return pageDataMap;
    }
    

    /**
     * Get a List of Sub Menu Models for a give Parent page.
     * @param menuPage
     * @param subMenuList
     * @return Sub-menu Model list.
     */
    private List<PageDataModel> getSubMenuModelList(Page menuPage, List<PageDataModel> subMenuList){

        LOG.debug("Method Entry :: getSubMenuModelList of GetPageHierarchyDataServiceImpl class");

        // List first level of Children nodes.
        Iterator<Page> subMenuPages = menuPage.listChildren(new PageFilter());
        while(subMenuPages.hasNext()){
            
            Page subMenuPage = subMenuPages.next();
            if(!isHideInMegaMenu(subMenuPage)){
                
                PageDataModel subMenuModel = new PageDataModel();
                subMenuModel.setTitle(subMenuPage.getPageTitle());
                subMenuModel.setPath(subMenuPage.getPath());
                System.out.println("SUB-MENU :: " + subMenuModel.toString());
                
                //Adding list of Sub-menu models..
                subMenuList.add(subMenuModel);
            }
        }

        LOG.debug("Method Exit :: getSubMenuModelList of GetPageHierarchyDataServiceImpl class");
        return subMenuList;

    }
}
