package com.kaan.jakarta.hello;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class HelloWorldResourceTest {
    @InjectMocks
    private HelloWorldResource helloResource;

    @Test
    public void givenHelloWithNameWhenCallHelloEndpointThenShouldReturnHelloName() {
        // Test when a name is provided
        Hello response = helloResource.hello("Alice");
        assertNotNull(response);
        assertEquals("Alice", response.getHello(), "The name should be Alice when passed as a parameter");
    }

    @Test
    public void givenHelloWithNoNameWhenCallHelloEndpointThenShouldReturnHelloWorld() {
        // Test when no name is provided (should default to "world")
        Hello response = helloResource.hello(null);
        assertNotNull(response);
        assertEquals("world", response.getHello(), "The name should default to 'world' when no parameter is provided");

        // Test when an empty name is provided
        response = helloResource.hello(" ");
        assertNotNull(response);
        assertEquals("world", response.getHello(), "The name should default to 'world' when an empty string is provided");
    }
  
}