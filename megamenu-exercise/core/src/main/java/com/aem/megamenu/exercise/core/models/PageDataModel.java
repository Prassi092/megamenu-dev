package com.aem.megamenu.exercise.core.models;

/**
 * Sling Model for PageData for MegaMenu component's consumption..
 *
 * Created on December 02 2023
 *
 * @author Prasanth
 */
public class PageDataModel {

    String title;
    String path;

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }
    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }
    /**
     * @return the path
     */
    public String getPath() {
        return path;
    }
    /**
     * @param path the path to set
     */
    public void setPath(String path) {
        this.path = path;
    }
    @Override
    public String toString(){
        return "PageDataModel [ title=" + getTitle() + ", path=" +getPath()+ "]";
    }
}
