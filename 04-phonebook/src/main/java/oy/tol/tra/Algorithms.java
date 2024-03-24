package oy.tol.tra;


import java.util.function.Predicate;

public class Algorithms {

    public static <T> void reverse(T[] array) {
        int start = 0;
        int end = array.length - 1;

        while (start < end) {
            swap(array, start, end);
            start++;
            end--;
        }
    }


    public static <T extends Comparable<T>> void sort(T[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < array.length - 1 - i; j++) {
                if (array[j].compareTo(array[j + 1]) > 0) {
                    swap(array, j, j + 1);
                    swapped = true;
                }
            }
            if (!swapped) {
                break;
            }
        }
    }

    public static <E extends Comparable<E>> void fastSort(E[] array) {
        if (array == null || array.length <= 1) {
            return;
        }
        quickSort(array, 0, array.length - 1);
    }

    public static <E extends Comparable<E>> void quickSort(E[] array, int begin, int end) {
        if (begin < end) {
            int partitionIndex = partition(array, begin, end);
            quickSort(array, begin, partitionIndex - 1);
            quickSort(array, partitionIndex + 1, end);
        }
    }

    private static <E extends Comparable<E>> int partition(E[] array, int begin, int end) {
        E pivot = array[end];
        int i = begin - 1;
        for (int j = begin; j < end; j++) {
            if (array[j].compareTo(pivot) < 0) {
                i++;
                if (i != j) {
                    swap(array, i, j);
                }
            }
        }
        swap(array, i + 1, end);
        return i + 1;
    }


    public static <T> void swap(T[] array,int i,int j){
        T temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public static <T extends Comparable<T>> int binarySearch(T value, T[] array, int fromIndex, int toIndex) {
        if (fromIndex <= toIndex) {
            int middle = fromIndex + (toIndex - fromIndex) / 2;

            if (value.compareTo(array[middle]) == 0) {
                return middle;
            } else if (value.compareTo(array[middle]) < 0) {
                return binarySearch(value, array, fromIndex, middle - 1);
            } else {
                return binarySearch(value, array, middle + 1, toIndex);
            }
        }
        return -1;
    }
    public static <T> int partitionByRule(T[] array, int count, Predicate<T> rule) {
        int leftIndex = 0;

        for (int i = 0; i < count; i++) {
            if (!rule.test(array[i])) {
                swap(array, leftIndex, i);
                leftIndex++;
            }
        }

        return leftIndex;
    }



}
