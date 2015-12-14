package geneticalgorithms.mutation;

import geneticalgorithms.Utility;

public class BooleanMutationFunction implements MutationFunction<Boolean> {

	@Override
	public Boolean getMutation(Boolean gene, int chromosomeSize, int generation) {
		double r = Utility.rand().nextDouble();
		double p = 25 * 1.0 / chromosomeSize;
		if(r <= p) {
			return !gene.booleanValue();
		}
		return gene;
	}

}
