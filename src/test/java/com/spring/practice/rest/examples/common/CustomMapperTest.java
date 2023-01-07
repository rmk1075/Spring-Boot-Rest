package com.spring.practice.rest.examples.common;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.spring.practice.rest.examples.domain.mapperEx.Dest;
import com.spring.practice.rest.examples.domain.mapperEx.Source;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class CustomMapperTest {
    
    @Autowired
    CustomMapper mapper;

    @Test
    public void testCustomMapper() {
        Source source = new Source();
        source.setId("testId");
        source.setName("testName");

        Dest dest = mapper.sourceToDest(source);

        assertEquals(source.getId(), dest.getId());
        assertEquals(source.getName(), dest.getName());
        
        source = mapper.destToSource(dest);
        
        assertEquals(source.getId(), dest.getId());
        assertEquals(source.getName(), dest.getName());
    }
}
