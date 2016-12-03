package ua.rd.project4.model.dao.impl;

public enum JdbcFields {
    ID("id"), CAR("car"), INVOICE("invoice"), CLIENT("client");

    String fieldName;

    public String getFieldName() {
        return fieldName;
    }

    JdbcFields(String fieldName) {
        this.fieldName = fieldName;
    }
}
