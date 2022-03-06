package com.vector.model.google;

import java.util.Arrays;

public class Google {
    private Result[] results;
    private ImageResult[] image_results;
    private long total;
    private String[] answers;
    private double ts;
    private String device_region;
    private String device_type;
    
    public Result[] getResults() {
        return results;
    }

    public ImageResult[] getImage_results() {
        return image_results;
    }

    public void setImage_results(ImageResult[] image_results) {
        this.image_results = image_results;
    }

    public double getTs() {
        return ts;
    }

    public void setTs(double ts) {
        this.ts = ts;
    }

    public String getDevice_region() {
        return device_region;
    }

    public void setDevice_region(String device_region) {
        this.device_region = device_region;
    }

    public String getDevice_type() {
        return device_type;
    }

    public void setDevice_type(String device_type) {
        this.device_type = device_type;
    }

    public String[] getAnswers() {
        return answers;
    }

    public void setAnswers(String[] answers) {
        this.answers = answers;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }


    public void setResults(Result[] results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "Google [answers=" + Arrays.toString(answers) + ", device_region=" + device_region + ", device_type="
                + device_type + ", imageResult=" + Arrays.toString(image_results) + ", results="
                + Arrays.toString(results) + ", total=" + total + ", ts=" + ts + "]";
    }

    
}
