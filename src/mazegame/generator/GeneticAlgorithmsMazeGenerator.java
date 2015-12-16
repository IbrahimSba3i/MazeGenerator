package mazegame.generator;

import geneticalgorithms.FinishedObserver;
import geneticalgorithms.FitnessFunction;
import geneticalgorithms.GeneticAlgorithmsManager;
import geneticalgorithms.ProgressObserver;
import geneticalgorithms.chromosome.ChromosomeInitializer;
import geneticalgorithms.chromosome.RandomBooleanInitializer;
import geneticalgorithms.crossover.BooleanCrossoverFunction;
import geneticalgorithms.crossover.CrossoverFunction;
import geneticalgorithms.mutation.BooleanMutationFunction;
import geneticalgorithms.mutation.MutationFunction;
import geneticalgorithms.replacement.FullyConnectedReplacement;
import geneticalgorithms.replacement.GenerationReplacementMethod;

import java.util.ArrayList;
import java.util.List;

import mazegame.Globals;
import mazegame.Difficulty;

public class GeneticAlgorithmsMazeGenerator {
	protected GeneticAlgorithmsManager<Boolean> geneticAlgorithmsManager;
	protected int chromosomeSize = Globals.TILES_COLS * Globals.TILES_ROWS;
	protected int maxEpochs = Globals.MAX_EPOCHS;
	protected int populationSize = 100;
	protected double stoppingFitness = 0.1;
	protected MutationFunction<Boolean> mutationFunction;
	protected CrossoverFunction<Boolean> crossoverFunction;
	protected FitnessFunction<Boolean> fitnessFunction;
	protected ChromosomeInitializer<Boolean> chromosomeInitializer;
	protected GenerationReplacementMethod<Boolean> generationReplacement;
	protected Difficulty difficulty;
	
	protected MazeInformation errorTracker;
	protected FinishedObserver finishedObserver;
	
	public List<Boolean> getTestChromosome() {
		boolean[] testChromosomeVals = {true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, false, true, true, true, false, false, true, false, false, true, true, true, true, true, false, true, false, true, true, true, false, false, false, false, false, true, true, true, false, false, false, false, false, true, false, true, false, false, true, true, false, true, true, true, true, true, true, false, false, true, true, true, true, true, false, true, true, false, true, true, false, true, true, false, true, true, true, true, false, true, true, true, true, true, false, true, false, false, true, true, false, true, true, false, false, false, false, true, false, false, false, false, false, false, false, true, false, true, true, true, false, false, false, false, true, true, true, true, false, true, true, true, true, true, true, true, false, true, true, true, false, false, true, true, true, false, false, false, false, true, true, false, false, false, false, true, false, false, true, true, true, false, true, true, true, true, true, true, false, true, true, false, true, true, true, true, true, false, true, true, true, false, true, true, true, true, false, true, false, false, false, false, true, true, true, true, true, false, true, true, false, false, false, false, false, false, false, false, false, true, true, true, true, true, false, true, true, false, true, true, true, false, true, true, true, true, false, true, true, true, true, true, true, true, false, true, false, false, true, true, false, false, true, true, true, true, false, true, true, false, true, true, true, true, false, true, false, true, true, true, true, false, true, true, true, true, false, true, true, false, false, false, false, false, false, true, false, false, true, true, true, false, false, true, true, true, false, true, true, false, true, true, true, true, true, true, false, true, true, true, true, false, true, true, true, true, false, true, true, false, true, true, true, true, true, true, false, true, true, true, true, false, true, true, true, true, false, false, false, false, false, false, false, false, false, false, false, false, true, true, true, false, true, true, true, true, true, true, true, false, true, true, true, true, false, true, false, true, true, true, true, false, true, true, false, true, true, true, true, true, true, true, true, true, false, true, false, true, true, true, true, false, true, true, false, true, true, true, true, true, true, true, true, true, false, true, false, true, true, true, false, false, true, true, false, true, false, false, false, false, false, false, false, true, false, true, false, true, true, true, false, true, true, true, false, true, false, true, true, true, true, false, true, true, false, true, false, true, true, true, false, true, true, true, false, true, false, true, true, true, true, false, true, true, false, false, false, true, true, true, false, false, false, false, false, false, false, true, true, true, true, false, true, true, true, false, false, false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true};
		List<Boolean> testChromosome = new ArrayList<>();
		for(int i = 0; i < testChromosomeVals.length; i++) {
			testChromosome.add(testChromosomeVals[i]);
		}
		return testChromosome;
	}

	
	public GeneticAlgorithmsMazeGenerator(ProgressObserver loadingBarWindow,
			FinishedObserver finishedObserver,
			Difficulty difficulty) {
		this.difficulty = difficulty;
		this.finishedObserver = finishedObserver;

		errorTracker = null;
		
		initializeMutationFunction();
		initializeCrossoverFunction();
		initializeFitnessFunction();
		initializeChromosomeInitializer();
		initializeGenerationReplacement();
		
		geneticAlgorithmsManager = new GeneticAlgorithmsManager<>(populationSize, 
				chromosomeSize,
				maxEpochs,
				stoppingFitness,
				mutationFunction,
				crossoverFunction,
				fitnessFunction,
				chromosomeInitializer,
				generationReplacement);
		
		if(loadingBarWindow != null) {
			geneticAlgorithmsManager.addProgressObserver(loadingBarWindow);
		}
		if(finishedObserver != null) {
			geneticAlgorithmsManager.addFinishedObserver(finishedObserver);
		}
		
		// Error tracker per epoch
		geneticAlgorithmsManager.addProgressObserver(new ProgressObserver() {
			@Override
			public void onUpdate(int progress) {
				
				System.out.println("Epoch:   " + progress);
				System.out.println("Fitness: " + geneticAlgorithmsManager.getMaxFitness());
				System.out.println("-------------------------------------");
				
			}
		});
	}

	public void initializeGenerationReplacement() {
		generationReplacement = new FullyConnectedReplacement<>();
	}

	public void initializeChromosomeInitializer() {
		chromosomeInitializer = new RandomBooleanInitializer();
	}

	public void initializeFitnessFunction() {
		fitnessFunction = new MazeFitnessFunction(difficulty);
		((MazeFitnessFunction) fitnessFunction).setErrorRecorder(new ErrorRecorder() {
			
			@Override
			public void onErrorMeasure(MazeInformation mazeInformation) {
				if(errorTracker == null) {
					errorTracker = mazeInformation;
				}
				else if(errorTracker.compareTo(mazeInformation) == -1){
					errorTracker = mazeInformation;
				}
			}
		});
	}

	public void initializeCrossoverFunction() {
		crossoverFunction = new BooleanCrossoverFunction();
		// crossoverFunction = new BooleanRandomParentCrossover();
	}

	public void initializeMutationFunction() {
		mutationFunction = new BooleanMutationFunction();
	}

	public GeneticAlgorithmsManager<Boolean> getGeneticAlgorithmsManager() {
		return geneticAlgorithmsManager;
	}

	public int getChromosomeSize() {
		return chromosomeSize;
	}

	public int getMaxEpochs() {
		return maxEpochs;
	}

	public int getPopulationSize() {
		return populationSize;
	}

	public double getStoppingFitness() {
		return stoppingFitness;
	}

	public MutationFunction<Boolean> getMutationFunction() {
		return mutationFunction;
	}

	public CrossoverFunction<Boolean> getCrossoverFunction() {
		return crossoverFunction;
	}

	public FitnessFunction<Boolean> getFitnessFunction() {
		return fitnessFunction;
	}

	public ChromosomeInitializer<Boolean> getChromosomeInitializer() {
		return chromosomeInitializer;
	}
	
	public GenerationReplacementMethod<Boolean> getGenerationReplacement() {
		return generationReplacement;
	}

	public void start() {
		geneticAlgorithmsManager.startTraining();
	}

	public MazeInformation getBestMazeInfo() {
		return errorTracker;
	}
	
}
