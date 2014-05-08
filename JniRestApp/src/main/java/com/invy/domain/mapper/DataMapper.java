/**
 * 
 */
package com.invy.domain.mapper;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.converter.ConverterFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.impl.generator.JavassistCompilerStrategy;
import ma.glasnost.orika.metadata.CaseInsensitiveClassMapBuilder;

import org.springframework.stereotype.Component;

/**
 * @author ema
 *
 */
@Component
public class DataMapper extends ConfigurableMapper {
	@Override
    public void configure(MapperFactory mapperFactory) {

        ConverterFactory converterFactory = mapperFactory.getConverterFactory();
       
    }

    public void configureFactoryBuilder(DefaultMapperFactory.Builder builder) {
        // Enforces Javassist compiler strategy; no need to change this
        builder.compilerStrategy(new JavassistCompilerStrategy());

        // Allows case-insensitve matching of names in the byDefault method
        builder.classMapBuilderFactory(new CaseInsensitiveClassMapBuilder.Factory());
    }
}
