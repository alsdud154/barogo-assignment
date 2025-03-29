package com.barogo.assignment.application.delivery.dto;

public record ChangeAddressCommand(long id, long accountId, String address) {}
