package geneticalgorithms.mutation;

public interface MutationFunction<T> {
	public T getMutation(T gene, int chromosomeSize, int generation);
}
