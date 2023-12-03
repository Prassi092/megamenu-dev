package com.aem.megamenu.exercise.core.services.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class GetPageHierarchyDataServiceConfigTest {

    private GetPageHierarchyDataServiceConfig config = Mockito.mock(GetPageHierarchyDataServiceConfig.class);
    
    @Test
    void testHeaderMsg() {
        Mockito.when(config.headerMsg()).thenReturn("XYZ TELCO COMPANY");
        assertEquals("XYZ TELCO COMPANY", config.headerMsg());
    }

    @Test
    void testSourcePath() {
        Mockito.when(config.sourcePath()).thenReturn("/content/megamenu-exercise/en-root");
        assertEquals("/content/megamenu-exercise/en-root", config.sourcePath());
    }

    @Test
    void testHeaderMsg_Null() {
        Mockito.when(config.headerMsg()).thenReturn(null);
        assertNull(config.headerMsg());
    }

    @Test
    void testSourcePath_Null() {
        Mockito.when(config.sourcePath()).thenReturn(null);
        assertNull(config.sourcePath());
    }
}
