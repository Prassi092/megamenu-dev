package com.aem.megamenu.exercise.core.services.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junit.framework.Assert;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class MegaMenuResourceResolverServiceImplTest {

    @InjectMocks
    MegaMenuResourceResolverServiceImpl resolveServiceMockImpl;

    @Mock
    ResourceResolverFactory resourceResolverFactory;

    @Mock
    ResourceResolver resourceResolver;

    Map<String, Object> param = new HashMap<>();

    @Test
    void testGetMegaMenuResourceResolver() throws LoginException {

        when(resourceResolverFactory.getServiceResourceResolver(any())).thenReturn(resourceResolver);
        Assert.assertNotNull(resolveServiceMockImpl.getMegaMenuResourceResolver());
    }

    @Test
    void testGetMegaMenuResourceResolver_exception() throws LoginException {
        Mockito.lenient().when(resourceResolverFactory.getServiceResourceResolver(any()))
                .thenThrow(new LoginException());
        Assertions.assertNull(resolveServiceMockImpl.getMegaMenuResourceResolver());

    }
}
