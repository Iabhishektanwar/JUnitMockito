package com.Java.JUnitMockito.ModelMapper;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class EntityDtoConverter {
	
	private static final Logger logger = LoggerFactory.getLogger(EntityDtoConverter.class);
	
	@Autowired
	private ModelMapper modelMapper;
	
	public <E, D> D convertToDto(E entity, Class<D> dtoClass) {
        logger.info("EntityDtoConverter :: Inside convertToDto");
        return modelMapper.map(entity, dtoClass);
    }

    public <D, E> E convertToEntity(D dto, Class<E> entityClass) {
        logger.info("EntityDtoConverter :: Inside convertToEntity");
        return modelMapper.map(dto, entityClass);
    }

}
