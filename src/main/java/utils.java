import java.io.BufferedReader;
import java.io.InputStreamReader;

public class utils {

    public static String runCmd(String cmd) {
        String out = "";
        try {
            Runtime r = Runtime.getRuntime();
            Process p = r.exec("docker ps");
            p.waitFor();
            BufferedReader b = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = "";

            while ((line = b.readLine()) != null) {
                System.out.println(line);
            }
            out = b.toString();
            b.close();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return out;
    }


}
