package com.vector.model.google;

public class Link {
   private String text;
   private String href;
public String getText() {
    return text;
}
public void setText(String text) {
    this.text = text;
}
public String getHref() {
    return href;
}
public void setHref(String href) {
    this.href = href;
}
@Override
public String toString() {
    return "Link [href=" + href + ", text=" + text + "]";
}

   
}
