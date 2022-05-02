package sidorov.common.jsonreader.lab6;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BellmanZadehPojo {
    private String[] variants;
    private String[] criteria;
    @JsonProperty("comparison_variants")
    private ComparisonVariant[] comparisonVariants;
    @JsonProperty("comparison_criteria")
    private ComparisonCriteria comparisonCriteria;
}
