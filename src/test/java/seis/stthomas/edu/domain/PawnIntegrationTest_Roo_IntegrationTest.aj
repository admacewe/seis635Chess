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
import seis.stthomas.edu.domain.Pawn;
import seis.stthomas.edu.domain.PawnDataOnDemand;
import seis.stthomas.edu.domain.PawnIntegrationTest;

privileged aspect PawnIntegrationTest_Roo_IntegrationTest {
    
    declare @type: PawnIntegrationTest: @RunWith(SpringJUnit4ClassRunner.class);
    
    declare @type: PawnIntegrationTest: @ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml");
    
    declare @type: PawnIntegrationTest: @Transactional;
    
    @Autowired
    private PawnDataOnDemand PawnIntegrationTest.dod;
    
    @Test
    public void PawnIntegrationTest.testCountPawns() {
        Assert.assertNotNull("Data on demand for 'Pawn' failed to initialize correctly", dod.getRandomPawn());
        long count = Pawn.countPawns();
        Assert.assertTrue("Counter for 'Pawn' incorrectly reported there were no entries", count > 0);
    }
    
    @Test
    public void PawnIntegrationTest.testFindPawn() {
        Pawn obj = dod.getRandomPawn();
        Assert.assertNotNull("Data on demand for 'Pawn' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Pawn' failed to provide an identifier", id);
        obj = Pawn.findPawn(id);
        Assert.assertNotNull("Find method for 'Pawn' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'Pawn' returned the incorrect identifier", id, obj.getId());
    }
    
    @Test
    public void PawnIntegrationTest.testFindAllPawns() {
        Assert.assertNotNull("Data on demand for 'Pawn' failed to initialize correctly", dod.getRandomPawn());
        long count = Pawn.countPawns();
        Assert.assertTrue("Too expensive to perform a find all test for 'Pawn', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<Pawn> result = Pawn.findAllPawns();
        Assert.assertNotNull("Find all method for 'Pawn' illegally returned null", result);
        Assert.assertTrue("Find all method for 'Pawn' failed to return any data", result.size() > 0);
    }
    
    @Test
    public void PawnIntegrationTest.testFindPawnEntries() {
        Assert.assertNotNull("Data on demand for 'Pawn' failed to initialize correctly", dod.getRandomPawn());
        long count = Pawn.countPawns();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<Pawn> result = Pawn.findPawnEntries(firstResult, maxResults);
        Assert.assertNotNull("Find entries method for 'Pawn' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'Pawn' returned an incorrect number of entries", count, result.size());
    }
    
    @Test
    public void PawnIntegrationTest.testFlush() {
        Pawn obj = dod.getRandomPawn();
        Assert.assertNotNull("Data on demand for 'Pawn' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Pawn' failed to provide an identifier", id);
        obj = Pawn.findPawn(id);
        Assert.assertNotNull("Find method for 'Pawn' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyPawn(obj);
        Integer currentVersion = obj.getVersion();
        obj.flush();
        Assert.assertTrue("Version for 'Pawn' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void PawnIntegrationTest.testMergeUpdate() {
        Pawn obj = dod.getRandomPawn();
        Assert.assertNotNull("Data on demand for 'Pawn' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Pawn' failed to provide an identifier", id);
        obj = Pawn.findPawn(id);
        boolean modified =  dod.modifyPawn(obj);
        Integer currentVersion = obj.getVersion();
        Pawn merged = (Pawn)obj.merge();
        obj.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'Pawn' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void PawnIntegrationTest.testPersist() {
        Assert.assertNotNull("Data on demand for 'Pawn' failed to initialize correctly", dod.getRandomPawn());
        Pawn obj = dod.getNewTransientPawn(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'Pawn' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'Pawn' identifier to be null", obj.getId());
        obj.persist();
        obj.flush();
        Assert.assertNotNull("Expected 'Pawn' identifier to no longer be null", obj.getId());
    }
    
    @Test
    public void PawnIntegrationTest.testRemove() {
        Pawn obj = dod.getRandomPawn();
        Assert.assertNotNull("Data on demand for 'Pawn' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Pawn' failed to provide an identifier", id);
        obj = Pawn.findPawn(id);
        obj.remove();
        obj.flush();
        Assert.assertNull("Failed to remove 'Pawn' with identifier '" + id + "'", Pawn.findPawn(id));
    }
    
}