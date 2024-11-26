package edu.du.sb1101.registerMember.entity;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class MemDto {

    @NotBlank
    private String username;

    @NotBlank
    private String address;

    @NotBlank
    private String email;
}
