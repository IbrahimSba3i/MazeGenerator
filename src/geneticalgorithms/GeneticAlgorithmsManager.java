package geneticalgorithms;

import geneticalgorithms.chromosome.Chromosome;
import geneticalgorithms.chromosome.ChromosomeInitializer;
import geneticalgorithms.crossover.CrossoverFunction;
import geneticalgorithms.mutation.MutationFunction;
import geneticalgorithms.replacement.GenerationReplacementMethod;

import java.util.ArrayList;
import java.util.List;

public class GeneticAlgorithmsManager<T> implements ProgressObservable, FinishedObservable {
	
	protected List<Chromosome<T>> population;
	protected List<ProgressObserver> progressObservers;
	protected List<FinishedObserver> finishedObservers;
	protected MutationFunction<T> mutationFunction;
	protected CrossoverFunction<T> crossoverFunction;
	protected FitnessFunction<T> fitnessFunction;
	protected ChromosomeInitializer<T> chromosomeInitializer;
	protected GenerationReplacementMethod<T> generationReplacement;
	protected int populationSize;
	protected int chromosomeSize;
	protected int maxEpochs;
	protected double stoppingFitness;
	protected double maxFitness;
	protected Chromosome<T> bestChromosome;
	
	protected Chromosome<T> getInitialChromosome() {
		return new Chromosome<>(0, chromosomeSize, fitnessFunction, mutationFunction, chromosomeInitializer);
	}
	
	public GeneticAlgorithmsManager(int populationSize, int chromosomeSize,
			int maxEpochs, double stoppingFitness, 
			MutationFunction<T> mutationFunction, 
			CrossoverFunction<T> crossoverFunction,
			FitnessFunction<T> fitnessFunction, 
			ChromosomeInitializer<T> chromosomeInitializer,
			GenerationReplacementMethod<T> generationReplacement) {
		
		this.populationSize = populationSize;
		this.maxEpochs = maxEpochs;
		this.stoppingFitness = stoppingFitness;
		this.mutationFunction = mutationFunction;
		this.crossoverFunction = crossoverFunction;
		this.fitnessFunction = fitnessFunction;
		this.chromosomeSize = chromosomeSize;
		this.chromosomeInitializer = chromosomeInitializer;
		this.generationReplacement = generationReplacement;
		
		initializePopulation();
		progressObservers = new ArrayList<>();
		finishedObservers = new ArrayList<>();
		maxFitness = Integer.MIN_VALUE;
	}

	protected void initializePopulation() {
		population = new ArrayList<Chromosome<T>>();
		for(int i = 0; i < populationSize; i++) {
			population.add(getInitialChromosome());
		}
	}
    
    public void startTraining() {    	
    	List<Chromosome<T>> children = new ArrayList<>();
    	for(int epoch = 0; epoch < maxEpochs && maxFitness < stoppingFitness; epoch++) {
    		Chromosome<T> currentBest = trainGeneration(population, children);
    		
    		if(currentBest.getFitness() > maxFitness) {
    			maxFitness = currentBest.getFitness();
    			bestChromosome = currentBest;
    		}
    		
    		// Set the current population to the new generation
    		List<Chromosome<T>> temp = population;
    		population = children;
    		children = temp;
    		children.clear();
    		
    		notifyProgressObservers(epoch);
    	}
    	notifyFinishedObservers();
    }
    
	protected Chromosome<T> trainGeneration(List<Chromosome<T>> currentGeneration, List<Chromosome<T>> children) {
		generationReplacement.createNewGeneration(currentGeneration, children, crossoverFunction);
		return generationReplacement.getBestChild();
	}

	@Override
	public void addProgressObserver(ProgressObserver observer) {
		progressObservers.add(observer);
	}
	
	@Override
	public void removeProgressObserver(ProgressObserver observer) {
		progressObservers.remove(observer);
	}
	
	@Override
	public void notifyProgressObservers(int progress) {
		for(ProgressObserver observer : progressObservers) {
			observer.onUpdate(progress);
		}
	}

	public double getMaxFitness() {
		return maxFitness;
	}
	
	public Chromosome<T> getBestChromosome() {
		return bestChromosome;
	}

	@Override
	public void addFinishedObserver(FinishedObserver observer) {
		finishedObservers.add(observer);
	}

	@Override
	public void removeFinishedObserver(FinishedObserver observer) {
		finishedObservers.remove(observer);
	}

	@Override
	public void notifyFinishedObservers() {
		for(FinishedObserver observer : finishedObservers) {
			observer.onFinished();
		}
	}
}
