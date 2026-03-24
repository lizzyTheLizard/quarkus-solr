package io.quarkiverse.solr.runtime.observe;

import io.quarkus.runtime.metrics.MetricsFactory;
import org.apache.solr.client.solrj.SolrRequest;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class SolrMetrics {
    private Map<SolrRequest.SolrRequestType, RequestTypeMetrics> metricsMap;

    public void register(MetricsFactory metricsFactory) {
        metricsMap = Arrays.stream(SolrRequest.SolrRequestType.values())
                .collect(java.util.stream.Collectors.toMap(
                        type -> type,
                        type -> new RequestTypeMetrics(type, metricsFactory)));
    }

    public void updateSuccess(SolrRequest.SolrRequestType type, long timeTakenMillis) {
        if (metricsMap == null)
            return;
        metricsMap.get(type).updateSuccess(timeTakenMillis);
    }

    public void updateError(SolrRequest.SolrRequestType type) {
        if (metricsMap == null)
            return;
        metricsMap.get(type).updateError();
    }

    private static class RequestTypeMetrics {
        private final AtomicInteger successRequestCounter = new AtomicInteger(0);
        private final AtomicInteger errorRequestCounter = new AtomicInteger(0);
        private final MetricsFactory.TimeRecorder timeRecorder;

        private RequestTypeMetrics(SolrRequest.SolrRequestType type, MetricsFactory metricsFactory) {
            metricsFactory.builder("solr.request." + type)
                    .description("Number of successfully " + type + " requests to solr")
                    .unit("requests")
                    .buildCounter(successRequestCounter::get);
            metricsFactory.builder("solr.error." + type)
                    .description("Number of failed " + type + " requests to solr")
                    .unit("requests")
                    .buildCounter(errorRequestCounter::get);
            timeRecorder = metricsFactory.builder("solr.time." + type)
                    .description("Time taken for " + type + " requests")
                    .unit("milliseconds")
                    .buildTimer();
        }

        void updateSuccess(long timeTakenMillis) {
            timeRecorder.update(timeTakenMillis, TimeUnit.MILLISECONDS);
            successRequestCounter.incrementAndGet();
        }

        void updateError() {
            errorRequestCounter.incrementAndGet();
        }

    }
}
