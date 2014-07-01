package org.amse.marinaSokol.model.impl.object;

import org.amse.marinaSokol.model.impl.utils.NonExpandingQuery;

public class InputLayerData {
    private NonExpandingQuery myPatternTrainingData;

    public InputLayerData(double[][] data) {
        myPatternTrainingData = new NonExpandingQuery(data);
    }

    public InputLayerData(NonExpandingQuery data) {
        myPatternTrainingData = data;
    }

    public NonExpandingQuery getPatternTrainingData() {
        return myPatternTrainingData;
    }
}
