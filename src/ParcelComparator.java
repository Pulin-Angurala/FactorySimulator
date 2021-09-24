import java.util.Comparator;

public class ParcelComparator implements Comparator<Parcel<?>> {

    @Override
    public int compare(Parcel<?> o1, Parcel<?> o2) {
        return o1.compareTo(o2);
    }
}
