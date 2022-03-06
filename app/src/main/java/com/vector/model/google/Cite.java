package com.vector.model.google;

public class Cite {
    private String domain;
    private String span;
    public String getDomain() {
        return domain;
    }
    public void setDomain(String domain) {
        this.domain = domain;
    }
    public String getSpan() {
        return span;
    }
    public void setSpan(String span) {
        this.span = span;
    }
    @Override
    public String toString() {
        return "Cite [domain=" + domain + ", span=" + span + "]";
    }

    
}
