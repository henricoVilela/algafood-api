package com.study.algafood.core.jackson;

import java.io.IOException;

import org.springframework.boot.jackson.JsonComponent;
import org.springframework.data.domain.Page;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

@JsonComponent
public class PageJsonSerializer extends JsonSerializer<Page<?>>{

	@Override
	public void serialize(Page<?> value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		
		//{
		gen.writeStartObject();
		
			//"content":[{}],
			gen.writeObjectField("content", value.getContent());
			
			//"size": ?,
			gen.writeNumberField("size", value.getSize());
			
			//"totalElements": ?,
			gen.writeNumberField("totalElements", value.getTotalElements());
			
			//"totalPages": ?,
			gen.writeNumberField("totalPages", value.getTotalPages());
			
			//"number": ?
			gen.writeNumberField("number", value.getNumber());
		
		//}
		gen.writeEndObject();
		
	}

}
