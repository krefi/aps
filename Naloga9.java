import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Naloga9 {
    int Pnodes;
    int Tnodes;
    Node[] p;
    Node[] t;
    char x;
    int xCount = 0;
    int xNum = 0;
    int[] xLocations;
    int count = 0;

    public void read(String path) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            Pnodes = Integer.parseInt(br.readLine());
            p = new Node[Pnodes];
            for (int i = 0; i < Pnodes; i++) {
                String[] s = br.readLine().split(",");
                int id = Integer.parseInt(s[0]) - 1;
                p[id] = new Node();
                if (s[1].charAt(0) == 'X') {
                    xNum++;
                }
                p[id].name = s[1].charAt(0);
                for (int j = 2; j < s.length; j++) {
                    p[id].children.add(Integer.parseInt(s[j]));
                }
            }
            xLocations = new int[xNum];
            Tnodes = Integer.parseInt(br.readLine());
            t = new Node[Tnodes];
            for (int i = 0; i < Tnodes; i++) {
                String[] s = br.readLine().split(",");
                int id = Integer.parseInt(s[0]) - 1;
                t[id] = new Node();
                t[id].name = s[1].charAt(0);
                for (int j = 2; j < s.length; j++) {
                    t[id].children.add(Integer.parseInt(s[j]));
                }
            }
            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void solve() {
        for (int i = 0; i < Tnodes; i++) {
            x = '0';
            xCount = 0;
            count = 1;
            if (testChildren(i, 0)) {
                testX(xLocations.clone());
                System.out.println(count);
            }
        }
    }

    public boolean testChildren(int tId, int pId) {
        if (p[pId].children.size() != 0 && p[pId].children.size() != t[tId].children.size()) {
            return false;
        }
        for (int i = 0; i < p[pId].children.size(); i++) {
            count++;
            if (p[p[pId].children.get(i) - 1].name == 'X') {
                if (x == '0') {
                    x = t[t[tId].children.get(i) - 1].name;
                } else if (t[t[tId].children.get(i) - 1].name != x) {
                    return false;
                }
                xLocations[xCount] = t[tId].children.get(i) - 1;
                xCount++;

            } else if (p[p[pId].children.get(i) - 1].name != t[t[tId].children.get(i) - 1].name) {
                return false;
            }
            if (!testChildren(t[tId].children.get(i) - 1, p[pId].children.get(i) - 1)) {
                return false;
            }
            
        }
        return true;
    }

    public void testX(int[] locations) {
        int[] nextlocations = locations.clone();
       // System.out.println();
        for (int i = 0; i < xCount; i++) {
           // System.out.println(locations[i]);
            if(t[locations[0]].children.size() != t[locations[i]].children.size()){
                return;
            }
        }
        for (int i = 0; i < t[locations[0]].children.size(); i++) {
            for (int j = 0; j < xCount ; j++) {
                if(!(t[t[locations[0]].children.get(i) - 1].name ==  t[t[locations[j]].children.get(i) - 1].name)){
                    return;
                } 
            }
        }
        count += t[locations[0]].children.size() * xCount;
        for (int i = 0; i < t[locations[0]].children.size(); i++) {
            for (int j = 0; j < xCount ; j++) {
                nextlocations[j] = t[locations[j]].children.get(i) - 1;
            }
            testX(nextlocations.clone());
        }
    }

    public static void main(String[] args) {
        Naloga9 naloga = new Naloga9();
        naloga.read("I9_2.txt");
        naloga.solve();
        System.out.println(naloga);
    }

    public class Node {
        char name;
        ArrayList<Integer> children = new ArrayList<Integer>();
    }
}