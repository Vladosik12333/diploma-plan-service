package by.vb.diplomaplanservice.mapper;

public interface Mapper<S, T> {
	T mapTo(S source);
}
