package io.quarkiverse.solr.deployment.devservices;

import org.testcontainers.solr.SolrContainer;

import io.quarkiverse.solr.runtime.SolrDevServicesConfig;

class SolrDevContainer extends SolrContainer {
    SolrDevContainer(String imageName, SolrDevServicesConfig config) {
        super(imageName);
        withReuse(true);
        withLabel(DevServiceProcessor.DEV_SERVICE_LABEL, "true");
        if (config.port().isPresent())
            addFixedExposedPort(SOLR_PORT, config.port().getAsInt());
    }

    public String solrUrl() {
        return "http://localhost:" + getSolrPort() + "/solr";
    }
}
