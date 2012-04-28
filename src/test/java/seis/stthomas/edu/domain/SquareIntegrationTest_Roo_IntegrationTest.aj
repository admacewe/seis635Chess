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
import seis.stthomas.edu.domain.Square;
import seis.stthomas.edu.domain.SquareDataOnDemand;
import seis.stthomas.edu.domain.SquareIntegrationTest;

privileged aspect SquareIntegrationTest_Roo_IntegrationTest {
    
    declare @type: SquareIntegrationTest: @RunWith(SpringJUnit4ClassRunner.class);
    
    declare @type: SquareIntegrationTest: @ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml");
    
    declare @type: SquareIntegrationTest: @Transactional;
    
    @Autowired
    private SquareDataOnDemand SquareIntegrationTest.dod;
    
    @Test
    public void SquareIntegrationTest.testCountSquares() {
        Assert.assertNotNull("Data on demand for 'Square' failed to initialize correctly", dod.getRandomSquare());
        long count = Square.countSquares();
        Assert.assertTrue("Counter for 'Square' incorrectly reported there were no entries", count > 0);
    }
    
    @Test
    public void SquareIntegrationTest.testFindSquare() {
        Square obj = dod.getRandomSquare();
        Assert.assertNotNull("Data on demand for 'Square' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Square' failed to provide an identifier", id);
        obj = Square.findSquare(id);
        Assert.assertNotNull("Find method for 'Square' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'Square' returned the incorrect identifier", id, obj.getId());
    }
    
    @Test
    public void SquareIntegrationTest.testFindAllSquares() {
        Assert.assertNotNull("Data on demand for 'Square' failed to initialize correctly", dod.getRandomSquare());
        long count = Square.countSquares();
        Assert.assertTrue("Too expensive to perform a find all test for 'Square', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<Square> result = Square.findAllSquares();
        Assert.assertNotNull("Find all method for 'Square' illegally returned null", result);
        Assert.assertTrue("Find all method for 'Square' failed to return any data", result.size() > 0);
    }
    
    @Test
    public void SquareIntegrationTest.testFindSquareEntries() {
        Assert.assertNotNull("Data on demand for 'Square' failed to initialize correctly", dod.getRandomSquare());
        long count = Square.countSquares();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<Square> result = Square.findSquareEntries(firstResult, maxResults);
        Assert.assertNotNull("Find entries method for 'Square' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'Square' returned an incorrect number of entries", count, result.size());
    }
    
    @Test
    public void SquareIntegrationTest.testFlush() {
        Square obj = dod.getRandomSquare();
        Assert.assertNotNull("Data on demand for 'Square' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Square' failed to provide an identifier", id);
        obj = Square.findSquare(id);
        Assert.assertNotNull("Find method for 'Square' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifySquare(obj);
        Integer currentVersion = obj.getVersion();
        obj.flush();
        Assert.assertTrue("Version for 'Square' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void SquareIntegrationTest.testMergeUpdate() {
        Square obj = dod.getRandomSquare();
        Assert.assertNotNull("Data on demand for 'Square' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Square' failed to provide an identifier", id);
        obj = Square.findSquare(id);
        boolean modified =  dod.modifySquare(obj);
        Integer currentVersion = obj.getVersion();
        Square merged = obj.merge();
        obj.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'Square' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void SquareIntegrationTest.testPersist() {
        Assert.assertNotNull("Data on demand for 'Square' failed to initialize correctly", dod.getRandomSquare());
        Square obj = dod.getNewTransientSquare(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'Square' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'Square' identifier to be null", obj.getId());
        obj.persist();
        obj.flush();
        Assert.assertNotNull("Expected 'Square' identifier to no longer be null", obj.getId());
    }
    
    @Test
    public void SquareIntegrationTest.testRemove() {
        Square obj = dod.getRandomSquare();
        Assert.assertNotNull("Data on demand for 'Square' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Square' failed to provide an identifier", id);
        obj = Square.findSquare(id);
        obj.remove();
        obj.flush();
        Assert.assertNull("Failed to remove 'Square' with identifier '" + id + "'", Square.findSquare(id));
    }
    
}
