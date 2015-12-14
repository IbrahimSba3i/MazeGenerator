package geneticalgorithms.chromosome;

import geneticalgorithms.FitnessFunction;
import geneticalgorithms.mutation.MutationFunction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Chromosome<T> implements Comparable<Chromosome<T>>, List<T>{
	protected int generation;
	protected FitnessFunction<T> fitnessFunction;
	protected MutationFunction<T> mutationFunction;
	protected ChromosomeInitializer<T> initializer;
	protected double fitness;
	protected boolean fitnessInitialized;
	protected List<T> genes;
	protected int chromosomeSize;
	protected int id;
	protected static int idCounter = 0;
	
	public Chromosome(int generation, int chromosomeSize,
			FitnessFunction<T> fitnessFunction, MutationFunction<T> mutationFunction, 
			ChromosomeInitializer<T> initializer) {
		id = ++idCounter;
		this.generation = generation;
		this.fitnessFunction = fitnessFunction;
		this.mutationFunction = mutationFunction;
		this.initializer = initializer;
		this.genes = new ArrayList<T>();
		this.chromosomeSize = chromosomeSize;
		fitnessInitialized = false;
		initializeGenes();
	}
	
	public Chromosome(Chromosome<T> other) {
		id = ++idCounter;
		this.generation = other.generation;
		this.fitnessFunction = other.fitnessFunction;
		this.mutationFunction = other.mutationFunction;
		this.initializer = other.initializer;
		this.genes = new ArrayList<T>();
		this.chromosomeSize = other.chromosomeSize;
		fitnessInitialized = true;
		this.fitness = other.fitness;
		
		for(int i = 0; i < other.size(); i++) {
			genes.add(other.get(i));
		}
	}
	
	public Chromosome(int generation, int chromosomeSize,
			FitnessFunction<T> fitnessFunction, MutationFunction<T> mutationFunction, 
			List<T> genes) {
		this.generation = generation;
		this.fitnessFunction = fitnessFunction;
		this.mutationFunction = mutationFunction;
		this.genes = genes;
		this.chromosomeSize = chromosomeSize;
		fitnessInitialized = false;
	}
	
	public void initializeGenes() {
		for(int i = 0; i < chromosomeSize; i++) {
			genes.add(initializer.getCorrespondingValue(i));
		}
	}

	@Override
	public int compareTo(Chromosome<T> o) {
        if(getFitness() < o.getFitness())
            return -1;
        else if(getFitness() > o.getFitness()) {
            return 1;
        }
        return 0;
	}

	public double getFitness() {
		if(fitnessInitialized == false) {
			fitnessInitialized = true;
			fitness = fitnessFunction.getFitness(this);
			getMutation();
		}
		return fitness;
	}
	
	public void getMutation() {
		for(int i = 0; i < size(); i++) {
			genes.set(i, mutationFunction.getMutation(genes.get(i), size(), getGeneration()));
		}
	}
	
	public int getGeneration() {
		return generation;
	}

	public MutationFunction<T> getMutationFunction() {
		return mutationFunction;
	}

	public FitnessFunction<T> getFitnessFunction() {
		return fitnessFunction;
	}

	@Override
	public boolean add(T arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void add(int arg0, T arg1) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addAll(Collection<? extends T> arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addAll(int arg0, Collection<? extends T> arg1) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clear() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean contains(Object arg0) {
		return genes.contains(arg0);
	}

	@Override
	public boolean containsAll(Collection<?> arg0) {
		return genes.containsAll(arg0);
	}

	@Override
	public T get(int arg0) {
		return genes.get(arg0);
	}

	@Override
	public int indexOf(Object arg0) {
		return genes.indexOf(arg0);
	}

	@Override
	public boolean isEmpty() {
		return genes.isEmpty();
	}

	@Override
	public Iterator<T> iterator() {
		return genes.iterator();
	}

	@Override
	public int lastIndexOf(Object arg0) {
		return genes.lastIndexOf(arg0);
	}

	@Override
	public ListIterator<T> listIterator() {
		return genes.listIterator();
	}

	@Override
	public ListIterator<T> listIterator(int arg0) {
		return genes.listIterator(arg0);
	}

	@Override
	public boolean remove(Object arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public T remove(int arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeAll(Collection<?> arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean retainAll(Collection<?> arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public T set(int arg0, T arg1) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int size() {
		return genes.size();
	}

	@Override
	public List<T> subList(int arg0, int arg1) {
		return genes.subList(arg0, arg1);
	}

	@Override
	public Object[] toArray() {
		return genes.toArray();
	}

	@SuppressWarnings("hiding")
	@Override
	public <T> T[] toArray(T[] arg0) {
		return genes.toArray(arg0);
	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Chromosome [generation=" + generation + ", fitness=" + fitness
				+ ", genes=" + genes + "]";
	}

}
