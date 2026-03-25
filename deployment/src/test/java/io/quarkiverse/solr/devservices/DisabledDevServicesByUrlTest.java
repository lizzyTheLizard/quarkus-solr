package io.quarkiverse.solr.devservices;

import io.quarkus.test.QuarkusUnitTest;
import jakarta.inject.Inject;
import org.apache.solr.client.solrj.SolrClient;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import java.net.ConnectException;

import static org.junit.jupiter.api.Assertions.assertThrows;

class DisabledDevServicesByUrlTest {
    @RegisterExtension
    static final QuarkusUnitTest unitTest = new QuarkusUnitTest()
            .overrideConfigKey("quarkus.solr.url", "http://localhost:8080")
            .setArchiveProducer(() -> ShrinkWrap.create(JavaArchive.class));

    @Inject
    SolrClient solrClient;

    @Test
    void containerNotStarted() {
        assertThrows(ConnectException.class, () -> solrClient.ping("dummy"));
    }
}
