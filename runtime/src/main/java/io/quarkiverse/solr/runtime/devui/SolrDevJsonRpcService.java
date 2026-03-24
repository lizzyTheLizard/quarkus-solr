package io.quarkiverse.solr.runtime.devui;

import io.quarkiverse.solr.runtime.SolrRunTimeConfig;
import io.smallrye.common.annotation.NonBlocking;

public class SolrDevJsonRpcService {
    private final SolrRunTimeConfig solrRunTimeConfig;

    public SolrDevJsonRpcService(SolrRunTimeConfig solrRunTimeConfig) {
        this.solrRunTimeConfig = solrRunTimeConfig;
    }

    @NonBlocking
    public String getSolrAdminUrl() {
        return solrRunTimeConfig.url().get(0);
    }
}
