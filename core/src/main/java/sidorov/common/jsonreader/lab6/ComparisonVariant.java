package sidorov.common.jsonreader.lab6;

import lombok.Data;

@Data
public class ComparisonVariant {
    private String criterion;
    private String variant;
    private Comparison[] comparisons;
}
