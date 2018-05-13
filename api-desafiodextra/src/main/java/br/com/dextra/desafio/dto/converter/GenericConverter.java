package br.com.dextra.desafio.dto.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface GenericConverter<I, O> extends Function<I, O> {

	default O convert(final I input) {
        O output = null;
        if (input != null) {
            output = this.apply(input);
        }
        return output;
    }
    
    default List<O> convert(final List<I> input) {
        List<O> output = new ArrayList<O>();
        if (input != null) {
            output = input.stream().map(this::apply).collect(Collectors.toList());
        }
        return output;
    }
}
