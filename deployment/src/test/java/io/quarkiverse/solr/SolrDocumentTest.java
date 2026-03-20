package io.quarkiverse.solr;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.UUID;

import jakarta.inject.Inject;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.request.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import io.quarkus.test.QuarkusUnitTest;

class SolrDocumentTest {
    @RegisterExtension
    static final QuarkusUnitTest unitTest = new QuarkusUnitTest()
            .setArchiveProducer(() -> ShrinkWrap.create(JavaArchive.class));

    @Inject
    SolrClient solrClient;

    @Test
    void indexDocument() throws SolrServerException, IOException {
        SolrInputDocument doc = new SolrInputDocument();
        doc.addField("id", UUID.randomUUID().toString());
        doc.addField("description", "Amazon Kindle Paperwhite");
        UpdateResponse updateResponse = solrClient.add("dummy", doc);
        UpdateResponse commitResponse = solrClient.commit("dummy");

        assertEquals(0, updateResponse.getStatus());
        assertEquals(0, commitResponse.getStatus());
    }

    @Test
    void queryDocument() throws SolrServerException, IOException {
        indexDocument();

        SolrQuery query = new SolrQuery("*:*");
        query.addField("id");
        query.addField("description");
        query.setSort("id", SolrQuery.ORDER.asc);
        QueryResponse response = solrClient.query("dummy", query);
        SolrDocumentList documents = response.getResults();

        assertEquals(1, documents.getNumFound());
        assertEquals(2, documents.get(0).size());
        assertEquals("Amazon Kindle Paperwhite", documents.get(0).getFirstValue("description"));
    }
}
