package com.atipera.githubRepositoryViewerApp.exceptions;

public class UrlIsUnavailableException extends RuntimeException {
    public UrlIsUnavailableException(String message) {
        super(message);
    }
}
