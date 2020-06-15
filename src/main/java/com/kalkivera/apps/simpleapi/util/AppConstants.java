package com.kalkivera.apps.simpleapi.util;

public enum AppConstants {

	SIMPLEAPI_PERSON_NOT_FOUND("simpleapi.validation.personnotfound", "simpleapi.validation.personnotfound"),
	
	SIMPLEAPI_INVALID_PERSONID("simpleapi.validation.invalidpersonid","simpleapi.validation.invalidpersonid")
	;

	String name;

	String value;

	public Integer getIntValue() {
		return Integer.valueOf(value);
	}

	private AppConstants(String name) {
		this.name = name;
	}

	private AppConstants(String name, String value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}

}
