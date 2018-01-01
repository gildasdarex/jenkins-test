package com.pej.services;

import org.springframework.stereotype.Component;


public interface NotificationService {
	void addInfoMessage(String msg);
    void addErrorMessage(String msg);
    void addWarningMessage(String msg);
    void addSucceesgMessage(String msg);
}
