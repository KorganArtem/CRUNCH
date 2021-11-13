package ru.reasy.crunch.dto;

import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
public class Account {

    public String nickname;
    public boolean deletable;
    public boolean editable;
    public boolean competition;
}
