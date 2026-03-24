package io.quarkiverse.solr.deployment.observe;

import io.quarkiverse.solr.runtime.observe.SolrMetrics;
import io.quarkus.builder.item.SimpleBuildItem;

public final class MetricsBuildItem extends SimpleBuildItem {
    private final SolrMetrics metrics;

    public MetricsBuildItem() {
        this.metrics = new SolrMetrics();
    }

    public SolrMetrics getMetrics() {
        return metrics;
    }
}
