package com.pej.pojo;

import java.util.List;

/**
 * Created by darextossa on 7/26/17.
 */
public class FilterResult {

    private Object data;
    private List<FilterModel> copyFilterModel;
    private List<FilterModel> originalFilterModel;
    private int nextPage;
    private Long total;
    private int previousPage;
    private int currentPage;
    private String criteriaParameter;
    private int[] listOfPages;

    public FilterResult() {
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public List<FilterModel> getCopyFilterModel() {
        return copyFilterModel;
    }

    public void setCopyFilterModel(List<FilterModel> copyFilterModel) {
        this.copyFilterModel = copyFilterModel;
    }

    public List<FilterModel> getOriginalFilterModel() {
        return originalFilterModel;
    }

    public void setOriginalFilterModel(List<FilterModel> originalFilterModel) {
        this.originalFilterModel = originalFilterModel;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public int getPreviousPage() {
        return previousPage;
    }

    public void setPreviousPage(int previousPage) {
        this.previousPage = previousPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public String getCriteriaParameter() {
        return criteriaParameter;
    }

    public void setCriteriaParameter(String criteriaParameter) {
        this.criteriaParameter = criteriaParameter;
    }

    public int[] getListOfPages() {
        return listOfPages;
    }

    public void setListOfPages(int[] listOfPages) {
        this.listOfPages = listOfPages;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
