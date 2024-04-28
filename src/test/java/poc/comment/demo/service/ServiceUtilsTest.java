package poc.comment.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import poc.comment.demo.model.ParsedLong;

public class ServiceUtilsTest {

    @Test
    public void notValidLongTest(){

        ServiceUtils serviceUtils = new ServiceUtils();

        ParsedLong result = serviceUtils.validateLong("no-numerico", "TestVar");

        assertNotNull(result);
        assertFalse(result.isSuccess());
        assertEquals("TestVar no valido", result.getErrorMessage());

    }

    @Test
    public void negativeLongTest(){

        ServiceUtils serviceUtils = new ServiceUtils();

        ParsedLong result = serviceUtils.validateLong("-20", "TestVar");

        assertNotNull(result);
        assertFalse(result.isSuccess());
        assertEquals("TestVar no puede ser negativo", result.getErrorMessage());

    }

    @Test
    public void validLongTest(){

        ServiceUtils serviceUtils = new ServiceUtils();

        ParsedLong result = serviceUtils.validateLong("10", "TestVar");

        assertNotNull(result);
        assertTrue(result.isSuccess());
        assertEquals(10L, result.getResultLong());

    }
}
