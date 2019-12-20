
package org.freakz.luncher.dto.majakka;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "Date",
    "LunchTime",
    "SetMenus"
})
public class MenusForDay {

    @JsonProperty("Date")
    public String date;
    @JsonProperty("LunchTime")
    public Object lunchTime;
    @JsonProperty("SetMenus")
    public List<SetMenu> setMenus = null;

}
