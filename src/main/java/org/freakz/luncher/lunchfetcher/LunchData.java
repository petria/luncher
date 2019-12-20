package org.freakz.luncher.lunchfetcher;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LunchData {

    private String title;
    private String menu;

}
