package geneticalgorithms;

import java.util.List;

public interface FitnessFunction<T> {
	double getFitness(List<T> chromosome);
}
