
package org.freakz.luncher.dto.majakka;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "RestaurantName",
    "RestaurantUrl",
    "PriceHeader",
    "Footer",
    "MenusForDays",
    "ErrorText"
})
public class MajakkaJSON {

    @JsonProperty("RestaurantName")
    public String restaurantName;
    @JsonProperty("RestaurantUrl")
    public String restaurantUrl;
    @JsonProperty("PriceHeader")
    public Object priceHeader;
    @JsonProperty("Footer")
    public String footer;
    @JsonProperty("MenusForDays")
    public List<MenusForDay> menusForDays = null;
    @JsonProperty("ErrorText")
    public Object errorText;

}
