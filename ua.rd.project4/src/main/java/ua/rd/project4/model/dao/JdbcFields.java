package ua.rd.project4.model.dao;

public enum JdbcFields {
    ID("id"), CAR("car"), INVOICE("invoice"), CLIENT("client"), CARREQUEST("carRequest"), RESPONSIBLEUSER("responsiblePerson");

    String fieldName;

    public String getFieldName() {
        return fieldName;
    }

    JdbcFields(String fieldName) {
        this.fieldName = fieldName;
    }
}
