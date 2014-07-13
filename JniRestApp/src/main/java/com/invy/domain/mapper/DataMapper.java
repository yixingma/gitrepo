/**
 * 
 */
package com.invy.domain.mapper;

import java.io.IOException;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.invy.commons.tool.CompressionUtils;
import com.invy.database.jpa.data.Item;
import com.invy.database.jpa.data.Itemtx;
import com.invy.database.jpa.data.Kit;
import com.invy.database.jpa.data.Optitemtemplate;
import com.invy.database.jpa.data.Requestimage;
import com.invy.database.jpa.data.Requestmaster;
import com.invy.database.jpa.data.Subkit;
import com.invy.endpoint.ItemBinding;
import com.invy.endpoint.KitBinding;
import com.invy.endpoint.RegisterNewKitRequest;
import com.invy.endpoint.SubkitBinding;

/**
 * @author ema
 * 
 */
@Component
public class DataMapper extends ConfigurableMapper {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(DataMapper.class);

	private static class ItemToItemBindingConverter extends
			CustomConverter<Item, ItemBinding> {

		@Override
		public ItemBinding convert(Item item,
				Type<? extends ItemBinding> destinationType) {
			ItemBinding itemBinding = new ItemBinding();
			itemBinding.setItemId(item.getId());
			itemBinding.setItemrefId(item.getItemref().getId());
			itemBinding.setDescription(item.getItemref().getDescription());
			itemBinding.setName(item.getItemref().getName());
			itemBinding.setUnitNum(item.getUnitNum());
			return itemBinding;
		}
	}

	private static class ItemTxToItemBindingConverter extends
			CustomConverter<Itemtx, ItemBinding> {

		@Override
		public ItemBinding convert(Itemtx itemtx,
				Type<? extends ItemBinding> destinationType) {
			ItemBinding itemBinding = new ItemBinding();
			itemBinding.setItemId(itemtx.getId());
			itemBinding.setItemrefId(itemtx.getItemref().getId());
			itemBinding.setDescription(itemtx.getItemref().getDescription());
			itemBinding.setName(itemtx.getItemref().getName());
			itemBinding.setUnitNum(itemtx.getUnitNum());
			return itemBinding;
		}
	}
	private static class OptitemtemplateToItemBindingConverter extends
			CustomConverter<Optitemtemplate, ItemBinding> {

		@Override
		public ItemBinding convert(Optitemtemplate optitemTemplate,
				Type<? extends ItemBinding> destinationType) {
			ItemBinding itemBinding = new ItemBinding();
			itemBinding.setItemrefId(optitemTemplate.getItemref().getId());
			itemBinding.setDescription(optitemTemplate.getItemref().getDescription());
			itemBinding.setName(optitemTemplate.getItemref().getName());
			itemBinding.setUnitNum(optitemTemplate.getOptUnitNum());
			itemBinding.setItemId(optitemTemplate.getId());
			return itemBinding;
		}
	}
	
	private static class SubkitToSubkitBindingConverter extends
	CustomConverter<Subkit, SubkitBinding> {
		/** The mapper. */
		final private MapperFacade mapper;

		/**
		 * 
		 * @param mapper
		 *            the mapper
		 */
		SubkitToSubkitBindingConverter(final MapperFacade mapper) {
			this.mapper = mapper;
		}
		@Override
		public SubkitBinding convert(Subkit subkit,
				Type<? extends SubkitBinding> destinationType) {
			SubkitBinding subkitBinding = new SubkitBinding();
			subkitBinding.setItemBindings(mapper.mapAsList(subkit.getItems(), ItemBinding.class));
			subkitBinding.setSubkitDescription(subkit.getDescription());
			subkitBinding.setSubkitId(subkit.getId());
			subkitBinding.setSubkitName(subkit.getName());
			subkitBinding.setSubkitTypeId(subkit.getSubkittype().getId());
			return subkitBinding;
		}
		
	}
	private static class KitToKitBindingConverter extends
			CustomConverter<Kit, KitBinding> {
		/** The mapper. */
		final private MapperFacade mapper;

		/**
		 * 
		 * @param mapper
		 *            the mapper
		 */
		KitToKitBindingConverter(final MapperFacade mapper) {
			this.mapper = mapper;
		}
		@Override
		public KitBinding convert(Kit kit,
				Type<? extends KitBinding> destinationType) {
			KitBinding kitBinding = new KitBinding();
			kitBinding.setKitDescription(kit.getDescription());
			kitBinding.setKitId(kit.getId());
			kitBinding.setKitName(kit.getName());
			kitBinding.setKitTypeId(kit.getKittype().getId());
			kitBinding.setOwnerId(kit.getOwner().getUserId());
			kitBinding.setSetupComplete(kit.isSetupComplete());
			kitBinding.setSubkitBindings(mapper.mapAsList(kit.getSubkits(), SubkitBinding.class));
			return kitBinding;
		}
	}
	
	
	private static class RegisterNewKitRequestToRequestMasterConverter extends
			CustomConverter<RegisterNewKitRequest, Requestmaster> {

		/** The mapper. */
		final private MapperFacade mapper;

		/**
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
			byte[] rawImage = registerNewKitRequest.getKitBinding()
					.getSubkitBindings().get(0).getImage();
			byte[] compressedImage=null;
			try {
				compressedImage = CompressionUtils.compress(rawImage);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				LOGGER.error(e.getMessage());
			}
			requestImage.setRequestImage(compressedImage);
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
		converterFactory.registerConverter(new ItemTxToItemBindingConverter());
		converterFactory.registerConverter(new ItemToItemBindingConverter());
		converterFactory.registerConverter(new OptitemtemplateToItemBindingConverter());
		converterFactory.registerConverter(new SubkitToSubkitBindingConverter(this));
		converterFactory.registerConverter(new KitToKitBindingConverter(this));
		
	}

	public void configureFactoryBuilder(DefaultMapperFactory.Builder builder) {
		// Enforces Javassist compiler strategy; no need to change this
		builder.compilerStrategy(new JavassistCompilerStrategy());

		// Allows case-insensitve matching of names in the byDefault method
		builder.classMapBuilderFactory(new CaseInsensitiveClassMapBuilder.Factory());
	}
}
