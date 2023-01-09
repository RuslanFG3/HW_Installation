import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {
        List<GameProgress> list = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        String[][][] directoryName = {{{"src", "res", "savegames", "temp"}}, {{"main", "test"}}, {{"drawables", "vectors", "icons"}}};
        String[] address = {"F://Games", "F://Games/src", "F://Games/res"};
        String[] way = {"F://Games/savegames/save1.dat", "F://Games/savegames/save2.dat", "F://Games/savegames/save3.dat"};
        String zip = "F://Games/savegames/zip.zip";
        GameProgress gameProgress1 = new GameProgress(94, 10, 2, 254.32);
        GameProgress gameProgress2 = new GameProgress(104, 20, 12, 354.42);
        GameProgress gameProgress3 = new GameProgress(114, 30, 22, 454.52);
        list.add(gameProgress1);
        list.add(gameProgress2);
        list.add(gameProgress3);

        int i = 0;
        while (i < 3) {
            for (String[][] directory : directoryName) {
                for (String[] directory1 : directory) {
                    for (String directory2 : directory1) {
                        File f = new File(address[i], directory2);
                        if (f.mkdir()) {
                            sb.append("Каталог " + directory2 + " создан" + "\n");
                        }
                    }
                    i++;
                }
            }
        }

            String[] FileName = {"Main.java", "Utils.java"};
            for (String file : FileName) {
                File myFile = new File("F://Games/src/main", file);
                try {
                    if (myFile.createNewFile())
                        sb.append("Файл " + file + " был создан" + "\n");
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
            File myFile = new File("F://Games/temp", "temp.txt");
            try {
                if (myFile.createNewFile())
                    sb.append("Файл temp.txt был создан" + "\n");
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
            System.out.println(sb);
            try (FileOutputStream fos = new FileOutputStream("F://Games/temp/temp.txt")) {
                byte[] bytes = sb.toString().getBytes();
                fos.write(bytes, 0, bytes.length);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
            saveGame(way, list);
            zipFiles(zip, way);
            delete(way);
        }
        static void delete (String[]way){
            for (String file : way) {
                File dir = new File(file);
                dir.delete();
            }
        }
        static void saveGame (String[]way, List < GameProgress > list){
            for (int i = 0; i < way.length; i++) {
                try (FileOutputStream fos = new FileOutputStream(way[i]);
                     ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                    oos.writeObject(list.get(i));
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
        static void zipFiles (String zip, String[]way){
            try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(zip))) {
                for (int i = 0; i < way.length; i++) {
                    FileInputStream fis = new FileInputStream(way[i]);
                    ZipEntry entry = new ZipEntry("save" + (i + 1) + ".dat");
                    zout.putNextEntry(entry);
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    zout.write(buffer);
                    zout.closeEntry();
                    fis.close();
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

