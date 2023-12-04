# MegaMenu Component Development

This is a small project to implement a Simple MegaMenu component for all the pages in an Application, which is constructed by mapping the hierary of the Pages from a given level, as the Menu & Sub-Menu of the component.

## Modules

The main parts of the template are:

* core: Java bundle containing all core functionality like OSGi services, listeners or schedulers, as well as component-related Java code such as servlets or request filters.
* it.tests: Java based integration tests
* ui.apps: contains the /apps (and /etc) parts of the project, ie JS&CSS clientlibs, components, and templates
* ui.content: contains sample content using the components from the ui.apps
* ui.config: contains runmode specific OSGi configs for the project
* ui.frontend: an optional dedicated front-end build mechanism (Angular, React or general Webpack project)
* ui.tests: Selenium based UI tests
* all: a single content package that embeds all of the compiled modules (bundles and content packages) including any vendor dependencies
* analyse: this module runs analysis on the project which provides additional validation for deploying into AEMaaCS

## Some of the noteable Files in this repo are : 

| File | Property |
| --- | --- |
|1) megamenu.html|UI - Rendering Script for Component.|
|`2) megamenu.js`|Clientlib to facilitate browser event listeners and handlers.|
|`3) megamenu.less`|Basic Styling for component.|
|`4) PageDataModel.java`|Modal class for Page data.|
|`5) MegaMenuModal.java`|Sling Model to invoke OSGi Services.|
|`6) GetPageHierarchyDataServiceConfig.java`|Object Class Definition for OSGI config of Service class.|
|`7) GetPageHierarchyDataServiceImpl.java`|OSGi component that provide Page Hierarchy data.|
|`8) MegaMenuResourceResolverServiceImpl.java`|Provides Service Resource Resolver.|


## How to build

To build all the modules run in the project root directory the following command with Maven 3:

    mvn clean install

To build all the modules and deploy the `all` package to a local instance of AEM, run in the project root directory the following command:

    mvn clean install -PautoInstallSinglePackage


## The following Page Hierarchy is followed as Test Data

| MENU | SUB-MENU | PATH | CONTROL PROPERTY |
| --- | --- | --- | --- |
|`Menu-1`||/content/megamenu-exercise/en/x1||
||`Sub Menu 1-1`|/content/megamenu-exercise/en/x1/xx||
||`Sub Menu 1-2`|/content/megamenu-exercise/en/x2/yy||
||`Sub Menu 1-3`|/content/megamenu-exercise/en/x2/zz||
|`Menu-2`||/content/megamenu-exercise/en/x2||
||`Sub Menu 2-1`|/content/megamenu-exercise/en/x2/xx||
||`Sub Menu 2-2`|/content/megamenu-exercise/en/x2/yy|hideInMegaMenu: true|
|`Menu-3`||/content/megamenu-exercise/en/x3|hideChildrenInMegaMenu:true|
||`Sub Menu 3-1`|/content/megamenu-exercise/en/x3/xx||

## CACHING STRATERGY :

* The component, Payload pages & Experience Fragments are enabled to cache on the Dispatcher caching rule.
* The Dispatcher's invalidate rules are set to invalidate after any update or activation.
* The "Sling Dynamic Include" are not in the scope of this Repo, due to "Dynamic Include" bundle's dependency. However, some pointers to include the INCLUDE directory are given.


Developed on : `AEM Version 6.5.14.0`
