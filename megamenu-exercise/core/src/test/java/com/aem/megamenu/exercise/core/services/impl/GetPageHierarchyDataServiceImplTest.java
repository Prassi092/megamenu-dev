package com.aem.megamenu.exercise.core.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.aem.megamenu.exercise.core.models.PageDataModel;
import com.aem.megamenu.exercise.core.services.MegaMenuResourceResolverService;
import com.aem.megamenu.exercise.core.services.config.GetPageHierarchyDataServiceConfig;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class GetPageHierarchyDataServiceImplTest {

    @InjectMocks
    GetPageHierarchyDataServiceImpl pageHierarchyServiceImpl;

    @Mock
    GetPageHierarchyDataServiceConfig mockConfig;

    @Mock
    MegaMenuResourceResolverService mockResolverService;

    @Mock
    ResourceResolver mockResolver;

    public final AemContext context = new AemContext(ResourceResolverType.JCR_MOCK);

    @BeforeEach
    public void setUp() throws Exception{
        context.load().json("/sourcepage.json", "/content/megamenu-exercise/en-root");
        //context.load().json("/com/aem/megamenu/exercise/core/resources/sourcepage.json", "/content/megamenu-exercise/en-root");
    }

    @Test
    void testConfigure() {
        pageHierarchyServiceImpl.configure(mockConfig);
        assertNotNull(mockConfig);
    }

    @Test
    void testGetHeaderMsg() {
        when(mockConfig.headerMsg()).thenReturn("Test Component Message");
        assertNotNull(pageHierarchyServiceImpl.getHeaderMsg());
    }

    @Test
    void testGetPageHierarchyData() throws LoginException {
        
        Resource sourcePageResource = context.resourceResolver().getResource("/content/megamenu-exercise/en-root");

        when(mockConfig.sourcePath()).thenReturn("/content/megamenu-exercise/en-root");
        when(mockResolverService.getMegaMenuResourceResolver()).thenReturn(mockResolver);
        when(mockResolver.getResource("/content/megamenu-exercise/en-root")).thenReturn(sourcePageResource);

        Map<PageDataModel, List<PageDataModel>> resultPageMap = pageHierarchyServiceImpl.getPageHierarchyData();
        assertEquals(3, resultPageMap.size());

        for(PageDataModel menuModel : resultPageMap.keySet()){
            if(menuModel.getTitle() == "Menu Page-1"){
                // Sub-Menu count is 2, since they are not hidden by Boolean props..
                assertEquals(2, resultPageMap.get(menuModel).size());
                assertEquals("Sub Menu 1-1", resultPageMap.get(menuModel).get(0).getTitle());
                assertEquals("Sub Menu 1-2", resultPageMap.get(menuModel).get(1).getTitle());
            }else if (menuModel.getTitle() == "Menu Page-2"){
                assertEquals("Menu Page-2", menuModel.getTitle());
                // Sub-Menu count is 1, since Sub-Menu 2.2 has "hideInNav" property..
                assertEquals(1, resultPageMap.get(menuModel).size());
                assertEquals("Sub Menu 2-1", resultPageMap.get(menuModel).get(0).getTitle());
            }else if (menuModel.getTitle() == "Menu Page-3"){
                assertEquals("Menu Page-3", menuModel.getTitle());
                // Sub-Menu is Null Since Menu hold "hideAllChildren Prop"
                assertNull(resultPageMap.get(menuModel));
            }
        }
    }

    @Test
    void testGetPageHierarchyDataNullResolver() throws LoginException {
        when(mockConfig.sourcePath()).thenReturn("/content/megamenu-exercise/en-root");
        when(mockResolverService.getMegaMenuResourceResolver()).thenReturn(null);
        assertEquals(0, pageHierarchyServiceImpl.getPageHierarchyData().size());
    }

    @Test
    void testGetPageHierarchyDataWrongPathAndNullResource() throws LoginException {
        when(mockConfig.sourcePath()).thenReturn("/content/invalid-path");
        when(mockResolverService.getMegaMenuResourceResolver()).thenReturn(mockResolver);
        when(mockResolver.getResource("/content/invalid-path")).thenReturn(null);
        assertEquals(0, pageHierarchyServiceImpl.getPageHierarchyData().size());
    }
}
