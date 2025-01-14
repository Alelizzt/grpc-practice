package com.system.grpc.server.model;

public record Book(int bookId, String tittle, float price, int pages, int authorId) { }
