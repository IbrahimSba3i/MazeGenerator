package geneticalgorithms.replacement;

import geneticalgorithms.Utility;
import geneticalgorithms.chromosome.Chromosome;
import geneticalgorithms.crossover.CrossoverFunction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FullyConnectedReplacement <T> implements GenerationReplacementMethod<T> {
	protected List<Chromosome<T>> population;
	protected List<Chromosome<T>> children;
	protected Chromosome<T> bestChild;

	@Override
	public Chromosome<T> selectRandomParent() {
		return null;
	}

	@Override
	public Chromosome<T> getBestChild() {
		return bestChild;
	}

	@Override
	public void createNewGeneration(List<Chromosome<T>> source, List<Chromosome<T>> target, CrossoverFunction<T> crossoverFunction) {
		this.population = source;
		this.children = target;
		
		List<Chromosome<T>> hugeGeneration = new ArrayList<Chromosome<T>>();
		
		double maxFitness = -100;

		for(int i = 0; i < population.size(); i++) {
			for(int j = i + 1; j < population.size(); j++) {
				Chromosome<T> parent1 = population.get(i);
				Chromosome<T> parent2 = population.get(j);
				List<Chromosome<T>> childrenPair = crossoverFunction.getChromosome(parent1, parent2);

				Chromosome<T> a = childrenPair.get(0);
				Chromosome<T> b = childrenPair.get(1);

				hugeGeneration.add(a);
				hugeGeneration.add(b);

				if(Utility.max(a, b).getFitness() > maxFitness) {
					maxFitness = Utility.max(a, b).getFitness();
					bestChild = Utility.max(a, b);
				}
			}
		}
		Collections.sort(hugeGeneration);
		Collections.reverse(hugeGeneration);
		for(int i = 0; i < population.size(); i++) {
			children.add(hugeGeneration.get(i));
		}
		Collections.shuffle(children);
	}
}
