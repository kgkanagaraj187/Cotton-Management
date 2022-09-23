package com.example.calpyte.masterservice.dto;

import com.example.calpyte.masterservice.entity.Country;
import com.example.calpyte.masterservice.entity.District;
import com.example.calpyte.masterservice.entity.State;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BasicDTO {
    private String id;
    private String name;

    private Country country;
    private State state;

}
