package org.tat.fni.api.domain.services;

public interface ICustomIdGenerator {

	String getNextId(String key, String productCode);

}
