package com.vector.model.google;

import java.util.Arrays;

public class Result {
    private String title;
    private String link;
    private String description;
    private Link[] additional_links;
    private Cite cite;

    public Link[] getAdditional_links() {
        return additional_links;
    }
    public void setAdditional_links(Link[] additional_links) {
        this.additional_links = additional_links;
    }
    
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getLink() {
        return link;
    }
    public void setLink(String link) {
        this.link = link;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    
    public Cite getCite() {
        return cite;
    }
    public void setCite(Cite cite) {
        this.cite = cite;
    }
    @Override
    public String toString() {
        return "Result [additionalLinks=" + Arrays.toString(additional_links) + ", cite=" + cite + ", description="
                + description + ", link=" + link + ", title=" + title + "]";
    }

    
    
}
