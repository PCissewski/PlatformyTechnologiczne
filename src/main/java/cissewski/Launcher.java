package cissewski;

import cissewski.pt.Element;
import cissewski.pt.ElementCompare;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashSet;
import java.util.Set;
import java.util.Date;
import java.util.TreeSet;

public class Launcher {

    /**
    @param args contains parameters
     index 0 contains sort type eg. "null"
     index 1 contains path to directory eg. "E:/Informatyka semestr 4/Platformy technologiczne/Labs"
     */

    public static void main(String[] args) throws IOException {

        Set<Element> files = setSortType(args[0]);

        File file = new File(args[1]);// Path to root file, replace with args[0]

        files.add(digDirectory(file, 0, args[0]));

        for (Element element: files) {
            printDir(element);
        }

        int nop = 0;
    }

    private static Date getCreationDate(File file) throws IOException {
        BasicFileAttributes butt = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
        return new Date(butt.creationTime().toMillis());
    }

    private static Element digDirectory(File file, int depth, String sortType) throws IOException {

        Element root = new Element(file.getName(), depth, getCreationDate(file), file.isDirectory(),sortType);

        if(file.isFile()){
            return root;
        }

        File[] subFiles = file.listFiles();
        if(subFiles == null){
            return root;
        }

        for (File subFile: subFiles) {
            Element element = digDirectory(subFile, depth + 1, sortType);
            root.getSubElements().add(element);
        }

        return root;
    }

    private static Set<Element> setSortType(String sortType) {
        if (sortType.equals("null")) {
            return new HashSet<>();
        }
        else if (sortType.equals("natural")){
            return new TreeSet<>();
        }
        else if (sortType.equals("alternative")){
            return new TreeSet<>(new ElementCompare());
        }
        else {
            throw new RuntimeException("No sortType set");
        }
    }

    private static void printDir(Element element){
        System.out.println(element);
        for (Element child: element.getSubElements()) {
            printDir(child);
        }
    }
}