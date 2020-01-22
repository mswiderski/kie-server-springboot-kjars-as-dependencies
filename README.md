# business application with kjars defined as dependencies

This application illustrates how to use business application (kie server with spring boot)
to avoid need of maven repository for kjars on runtime. Instead kjars are defined as application
dependencies and thus loaded on runtime directly from classpath

There are several requirements to allow this to work properly

1. Produce `KieContainerResource` as spring beans that will allow to deploy given kjar

```java
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
```

2. Configure kmodule.xml with the same id as container id of the produced `KieContainerResource`

```xml
<kmodule xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://jboss.org/kie/6.0.0/kmodule">
  <kbase name="evaluation" default="false" eventProcessingMode="cloud" equalsBehavior="identity" packages="*">
    <ksession name="evaluation_1s" type="stateful" default="false"/>
  </kbase>
</kmodule>
```

```xml
<kmodule xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://jboss.org/kie/6.0.0/kmodule">
  <kbase name="evaluation2" default="false" eventProcessingMode="cloud" equalsBehavior="identity" packages="*">
    <ksession name="evaluation_2_s" type="stateful" default="false"/>
  </kbase>
</kmodule>
```

3. Declare kjars as dependencies of your business application

```xml
<dependency>
  <groupId>evaluation</groupId>
  <artifactId>evaluation</artifactId>
  <version>1.0.0-SNAPSHOT</version>
</dependency>

<dependency>
  <groupId>evaluation</groupId>
  <artifactId>evaluation2</artifactId>
  <version>1.0.0-SNAPSHOT</version>
</dependency>
```


NOTE: Since these are maven dependencies you must use different group or artifact ids.
Having different version won't work as only one will be taken... usually the latest.
