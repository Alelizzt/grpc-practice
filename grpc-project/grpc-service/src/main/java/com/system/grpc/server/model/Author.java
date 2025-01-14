package com.system.grpc.server.model;

public record Author(int authorId, String firstName, String lastName, String gender, int bookId) { }
