package com.example.mergingfx;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class SolutionF extends Thread {

    private String explorer;
    private String outputfolder;
    private String explorer2;

    public void setExplorer(String explorer) {
        this.explorer = explorer;
    }
    public void setExplorer2(String explorer2) {this.explorer2 = explorer2;}
    public void setOutputfolder(String outputfolder) {
        this.outputfolder = outputfolder;
    }

    public void run() {

        System.out.println(explorer + "Running");

        File faceFolder = new File(explorer);
        File bodyFolder = new File(explorer2);

        ArrayList<File> faces = new ArrayList<>();
        ArrayList<File> bodies = new ArrayList<>();


        ArrayList<URL> bodiesURL = new ArrayList<>();
        ArrayList<URL> facesURL = new ArrayList<>();

        ArrayList<BufferedImage> im = new ArrayList<>();
        ArrayList<BufferedImage> zim = new ArrayList<>();
        ArrayList<BufferedImage> gesichter = new ArrayList<>();

        getAllFiles(faceFolder, faces);
        getAllFiles(bodyFolder, bodies);

        int n = 0;
        System.out.println(faces.size());
        System.out.println(bodies.size());
        for (int i = 0; i < bodies.size(); i++) {
            try {
                bodiesURL.add(bodies.get(i).toURI().toURL());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < faces.size(); i++) {
            try {
                facesURL.add(faces.get(i).toURI().toURL());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        String expression = " ";


        for (int j = 0; j < bodies.size(); j++) {
            try {
                im.add(ImageIO.read(bodies.get(j).toURI().toURL()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                zim.add(ImageIO.read(bodies.get(j).toURI().toURL()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < faces.size(); i++) {
            try {
                gesichter.add(ImageIO.read(faces.get(i).toURI().toURL()));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        for (int j = 0; j < bodies.size(); j++) {
            System.out.println("body angfang");
            for (int i = 0; i < faces.size(); i++) {

                try {
                    System.out.println(i);
                    //display(gesichter.get(i));
                    Graphics2D g = im.get(j).createGraphics();
                    g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
                    g.drawImage(gesichter.get(i), (im.get(j).getWidth() - gesichter.get(i).getWidth()) / 2, (im.get(j).getHeight() - gesichter.get(i).getHeight()) / 2, null);
                    g.dispose();

                    expression = switchCases(bodies, j, i, faces);
                    String bodyType = bodies.get(j).getName();
                    for (int k = 0; k < bodies.get(j).getName().length(); k++) {
                        int first = 0;
                        int second = 0;
                        if (bodies.get(j).getName().charAt(k) == '_' || bodies.get(j).getName().charAt(k) == '-') {
                            if (first == 0) {
                                first = k;
                            }
                            if (first != 0) {
                                bodyType = bodies.get(j).getName().substring(k + 1, bodies.get(j).getName().length() - 3);
                            }
                        }

                    }
                    //BodyType
                    File file = new File(outputfolder + "\\" + bodyType);

                    boolean dirCreated = file.mkdirs();
                    ImageIO.write(im.get(j), "png", new File(outputfolder + "\\" + bodyType + "\\" + expression + ".png"));

                    Graphics2D b = im.get(j).createGraphics();
                    b.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
                    b.drawImage(zim.get(j), (im.get(j).getWidth() - zim.get(j).getWidth()) / 2, (im.get(j).getHeight() - zim.get(j).getHeight()) / 2, null);
                    b.dispose();
                    n++;
                } catch (NullPointerException | IOException e) {
                    System.out.println("Fehler");
                }
            }
        }


    }


    public String switchCases(ArrayList<File> bodies, int j, int i, ArrayList<File> faces) {
        String result = "No output";
        for (int k = 0; k < bodies.get(j).getName().length(); k++) {
            int first = 0;
            int second = 0;
            if (bodies.get(j).getName().charAt(k) == '_' || bodies.get(j).getName().charAt(k) == '-') {
                if (first == 0) {
                    first = k;
                }
                if (first != 0) {
                    result = bodies.get(j).getName().substring(k + 1, bodies.get(j).getName().length() - 4) + "_";
                }
            }

        }
        if (bodies.get(j).getName().contains("blush")) {
            result += "Blush_";
        }
        if (bodies.get(j).getName().contains("open")) {
            if (bodies.get(j).getName().contains("hard")) {
                result += "Open_Hard_";
            }
            result += "Open_";
        }
        for (int k = 0; k < faces.get(i).getName().length(); k++) {
            int first = 0;
            int second = 0;
            if (faces.get(i).getName().charAt(k) == '_' || faces.get(i).getName().charAt(k) == '-') {
                if (first == 0) {
                    first = k;
                }
                if (first != 0) {
                    result = result + faces.get(i).getName().substring(k + 1, faces.get(i).getName().length() - 4);
                }
            }

        }
        System.out.println(result);
        return result;
    }

    private void getAllFiles(File file, ArrayList<File> list) {
        if (file.isDirectory()) {
            File[] subFiles = file.listFiles();
            if (subFiles == null) {
                return;
            }

            for (File subFile : subFiles) {
                getAllFiles(subFile, list);
            }
        } else {
            list.add(file);
        }
    }
}
