package com.aem.megamenu.exercise.core.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.tagext.PageData;

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
import com.day.cq.wcm.api.PageManager;

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
    public Map<String, List<PageDataModel>> getPageHierarchyData() throws LoginException {

        LOG.debug("Method Entry :: getPageHierarchyData of GetPageHierarchyDataServiceImpl class");
        Map<String, List<PageDataModel>> pageDataMap = new HashMap<>();

        String sourcePath = "";
        String rootPath = pageConfig.sourcePath();
        String[] pageLevels = rootPath.split("/");

        if(pageLevels.length > 2){
            for(int i=0;i<3;i++){
                sourcePath += "/"+pageLevels[i];
            }
        }else{
            LOG.info("Source path - {}, doesn't have enough Page levels to populate Mega Menu", rootPath);
        }

        // Find the Resource using the Source path..
        ResourceResolver resourceResolver  = megaMenuResourceResolverService.getMegaMenuResourceResolver();
        if(resourceResolver != null){
            Resource resource = resourceResolver.getResource(sourcePath);
            if(resource != null){
                // Get the PageManager from the ResourceResolver
                PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
                Page page = pageManager.getContainingPage(resource);

                // Get an iterator of immediate child pages (depth = 1)
                Iterator<Page> childPages = page.listChildren(new PageFilter());

                while(childPages.hasNext()){

                    Page menuPage = childPages.next();
                    List<PageDataModel> SubMenuList = new ArrayList<PageDataModel>();
                    
                    // If page config is allowed,
                    if(!isHideInMegaMenu(menuPage) && (!isHideChildrenInMegaMenu(menuPage))){

                            Iterator<Page> subMenuPages = menuPage.listChildren(new PageFilter());
                            while(subMenuPages.hasNext()){

                                Page subMenuPage = subMenuPages.next();
                                if(!isHideInMegaMenu(subMenuPage)){

                                    PageDataModel subMenuModel = new PageDataModel();
                                    subMenuModel.setTitle(subMenuPage.getPageTitle());
                                    subMenuModel.setPath(subMenuPage.getPath());

                                    SubMenuList.add(subMenuModel);
                                }
                            }
                    }
                    pageDataMap.put(menuPage.getTitle(), SubMenuList);
                }
            }else{
                LOG.info("Unable to identify resource at :: {}", sourcePath);
            }
        }else{
            LOG.info("Unable to get a Resource Resolver");
        }


        

        // PageDataModel subMenu1_1 = new PageDataModel();
        // subMenu1_1.setTitle("Sub-Menu 1.1");
        // subMenu1_1.setPath("/content/sg/en/x1/yy");
        // PageDataModel subMenu1_2 = new PageDataModel();
        // subMenu1_2.setTitle("Sub-Menu 1.2");
        // subMenu1_2.setPath("/content/sg/en/x1/zz");

        // PageDataModel subMenu2_1 = new PageDataModel();
        // subMenu2_1.setTitle("Sub-Menu 2.1");
        // subMenu2_1.setPath("/content/sg/en/x2/yy");
        // PageDataModel subMenu2_2 = new PageDataModel();
        // subMenu2_2.setTitle("Sub-Menu 2.2");
        // subMenu2_2.setPath("/content/sg/en/x2/zz");

        // List<PageDataModel> SubMenuList1 = new ArrayList<PageDataModel>();
        // SubMenuList1.add(subMenu1_1);
        // SubMenuList1.add(subMenu1_2);

        // List<PageDataModel> SubMenuList2 = new ArrayList<PageDataModel>();
        // SubMenuList2.add(subMenu2_1);
        // SubMenuList2.add(subMenu2_2);
        
        // // Returning Mock static data for testing..
        // Map<String, List<PageDataModel>> pageDataMap = new HashMap<>();

        // pageDataMap.put("Test Menu1", SubMenuList1);
        // pageDataMap.put("Test Menu1", SubMenuList2);

        LOG.debug("Method Exit :: getPageHierarchyData of GetPageHierarchyDataServiceImpl class");
        return pageDataMap;
    }

    @Override
    public String testString() {
        return "Test Text from GetPageHierarchyDataServiceImpl";
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
    
}
