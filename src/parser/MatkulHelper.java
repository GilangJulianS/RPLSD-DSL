package parser;

import java.util.List;

/**
 * Created by Fauzan Hilmi on 30/11/2015.
 */
public class MatkulHelper {
    public String nama;
    public String[] prereq;
    public int sks;
    public boolean availability;

    public MatkulHelper(String _nama, String[] _prereq, int _sks, boolean _availability) {
        nama = _nama;
        prereq = _prereq;
        sks = _sks;
        availability = _availability;
    }
}
