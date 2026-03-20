package io.quarkiverse.solr;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import jakarta.inject.Inject;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.request.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import io.quarkus.test.QuarkusUnitTest;

class SolrBeanTest {
    @RegisterExtension
    static final QuarkusUnitTest unitTest = new QuarkusUnitTest()
            .setArchiveProducer(() -> ShrinkWrap.create(JavaArchive.class).addClass(SolrBean.class));

    @Inject
    SolrClient solrClient;

    @Test
    void indexDocument() throws SolrServerException, IOException {
        SolrBean solrBean = new SolrBean(UUID.randomUUID().toString(), "Amazon Kindle 2");
        UpdateResponse updateResponse = solrClient.addBean("dummy", solrBean);
        UpdateResponse commitResponse = solrClient.commit("dummy");

        assertEquals(0, updateResponse.getStatus());
        assertEquals(0, commitResponse.getStatus());
    }

    @Test
    void queryDocument() throws SolrServerException, IOException {
        indexDocument();

        SolrQuery query = new SolrQuery("*:*");
        query.addField("*");
        query.setSort("id", SolrQuery.ORDER.asc);
        QueryResponse response = solrClient.query("dummy", query);
        List<SolrBean> documents = response.getBeans(SolrBean.class);

        assertEquals(1, documents.size());
        assertEquals("Amazon Kindle 2", documents.get(0).name);
    }
}
