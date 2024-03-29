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
import seis.stthomas.edu.domain.BoardDataOnDemand;
import seis.stthomas.edu.domain.BoardIntegrationTest;
import seis.stthomas.edu.service.BoardService;

privileged aspect BoardIntegrationTest_Roo_IntegrationTest {
    
    declare @type: BoardIntegrationTest: @RunWith(SpringJUnit4ClassRunner.class);
    
    declare @type: BoardIntegrationTest: @ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml");
    
    declare @type: BoardIntegrationTest: @Transactional;
    
    @Autowired
    private BoardDataOnDemand BoardIntegrationTest.dod;
    
    @Autowired
    BoardService BoardIntegrationTest.boardService;
    
    @Test
    public void BoardIntegrationTest.testCountAllBoards() {
        Assert.assertNotNull("Data on demand for 'Board' failed to initialize correctly", dod.getRandomBoard());
        long count = boardService.countAllBoards();
        Assert.assertTrue("Counter for 'Board' incorrectly reported there were no entries", count > 0);
    }
    
    @Test
    public void BoardIntegrationTest.testFindBoard() {
        Board obj = dod.getRandomBoard();
        Assert.assertNotNull("Data on demand for 'Board' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Board' failed to provide an identifier", id);
        obj = boardService.findBoard(id);
        Assert.assertNotNull("Find method for 'Board' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'Board' returned the incorrect identifier", id, obj.getId());
    }
    
    @Test
    public void BoardIntegrationTest.testFindAllBoards() {
        Assert.assertNotNull("Data on demand for 'Board' failed to initialize correctly", dod.getRandomBoard());
        long count = boardService.countAllBoards();
        Assert.assertTrue("Too expensive to perform a find all test for 'Board', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<Board> result = boardService.findAllBoards();
        Assert.assertNotNull("Find all method for 'Board' illegally returned null", result);
        Assert.assertTrue("Find all method for 'Board' failed to return any data", result.size() > 0);
    }
    
    @Test
    public void BoardIntegrationTest.testFindBoardEntries() {
        Assert.assertNotNull("Data on demand for 'Board' failed to initialize correctly", dod.getRandomBoard());
        long count = boardService.countAllBoards();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<Board> result = boardService.findBoardEntries(firstResult, maxResults);
        Assert.assertNotNull("Find entries method for 'Board' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'Board' returned an incorrect number of entries", count, result.size());
    }
    
    @Test
    public void BoardIntegrationTest.testFlush() {
        Board obj = dod.getRandomBoard();
        Assert.assertNotNull("Data on demand for 'Board' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Board' failed to provide an identifier", id);
        obj = boardService.findBoard(id);
        Assert.assertNotNull("Find method for 'Board' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyBoard(obj);
        Integer currentVersion = obj.getVersion();
        obj.flush();
        Assert.assertTrue("Version for 'Board' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void BoardIntegrationTest.testUpdateBoardUpdate() {
        Board obj = dod.getRandomBoard();
        Assert.assertNotNull("Data on demand for 'Board' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Board' failed to provide an identifier", id);
        obj = boardService.findBoard(id);
        boolean modified =  dod.modifyBoard(obj);
        Integer currentVersion = obj.getVersion();
        Board merged = boardService.updateBoard(obj);
        obj.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'Board' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void BoardIntegrationTest.testSaveBoard() {
        Assert.assertNotNull("Data on demand for 'Board' failed to initialize correctly", dod.getRandomBoard());
        Board obj = dod.getNewTransientBoard(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'Board' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'Board' identifier to be null", obj.getId());
        boardService.saveBoard(obj);
        obj.flush();
        Assert.assertNotNull("Expected 'Board' identifier to no longer be null", obj.getId());
    }
    
    @Test
    public void BoardIntegrationTest.testDeleteBoard() {
        Board obj = dod.getRandomBoard();
        Assert.assertNotNull("Data on demand for 'Board' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Board' failed to provide an identifier", id);
        obj = boardService.findBoard(id);
        boardService.deleteBoard(obj);
        obj.flush();
        Assert.assertNull("Failed to remove 'Board' with identifier '" + id + "'", boardService.findBoard(id));
    }
    
}
