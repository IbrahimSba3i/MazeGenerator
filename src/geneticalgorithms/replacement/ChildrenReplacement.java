package geneticalgorithms.replacement;

import geneticalgorithms.Utility;
import geneticalgorithms.chromosome.Chromosome;
import geneticalgorithms.crossover.CrossoverFunction;

import java.util.List;
import java.util.Random;

public class ChildrenReplacement<T> implements GenerationReplacementMethod<T> {
	protected List<Chromosome<T>> population;
	protected List<Chromosome<T>> children;
	protected Chromosome<T> bestChild;
	
	@Override
	public Chromosome<T> selectRandomParent() {
	   	Random rand = Utility.rand();
        Chromosome<T> firstCandidate = population.get(rand.nextInt(population.size()));
        Chromosome<T> secondCandidate = population.get(rand.nextInt(population.size()));
        return Utility.max(firstCandidate, secondCandidate);
	}

	@Override
	public Chromosome<T> getBestChild() {
		return bestChild;
	}

	@Override
	public void createNewGeneration(List<Chromosome<T>> source, List<Chromosome<T>> target, CrossoverFunction<T> crossoverFunction) {
		this.population = source;
		this.children = target;
		
		double maxFitness = -100;
		
		for(int i = 0; i < population.size(); i+=2) {
			Chromosome<T> parent1 = selectRandomParent();
            Chromosome<T> parent2 = selectRandomParent();
            List<Chromosome<T>> childrenPair = crossoverFunction.getChromosome(parent1, parent2);
            
            Chromosome<T> a = childrenPair.get(0);
            Chromosome<T> b = childrenPair.get(1);
            
            children.add(a);
            children.add(b);
            
            if(Utility.max(a, b).getFitness() > maxFitness) {
                maxFitness = Utility.max(a, b).getFitness();
                bestChild = Utility.max(a, b);
            }

		}	
	}

}
