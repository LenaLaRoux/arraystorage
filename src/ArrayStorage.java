import java.util.Arrays;
import java.util.Comparator;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private int resumesCount = -1;
    boolean isSorted = false;
    Resume[] storage = new Resume[10000];

    void clear() {
        Arrays.fill(storage, 0, resumesCount + 1, null);
        resumesCount = -1;
    }

    void save(Resume r) {
        int foundIdx = search(r);
        if (foundIdx < 0) {
            if (resumesCount < storage.length) {
                storage[++resumesCount] = r;
            } else
                throw new IllegalArgumentException("Unable to save element");
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

        if (foundIdx == resumesCount)
            storage[foundIdx] = null;
        else{
            storage[foundIdx] = storage[resumesCount];
            storage[resumesCount] = null;
        }
        resumesCount--;
    }

    int search(Resume searchItem) {
        if (searchItem == null)
            return -1;

        for (int i = 0; i <= resumesCount; i++)
        {
            if (storage[i].equals(searchItem))
                return i;
        }
        return -1;
    }

    int search(String uuid) {
        return search(new Resume(uuid));
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOf(storage, resumesCount + 1);
    }

    int size() {
        return resumesCount + 1;
    }
}
