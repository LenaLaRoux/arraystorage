import java.util.Comparator;
import java.util.Objects;

/**
 * Initial resume class
 */
public class Resume implements Comparable<Resume> {

    // Unique identifier
    String uuid;

    public Resume()
    {

    }
    public Resume(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return uuid;
    }

    @Override
    public int compareTo(Resume o) {
        if (o == null)
            return 1;
        if (this.uuid == null && o.uuid == null)
            return 0;
        if(this.uuid == null)
            return -1;
        if (o.uuid == null)
            return 1;
        return this.uuid.compareTo(o.uuid);
    }
}
