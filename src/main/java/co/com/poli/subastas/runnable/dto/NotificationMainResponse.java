/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.poli.subastas.runnable.dto;

import java.util.List;

/**
 *
 * @author 57304
 */
public class NotificationMainResponse {
    
    Integer multicastId;
    Integer success;
    Integer failure;
    Integer canonicalIds;
    List<Results> results;

    public NotificationMainResponse() {
    }

    public NotificationMainResponse(Integer multicastId, Integer success, Integer failure, Integer canonicalIds, List<Results> results) {
        this.multicastId = multicastId;
        this.success = success;
        this.failure = failure;
        this.canonicalIds = canonicalIds;
        this.results = results;
    }

    public Integer getMulticastId() {
        return multicastId;
    }

    public void setMulticastId(Integer multicastId) {
        this.multicastId = multicastId;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public Integer getFailure() {
        return failure;
    }

    public void setFailure(Integer failure) {
        this.failure = failure;
    }

    public Integer getCanonicalIds() {
        return canonicalIds;
    }

    public void setCanonicalIds(Integer canonicalIds) {
        this.canonicalIds = canonicalIds;
    }

    public List<Results> getResults() {
        return results;
    }

    public void setResults(List<Results> results) {
        this.results = results;
    }

    
    
}
