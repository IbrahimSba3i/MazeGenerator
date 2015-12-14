package geneticalgorithms.replacement;

import geneticalgorithms.chromosome.Chromosome;
import geneticalgorithms.crossover.CrossoverFunction;

import java.util.List;

public interface GenerationReplacementMethod<T> {
    public Chromosome<T> selectRandomParent();
	public Chromosome<T> getBestChild();
	public void createNewGeneration(List<Chromosome<T>> source, List<Chromosome<T>> target, CrossoverFunction<T> crossoverFunction);
}
