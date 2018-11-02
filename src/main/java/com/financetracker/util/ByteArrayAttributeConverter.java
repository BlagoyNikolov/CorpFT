package com.financetracker.util;

import org.apache.commons.codec.digest.DigestUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ByteArrayAttributeConverter implements AttributeConverter<byte[], String> {
  @Override
  public String convertToDatabaseColumn(byte[] attribute) {
    return (attribute == null ? null : DigestUtils.sha512Hex(attribute));
  }

  @Override
  public byte[] convertToEntityAttribute(String dbData) {
    return (dbData == null ? null : DigestUtils.sha512(dbData));
  }
}
