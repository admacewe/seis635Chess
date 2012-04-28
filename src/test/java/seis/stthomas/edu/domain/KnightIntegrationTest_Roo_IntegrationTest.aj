// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package seis.stthomas.edu.domain;

import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import seis.stthomas.edu.domain.Knight;
import seis.stthomas.edu.domain.KnightDataOnDemand;
import seis.stthomas.edu.domain.KnightIntegrationTest;

privileged aspect KnightIntegrationTest_Roo_IntegrationTest {
    
    declare @type: KnightIntegrationTest: @RunWith(SpringJUnit4ClassRunner.class);
    
    declare @type: KnightIntegrationTest: @ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml");
    
    declare @type: KnightIntegrationTest: @Transactional;
    
    @Autowired
    private KnightDataOnDemand KnightIntegrationTest.dod;
    
    @Test
    public void KnightIntegrationTest.testCountKnights() {
        Assert.assertNotNull("Data on demand for 'Knight' failed to initialize correctly", dod.getRandomKnight());
        long count = Knight.countKnights();
        Assert.assertTrue("Counter for 'Knight' incorrectly reported there were no entries", count > 0);
    }
    
    @Test
    public void KnightIntegrationTest.testFindKnight() {
        Knight obj = dod.getRandomKnight();
        Assert.assertNotNull("Data on demand for 'Knight' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Knight' failed to provide an identifier", id);
        obj = Knight.findKnight(id);
        Assert.assertNotNull("Find method for 'Knight' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'Knight' returned the incorrect identifier", id, obj.getId());
    }
    
    @Test
    public void KnightIntegrationTest.testFindAllKnights() {
        Assert.assertNotNull("Data on demand for 'Knight' failed to initialize correctly", dod.getRandomKnight());
        long count = Knight.countKnights();
        Assert.assertTrue("Too expensive to perform a find all test for 'Knight', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<Knight> result = Knight.findAllKnights();
        Assert.assertNotNull("Find all method for 'Knight' illegally returned null", result);
        Assert.assertTrue("Find all method for 'Knight' failed to return any data", result.size() > 0);
    }
    
    @Test
    public void KnightIntegrationTest.testFindKnightEntries() {
        Assert.assertNotNull("Data on demand for 'Knight' failed to initialize correctly", dod.getRandomKnight());
        long count = Knight.countKnights();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<Knight> result = Knight.findKnightEntries(firstResult, maxResults);
        Assert.assertNotNull("Find entries method for 'Knight' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'Knight' returned an incorrect number of entries", count, result.size());
    }
    
    @Test
    public void KnightIntegrationTest.testFlush() {
        Knight obj = dod.getRandomKnight();
        Assert.assertNotNull("Data on demand for 'Knight' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Knight' failed to provide an identifier", id);
        obj = Knight.findKnight(id);
        Assert.assertNotNull("Find method for 'Knight' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyKnight(obj);
        Integer currentVersion = obj.getVersion();
        obj.flush();
        Assert.assertTrue("Version for 'Knight' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void KnightIntegrationTest.testMergeUpdate() {
        Knight obj = dod.getRandomKnight();
        Assert.assertNotNull("Data on demand for 'Knight' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Knight' failed to provide an identifier", id);
        obj = Knight.findKnight(id);
        boolean modified =  dod.modifyKnight(obj);
        Integer currentVersion = obj.getVersion();
        Knight merged = (Knight)obj.merge();
        obj.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'Knight' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void KnightIntegrationTest.testPersist() {
        Assert.assertNotNull("Data on demand for 'Knight' failed to initialize correctly", dod.getRandomKnight());
        Knight obj = dod.getNewTransientKnight(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'Knight' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'Knight' identifier to be null", obj.getId());
        obj.persist();
        obj.flush();
        Assert.assertNotNull("Expected 'Knight' identifier to no longer be null", obj.getId());
    }
    
    @Test
    public void KnightIntegrationTest.testRemove() {
        Knight obj = dod.getRandomKnight();
        Assert.assertNotNull("Data on demand for 'Knight' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Knight' failed to provide an identifier", id);
        obj = Knight.findKnight(id);
        obj.remove();
        obj.flush();
        Assert.assertNull("Failed to remove 'Knight' with identifier '" + id + "'", Knight.findKnight(id));
    }
    
}
