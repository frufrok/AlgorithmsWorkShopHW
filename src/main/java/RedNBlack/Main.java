package RedNBlack;
public class Main {
    public static void main(String[] args) {
        Tree tree = new Tree();
        System.out.println("Наполняем дерево:");
        for (int i = 1; i<=50; i++) {
            int random = (int) Math.round(50 * Math.random());
            tree.insert(random, true);
        }
        System.out.println();
        System.out.println("Выполняем поиск по дереву:");
        System.out.println(tree.find(25));
        System.out.println(tree.find(35));

        System.out.println("Выводим минимум и максимум:");
        System.out.println(tree.min());
        System.out.println(tree.max());
    }
}
