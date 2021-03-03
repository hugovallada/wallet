package com.github.hugovallada.wallet.util;

public enum TypeEnum {
    EN("ENTRADA"),
    SD("SAIDA");

    private final String value;

    TypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public static TypeEnum getEnum(String value) {
        for (var t : values()) {
            if (value.equals(t.getValue())) {
                return t;
            }
        }
        return null;
    }
}