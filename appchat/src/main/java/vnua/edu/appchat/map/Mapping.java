package vnua.edu.appchat.map;

public interface Mapping<E,D> {
    E toEntity(D dto);
    D toDto(E entity);
    E updateFromDTO(D dto);
}