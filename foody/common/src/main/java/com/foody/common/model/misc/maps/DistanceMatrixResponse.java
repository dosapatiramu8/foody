package com.foody.common.model.misc.maps;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DistanceMatrixResponse {
    private List<String> destination_addresses;
    private List<String> origin_addresses;
    private List<Row> rows;
    private String status;

}
