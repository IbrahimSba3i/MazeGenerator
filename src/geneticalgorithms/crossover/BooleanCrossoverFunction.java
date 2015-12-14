package geneticalgorithms.crossover;

import geneticalgorithms.Utility;
import geneticalgorithms.chromosome.Chromosome;

import java.util.ArrayList;
import java.util.List;

public class BooleanCrossoverFunction implements CrossoverFunction<Boolean> {

	@Override
	public List<Chromosome<Boolean>> getChromosome(Chromosome<Boolean> a, Chromosome<Boolean> b) {
		int randomPosition = Utility.rand().nextInt(a.size());
		List<Boolean> child1 = new ArrayList<Boolean>();
		List<Boolean> child2 = new ArrayList<Boolean>();
		for(int i = 0; i < randomPosition; i++) {
			child1.add(a.get(i));
			child2.add(b.get(i));
		}
		for(int i = randomPosition; i < a.size(); i++) {
			child1.add(b.get(i));
			child2.add(a.get(i));
		}
		List<Chromosome<Boolean>> result = new ArrayList<>();
		
		result.add(new Chromosome<>(a.getGeneration() + 1, a.size(), a.getFitnessFunction(), a.getMutationFunction(), child1));
		result.add(new Chromosome<>(a.getGeneration() + 1, a.size(), a.getFitnessFunction(), a.getMutationFunction(), child2));
		return result;
	}

}
