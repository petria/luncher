
package org.freakz.luncher.dto.majakka;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "SortOrder",
    "Name",
    "Price",
    "Components"
})
public class SetMenu {

    @JsonProperty("SortOrder")
    public Integer sortOrder;
    @JsonProperty("Name")
    public String name;
    @JsonProperty("Price")
    public Object price;
    @JsonProperty("Components")
    public List<String> components = null;

}
