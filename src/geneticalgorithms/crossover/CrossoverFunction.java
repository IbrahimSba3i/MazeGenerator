package geneticalgorithms.crossover;

import geneticalgorithms.chromosome.Chromosome;

import java.util.List;

public interface CrossoverFunction<T> {
	public List<Chromosome<T>> getChromosome(Chromosome<T> a, Chromosome<T> b);
}
