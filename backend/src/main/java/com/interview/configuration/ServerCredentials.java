package com.interview.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@Setter
@Getter
public class ServerCredentials {
    @Value("${security.webservice-name}")
    private String webserviceName;

    @Value("${security.webservice-password}")
    private String webservicePassword;
}
