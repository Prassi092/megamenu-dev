package com.aem.megamenu.exercise.core.services.config;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name = "Get Page Hierarchy Data Service Config")
public @interface GetPageHierarchyDataServiceConfig {
    
    @AttributeDefinition(name="Mega Menu Source Path",
        description = "The Source Resource Path from which Mega Menu component forms the Menu & Sub-menu")
    String sourcePath() default "/content/megamenu-exercise/en-root";

    @AttributeDefinition(name="Mega Menu Header Message",
        description = "Message to populate on top of the Mega Menu component")
    String headerMsg() default "XYZ TELCO COMPANY";

}
