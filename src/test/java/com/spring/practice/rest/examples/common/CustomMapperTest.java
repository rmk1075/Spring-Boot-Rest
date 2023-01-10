package com.spring.practice.rest.examples.common;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.spring.practice.rest.examples.domain.mapperEx.A;
import com.spring.practice.rest.examples.domain.mapperEx.Dest;
import com.spring.practice.rest.examples.domain.mapperEx.Source;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class CustomMapperTest {
    
    @Autowired
    CustomMapper mapstructMapper;

    private final ModelMapper modelMapper = new ModelMapper();

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    @Test
    public void testMapstructMapper() throws ParseException {
        Source source = new Source();
        source.setId("testId");
        source.setName("testName");
        source.setSource("value");

        A value = new A();
        value.setValue("value");
        source.setValue(value);

        Date date = new Date();
        source.setDate(date);

        System.out.println(source.toString());

        Dest dest = mapstructMapper.sourceToDest(source);
        System.out.println(dest.toString());

        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);

        assertEquals(source.getId(), dest.getId());
        assertEquals(source.getName(), dest.getName());
        assertEquals(source.getValue().getValue(), dest.getValue().getValue());
        assertEquals(source.getDate().toString(), format.parse(dest.getDateStr()).toString());
        
        source = mapstructMapper.destToSource(dest);
        System.out.println(source.toString());
        
        assertEquals(source.getId(), dest.getId());
        assertEquals(source.getName(), dest.getName());
        assertEquals(source.getValue().getValue(), dest.getValue().getValue());
        assertEquals(source.getDate().toString(), format.parse(dest.getDateStr()).toString());
    }

    @Test
    public void testModelMapper() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        modelMapper.typeMap(Source.class, Dest.class).addMappings(
            mapper -> {
                mapper.map(src -> src.getSource(), Dest::setDest);
                mapper.using(ctx -> format.format(ctx.getSource())).map(src -> src.getDate(), Dest::setDateStr);
            }
        );

        Source source = new Source();
        source.setId("testId");
        source.setName("testName");
        source.setSource("value");

        A value = new A();
        value.setValue("value");
        source.setValue(value);

        Date date = new Date();
        source.setDate(date);

        System.out.println(source.toString());
        System.out.println(source.getDate().toString());

        Dest dest = modelMapper.map(source, Dest.class);

        System.out.println(dest.toString());
        System.out.println(dest.getDateStr());

        assertEquals(source.getId(), dest.getId());
        assertEquals(source.getName(), dest.getName());
        assertEquals(source.getValue().getValue(), dest.getValue().getValue());
        assertEquals(source.getDate().toString(), format.parse(dest.getDateStr()).toString());
        
        Converter<String, Date> converter = ctx -> {
            try {
                return format.parse(ctx.getSource());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;
        };
        modelMapper.typeMap(Dest.class, Source.class).addMappings(
            mapper -> {
                mapper.map(dst -> dst.getDest(), Source::setSource);
                mapper.using(converter).map(dst -> dst.getDateStr(), Source::setDate);
            }
        );

        source = modelMapper.map(dest, Source.class);
        System.out.println(source.toString());
        
        assertEquals(source.getId(), dest.getId());
        assertEquals(source.getName(), dest.getName());
        assertEquals(source.getValue().getValue(), dest.getValue().getValue());
        assertEquals(source.getDate().toString(), format.parse(dest.getDateStr()).toString());
    }
}
