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
import seis.stthomas.edu.domain.Queen;
import seis.stthomas.edu.domain.QueenDataOnDemand;
import seis.stthomas.edu.domain.QueenIntegrationTest;

privileged aspect QueenIntegrationTest_Roo_IntegrationTest {
    
    declare @type: QueenIntegrationTest: @RunWith(SpringJUnit4ClassRunner.class);
    
    declare @type: QueenIntegrationTest: @ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml");
    
    declare @type: QueenIntegrationTest: @Transactional;
    
    @Autowired
    private QueenDataOnDemand QueenIntegrationTest.dod;
    
    @Test
    public void QueenIntegrationTest.testCountQueens() {
        Assert.assertNotNull("Data on demand for 'Queen' failed to initialize correctly", dod.getRandomQueen());
        long count = Queen.countQueens();
        Assert.assertTrue("Counter for 'Queen' incorrectly reported there were no entries", count > 0);
    }
    
    @Test
    public void QueenIntegrationTest.testFindQueen() {
        Queen obj = dod.getRandomQueen();
        Assert.assertNotNull("Data on demand for 'Queen' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Queen' failed to provide an identifier", id);
        obj = Queen.findQueen(id);
        Assert.assertNotNull("Find method for 'Queen' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'Queen' returned the incorrect identifier", id, obj.getId());
    }
    
    @Test
    public void QueenIntegrationTest.testFindAllQueens() {
        Assert.assertNotNull("Data on demand for 'Queen' failed to initialize correctly", dod.getRandomQueen());
        long count = Queen.countQueens();
        Assert.assertTrue("Too expensive to perform a find all test for 'Queen', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<Queen> result = Queen.findAllQueens();
        Assert.assertNotNull("Find all method for 'Queen' illegally returned null", result);
        Assert.assertTrue("Find all method for 'Queen' failed to return any data", result.size() > 0);
    }
    
    @Test
    public void QueenIntegrationTest.testFindQueenEntries() {
        Assert.assertNotNull("Data on demand for 'Queen' failed to initialize correctly", dod.getRandomQueen());
        long count = Queen.countQueens();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<Queen> result = Queen.findQueenEntries(firstResult, maxResults);
        Assert.assertNotNull("Find entries method for 'Queen' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'Queen' returned an incorrect number of entries", count, result.size());
    }
    
    @Test
    public void QueenIntegrationTest.testFlush() {
        Queen obj = dod.getRandomQueen();
        Assert.assertNotNull("Data on demand for 'Queen' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Queen' failed to provide an identifier", id);
        obj = Queen.findQueen(id);
        Assert.assertNotNull("Find method for 'Queen' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyQueen(obj);
        Integer currentVersion = obj.getVersion();
        obj.flush();
        Assert.assertTrue("Version for 'Queen' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void QueenIntegrationTest.testMergeUpdate() {
        Queen obj = dod.getRandomQueen();
        Assert.assertNotNull("Data on demand for 'Queen' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Queen' failed to provide an identifier", id);
        obj = Queen.findQueen(id);
        boolean modified =  dod.modifyQueen(obj);
        Integer currentVersion = obj.getVersion();
        Queen merged = (Queen)obj.merge();
        obj.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'Queen' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void QueenIntegrationTest.testPersist() {
        Assert.assertNotNull("Data on demand for 'Queen' failed to initialize correctly", dod.getRandomQueen());
        Queen obj = dod.getNewTransientQueen(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'Queen' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'Queen' identifier to be null", obj.getId());
        obj.persist();
        obj.flush();
        Assert.assertNotNull("Expected 'Queen' identifier to no longer be null", obj.getId());
    }
    
    @Test
    public void QueenIntegrationTest.testRemove() {
        Queen obj = dod.getRandomQueen();
        Assert.assertNotNull("Data on demand for 'Queen' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Queen' failed to provide an identifier", id);
        obj = Queen.findQueen(id);
        obj.remove();
        obj.flush();
        Assert.assertNull("Failed to remove 'Queen' with identifier '" + id + "'", Queen.findQueen(id));
    }
    
}
