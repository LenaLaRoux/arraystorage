import java.util.Arrays;
import java.util.Comparator;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private int endIdx = 0;
    boolean isSorted = false;
    Resume[] storage = new Resume[10000];

    void clear() {
        Arrays.fill(storage, null);
        endIdx = 0;
    }

    void save(Resume r) {
        var foundIdx = search(r);
        if (foundIdx < 0) {
            if (size() <= storage.length) {
                storage[++endIdx] = r;
            } else
                throw new ArrayIndexOutOfBoundsException("Unable to save element");
        } else {
            storage[foundIdx] = r;
        }
    }

    Resume get(String uuid) {
        int foundIdx = search(uuid);
        return foundIdx < 0 ? null : storage[foundIdx];
    }

    void delete(String uuid) {
        int foundIdx = search(uuid);
        if (foundIdx < 0)
            return;
        storage[foundIdx--] = null;
    }

    //copied from Arrays.binarySearch
    private static int binarySearch(Resume[] a, int fromIndex, int toIndex, Resume key) {
        int low = fromIndex;
        int high = toIndex - 1;

        while (low <= high) {
            int mid = (low + high) >>> 1;
            if (a[mid] == null)
                return -(low + 1);  // key not found.
            Resume midVal = a[mid];
            int cmp = midVal.compareTo(key);

            if (cmp < 0)
                low = mid + 1;
            else if (cmp > 0)
                high = mid - 1;
            else
                return mid; // key found
        }
        return -(low + 1);  // key not found.
    }

    int search(Resume searchItem) {
        sort();
        if (searchItem == null || storage[0] == null)
            return -1;
        return binarySearch(storage, 0, size(), searchItem);
    }

    int search(String uuid) {
        if (uuid == null)
            return -1;
        return search(new Resume(uuid));
    }

    void sort() {
        Arrays.sort(storage, Comparator.<Resume>nullsLast(Resume::compareTo));
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        sort();
        return Arrays.copyOf(storage, size());
    }

    int size() {
        return endIdx + 1;
    }
}
