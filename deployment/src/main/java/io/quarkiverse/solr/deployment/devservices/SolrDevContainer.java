package io.quarkiverse.solr.deployment.devservices;

import java.net.URL;

import org.testcontainers.solr.SolrContainer;

import io.quarkiverse.solr.runtime.SolrDevServicesConfig;

class SolrDevContainer extends SolrContainer {
    SolrDevContainer(String imageName, SolrDevServicesConfig config) {
        super(imageName);
        withReuse(true);
        withLabel(DevServiceProcessor.DEV_SERVICE_LABEL, "true");
        config.port().ifPresent(port -> addFixedExposedPort(SOLR_PORT, port));
        config.collection().ifPresent(this::withCollection);
        config.configuration().ifPresent(c -> {
            URL solrconfigUrl = this.getClass().getClassLoader().getResource(c.solrconfig());
            if(solrconfigUrl == null) throw new RuntimeException("File " + c.solrconfig() + " cannot be found in the classpath. Please set a valid path to the property quarkus.solr.devservices.configuration.solrconfig");
            withConfiguration(c.name(), solrconfigUrl);
            URL schemaUrl = this.getClass().getClassLoader().getResource(c.schema());
            if(schemaUrl == null) throw new RuntimeException("File " + c.schema() + " cannot be found in the classpath. Please set a valid path to the property quarkus.solr.devservices.configuration.schema");
            withSchema(schemaUrl);
        });
    }

    public String solrUrl() {
        return "http://localhost:" + getSolrPort() + "/solr";
    }
}
