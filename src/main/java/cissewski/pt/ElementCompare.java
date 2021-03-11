package cissewski.pt;

import java.util.Comparator;

public class ElementCompare implements Comparator<Element> {
    @Override
    public int compare(Element o1, Element o2) {
        int ret = o1.getCreateDate().compareTo(o2.getCreateDate());

        if (ret == 0) {
            ret = o1.getName().compareTo(o2.getName());
        }

        if (ret == 0){
            ret = o1.getLevel() - o2.getLevel();
        }

        return ret;
    }

}
