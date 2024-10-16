package Models.TodoModels;
import com.fasterxml.jackson.annotation.*;
import javax.annotation.processing.Generated;
import java.util.LinkedHashMap;
import java.util.Map;
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "title",
        "due_on",
        "status"
})
@Generated("jsonschema2pojo")
public class CreateTodoRequestModel {
    @JsonProperty("title")
    private String title;
    @JsonProperty("due_on")
    private String dueOn;
    @JsonProperty("status")
    private String status;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty("due_on")
    public String getDueOn() {
        return dueOn;
    }

    @JsonProperty("due_on")
    public void setDueOn(String dueOn) {
        this.dueOn = dueOn;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}
