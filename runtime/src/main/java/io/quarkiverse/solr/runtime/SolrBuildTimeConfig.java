package io.quarkiverse.solr.runtime;

import io.quarkus.runtime.annotations.ConfigGroup;
import io.quarkus.runtime.annotations.ConfigPhase;
import io.quarkus.runtime.annotations.ConfigRoot;
import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithDefault;

@ConfigGroup
@ConfigMapping(prefix = "quarkus.solr")
@ConfigRoot(phase = ConfigPhase.BUILD_AND_RUN_TIME_FIXED)
public interface SolrBuildTimeConfig {

    /**
     * Is solr health check enabled
     */
    @WithDefault("true")
    boolean healthEnabled();

    @Override
    String toString();
}
