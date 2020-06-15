package com.kalkivera.apps.simpleapi.util;

import java.util.Set;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.converter.json.MappingJacksonValue;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

/**
 * Utility class for Shopping cart application
 * 
 */
public class AppUtil {

	private AppUtil() {

	}

	/**
	 * Get message from property files
	 * 
	 * @param messageSource
	 *            Source object
	 * @param messageKey
	 *            Key
	 * @return
	 */
	public static String getMessage(MessageSource messageSource, String messageKey) {
		return getMessage(messageSource, messageKey, null);
	}

	/**
	 * Get message from property files with arguments
	 * 
	 * @param messageSource
	 *            Source object
	 * @param messageKey
	 *            Key
	 * @param args
	 *            runtime values
	 * @return
	 */
	public static String getMessage(MessageSource messageSource, String messageKey, Object[] args) {
		return messageSource.getMessage(messageKey, args, LocaleContextHolder.getLocale());
	}

	/**
	 * Filter fields not required in JSON response
	 * 
	 * @param object
	 *            Response object
	 * @param propertiesToExclude
	 *            Fields to exclude
	 * @param jsonFilter
	 *            Jsonfilter name
	 * @return
	 */
	public static MappingJacksonValue applyJsonFilterExclude(Object object, Set<String> propertiesToExclude,
			String jsonFilter) {
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.serializeAllExcept(propertiesToExclude);
		return filterJson(object, jsonFilter, filter);
	}

	private static MappingJacksonValue filterJson(Object object, String jsonFilter, SimpleBeanPropertyFilter filter) {
		MappingJacksonValue jacksonValue = new MappingJacksonValue(object);
		FilterProvider filters = new SimpleFilterProvider().addFilter(jsonFilter, filter);
		jacksonValue.setFilters(filters);
		return jacksonValue;
	}

	public static MappingJacksonValue applyJsonFilterInclude(Object object, Set<String> propertiesToInclude,
			String jsonFilter) {
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept(propertiesToInclude);
		return filterJson(object, jsonFilter, filter);
	}

}
