package com.line4thon.kureomi.service;

import java.util.UUID;

public class GenerateUniqueUrl {
    public static String generateUniqueUrl() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
