package com.company.service;

import java.util.Arrays;

import org.kie.server.api.KieServerConstants;
import org.kie.server.api.model.KieContainerResource;
import org.kie.server.api.model.KieServerConfigItem;
import org.kie.server.api.model.ReleaseId;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    KieContainerResource evaluation() {
        return new KieContainerResource("evaluation", new ReleaseId("evaluation", "evaluation", "1.0"));
    }
    
    @Bean
    KieContainerResource evaluation2() {
        KieContainerResource container = new KieContainerResource("evaluation2", new ReleaseId("evaluation2", "evaluation2", "2.0"));
        container.setConfigItems(Arrays.asList(new KieServerConfigItem(KieServerConstants.PCFG_RUNTIME_STRATEGY, "PER_PROCESS_INSTANCE", "String")));
        return container;
    }
    
    @Bean
    KieContainerResource evaluation5() {
        KieContainerResource container = new KieContainerResource("evaluation5", new ReleaseId("evaluation5", "evaluation5", "5.0"));
        container.setConfigItems(Arrays.asList(new KieServerConfigItem(KieServerConstants.PCFG_RUNTIME_STRATEGY, "PER_PROCESS_INSTANCE", "String")));
        return container;
    }
}