import java.util.*;

public class Test {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        int n = sc.nextInt();
        sc.nextInt();
        Map<String, Set<String>> graph = new HashMap();
        Set<String> nodes = new HashSet();
        for (int i = 0; i < n; i++) {
            String[] parts = sc.nextLine().split(" ");
            String from = parts[0];
            String to = parts[1];
            nodes.add(from);
            nodes.add(to);
            graph.computeIfAbsent(from, k -> new HashSet<>()).add(to);
        }
        String[] alerts = sc.nextLine().split(" ");
        HashSet<String> toDelete = new HashSet<>();

        LinkedList<String> queue = new LinkedList<>();
        HashSet<String> indegrees = new HashSet<>(nodes);
        Collection<Set<String>> values = graph.values();
        for (Set<String> edges : values) {
            indegrees.removeAll(edges);
        }

        queue.addAll(indegrees);
        while (!queue.isEmpty()){
            String node = queue.poll();
            Set<String> orDefault = graph.getOrDefault(node, Collections.emptySet());
            for (String neighbor : orDefault) {
                if (!toDelete.contains(neighbor)){
                    toDelete.add(neighbor);
                    queue.offer(neighbor);
                }
            }
        }

        for (String alert : alerts) {
            if (!toDelete.contains(alert)){
                System.out.println(alert+"");
            }
        }

    }
}
