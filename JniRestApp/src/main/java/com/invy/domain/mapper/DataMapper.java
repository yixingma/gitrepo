/**
 * 
 */
package com.invy.domain.mapper;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import ma.glasnost.orika.CustomConverter;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.converter.ConverterFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.impl.generator.JavassistCompilerStrategy;
import ma.glasnost.orika.metadata.CaseInsensitiveClassMapBuilder;
import ma.glasnost.orika.metadata.Type;

import org.springframework.stereotype.Component;

import com.invy.database.jpa.data.Kit;
import com.invy.database.jpa.data.Requestimage;
import com.invy.database.jpa.data.Requestmaster;
import com.invy.database.jpa.data.Subkit;
import com.invy.endpoint.RegisterNewKitRequest;

/**
 * @author ema
 * 
 */
@Component
public class DataMapper extends ConfigurableMapper {
	private static class RegisterNewKitRequestToRequestMasterConverter extends
			CustomConverter<RegisterNewKitRequest, Requestmaster> {

		/** The mapper. */
		final private MapperFacade mapper;

		/**
		 * Instantiates a new member credit account data to member credit
		 * account converter.
		 * 
		 * @param mapper
		 *            the mapper
		 */
		RegisterNewKitRequestToRequestMasterConverter(final MapperFacade mapper) {
			this.mapper = mapper;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see ma.glasnost.orika.Converter#convert(java.lang.Object,
		 * ma.glasnost.orika.metadata.Type)
		 */
		@Override
		public Requestmaster convert(
				RegisterNewKitRequest registerNewKitRequest,
				Type<? extends Requestmaster> destinationType) {
			Requestmaster requestmaster = new Requestmaster();
			Date createAndUpdateTime = new Date();
			requestmaster.setCreateDateTime(createAndUpdateTime);
			requestmaster.setUpdateDateTime(createAndUpdateTime);
			requestmaster.setTransactionID(registerNewKitRequest
					.getTransactionId());
			requestmaster.setTransactionSeq(registerNewKitRequest
					.getTransactionSequenceNumber());
			// requestmaster.setKittype();
			Kit kit = new Kit();
			kit.setDescription(registerNewKitRequest.getKitBinding()
					.getKitDescription());
			kit.setName(registerNewKitRequest.getKitBinding().getKitName());
			// kit.setOwner();
			// kit.setKittype();
			kit.setSetupComplete(registerNewKitRequest.getKitBinding()
					.isSetupComplete());
			Set<Subkit> subkits = new HashSet<>();
			Subkit subkit = new Subkit();
			subkit.setDescription(registerNewKitRequest.getKitBinding()
					.getSubkitBindings().get(0).getSubkitDescription());
			subkit.setName(registerNewKitRequest.getKitBinding()
					.getSubkitBindings().get(0).getSubkitName());
			// subkit.setSubkittype();
			subkit.setKit(kit);
			subkits.add(subkit);
			kit.setSubkits(subkits);
			requestmaster.setKit(kit);

			Set<Requestimage> requestImages = new HashSet<>();
			Requestimage requestImage = new Requestimage();
			requestImage.setCreateDateTime(createAndUpdateTime);
			requestImage.setRequestImage(registerNewKitRequest.getKitBinding()
					.getSubkitBindings().get(0).getImage());
			requestImage.setRequestmaster(requestmaster);
			requestImage.setUpdateDateTime(createAndUpdateTime);
			// requestImage.setSubkittype();
			requestImages.add(requestImage);
			requestmaster.setRequestimages(requestImages);
			return requestmaster;
		}
	}


	@Override
	public void configure(MapperFactory mapperFactory) {

		ConverterFactory converterFactory = mapperFactory.getConverterFactory();
		converterFactory
				.registerConverter(new RegisterNewKitRequestToRequestMasterConverter(
						this));
	}

	public void configureFactoryBuilder(DefaultMapperFactory.Builder builder) {
		// Enforces Javassist compiler strategy; no need to change this
		builder.compilerStrategy(new JavassistCompilerStrategy());

		// Allows case-insensitve matching of names in the byDefault method
		builder.classMapBuilderFactory(new CaseInsensitiveClassMapBuilder.Factory());
	}
}
