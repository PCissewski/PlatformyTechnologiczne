package cissewski.pt;

import lombok.Getter;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

@Getter
@Setter

public class Element implements Comparable<Element>{
    private String name;
    private int level;
    private Date createDate;
    private Set<Element> subElements;
    private boolean isDirectory;

    public int getFileDepth(){
        int n = 0;
        if (!subElements.isEmpty()) {
            n = subElements.size();
            for (Element marine: subElements) {
                n += marine.getFileDepth();
            }
        }
        return n;
    }

    public Element(String name, int level, Date date, boolean isDirectory,String sortType) {
        this.name = name;
        this.level = level;
        this.createDate = date;
        this.isDirectory = isDirectory;

        // sorting files
        if (sortType.equals("null")) {
            this.subElements = new HashSet<>();
        }
        else if (sortType.equals("natural")){
            this.subElements = new TreeSet<>();
        }
        else if (sortType.equals("alternative")){
            this.subElements = new TreeSet<>(new ElementCompare());
        }
        else {
            throw new RuntimeException("No sortType set");
        }

    }

    @Override
    public int hashCode(){
        int result = name != null ? name.hashCode() : 0;
        result = 47* result + level;
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true; // exact the same object
        }

        if (other == null || getClass() != other.getClass()){
            return false; // other is null or final classes are different
        }

        Element file = (Element) other;

        return level == file.level &&
                createDate == file.createDate &&
                name.equals(file.name);
    }

    @Override
    public int compareTo(Element other) {
        int ret = name.compareTo(other.name);
        //not that great
        if (ret == 0){
            ret = level - other.level;
        }
        if (ret == 0){
            ret = createDate.compareTo(other.createDate);
        }
        return ret;
    }

    @Override
    public String toString(){

        SimpleDateFormat simplerDate = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < level; i++){
            builder.append(defineRepresentation());
        }
        builder.append(name +"(" +
                "Level=" + level + "," +
                "createDate=" + simplerDate.format(createDate) + ");");

        return builder.toString();
    }

    public String defineRepresentation(){
        if (isDirectory){
            return "+";
        }
        return "-";
    }

}