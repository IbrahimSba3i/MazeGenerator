package geneticalgorithms.chromosome;

import geneticalgorithms.Utility;

public class RandomBooleanInitializer implements ChromosomeInitializer<Boolean> {

	@Override
	public Boolean getCorrespondingValue(int index) {
		Boolean b = Utility.rand().nextBoolean();
		return b;
	}

}
