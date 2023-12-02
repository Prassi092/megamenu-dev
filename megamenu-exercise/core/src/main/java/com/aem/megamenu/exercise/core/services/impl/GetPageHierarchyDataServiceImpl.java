package com.aem.megamenu.exercise.core.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.Designate;

import com.aem.megamenu.exercise.core.models.PageDataModel;
import com.aem.megamenu.exercise.core.services.GetPageHierarchyDataService;

/**
 * @inheritDoc
 */
@Component(service = GetPageHierarchyDataService.class, immediate = true, property = {
        "service.description=Get the underlying Page Hierarchy and its properties" } )
//@Designate(ocd = GetPageHierarchyDataServiceImpl.Config.class)
public class GetPageHierarchyDataServiceImpl implements GetPageHierarchyDataService {

    @Override
    public Map<String, List<PageDataModel>> getPageHierarchyData() {

        PageDataModel subMenu1_1 = new PageDataModel();
        subMenu1_1.setTitle("Sub-Menu 1.1");
        subMenu1_1.setPath("/content/sg/en/x1/yy");
        PageDataModel subMenu1_2 = new PageDataModel();
        subMenu1_2.setTitle("Sub-Menu 1.2");
        subMenu1_2.setPath("/content/sg/en/x1/zz");

        PageDataModel subMenu2_1 = new PageDataModel();
        subMenu2_1.setTitle("Sub-Menu 2.1");
        subMenu2_1.setPath("/content/sg/en/x2/yy");
        PageDataModel subMenu2_2 = new PageDataModel();
        subMenu2_2.setTitle("Sub-Menu 2.2");
        subMenu2_2.setPath("/content/sg/en/x2/zz");

        List<PageDataModel> SubMenuList1 = new ArrayList<PageDataModel>();
        SubMenuList1.add(subMenu1_1);
        SubMenuList1.add(subMenu1_2);

        List<PageDataModel> SubMenuList2 = new ArrayList<PageDataModel>();
        SubMenuList2.add(subMenu2_1);
        SubMenuList2.add(subMenu2_2);
        
        // Returning Mock static data for testing..
        Map<String, List<PageDataModel>> pageDataMap = new HashMap<>();

        pageDataMap.put("Test Menu1", SubMenuList1);
        pageDataMap.put("Test Menu1", SubMenuList2);

        return pageDataMap;
    }

    @Override
    public String testString() {
        return "Test Text from GetPageHierarchyDataServiceImpl";
    }
    
}
