import java.util.Arrays;
import java.util.Objects;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private int endIdx = -1;
    Resume[] storage = new Resume[10000];

    void clear() {
        Arrays.fill(storage, 0, endIdx + 1, null);
        endIdx = -1;
    }

    void save(Resume r) {
        if (r == null) return;

        int foundIdx = search(r.uuid);
        //is already added
        if (foundIdx >= 0) return;

        if (endIdx >= storage.length) throw new IllegalArgumentException("Unable to save element");

        storage[++endIdx] = r;
    }

    Resume get(String uuid) {
        int foundIdx = search(uuid);
        return foundIdx < 0 ? null : storage[foundIdx];
    }

    void delete(String uuid) {
        int foundIdx = search(uuid);
        if (foundIdx < 0) return;

        if (foundIdx == endIdx) storage[foundIdx] = null;
        else {
            storage[foundIdx] = storage[endIdx];
            storage[endIdx] = null;
        }
        endIdx--;
    }

    private int search(String uuid) {

        for (int i = 0; i <= endIdx; i++) {
            if (Objects.equals(storage[i].uuid, uuid)) return i;
        }
        return -1; //not found
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOf(storage, endIdx + 1);
    }

    int size() {
        return endIdx + 1;
    }
}
