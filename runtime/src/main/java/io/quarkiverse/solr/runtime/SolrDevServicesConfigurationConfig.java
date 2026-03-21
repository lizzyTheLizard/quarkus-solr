package io.quarkiverse.solr.runtime;

public interface SolrDevServicesConfigurationConfig {
    /**
     * The configuration name to be used
     * <p>
     * Can only be used together with configurationFile() and schemaFile()
     */
    String name();

    /**
     * The solrconfig.xml file to be used
     * <p>
     * Can only be used together with configurationName() and schemaFile()
     * Must be a valid path to a file on the classpath and a valid SOLR configuration file
     */
    String solrconfig();

    /**
     * The schema.xml file to be used
     * <p>
     * Can only be used together with configurationName() and configurationFile()
     * Must be a valid path to a file on the classpath and a valid SOLR schema file
     */
    String schema();

    @Override
    String toString();
}
