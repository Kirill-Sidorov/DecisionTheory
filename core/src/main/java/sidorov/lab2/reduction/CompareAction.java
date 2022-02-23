package sidorov.lab2.reduction;

@FunctionalInterface
public interface CompareAction {
    boolean compare(int rowOrColumn, int i, int j);
}
