import java.util.Arrays;

public class TestCircularBag
{
    private static <T> void displayBag(BagInterface<T> bag)
    {
        System.out.printf("%2d: %s%n",
            bag.getCurrentSize(), Arrays.toString(bag.toArray()));
    }

    public static void main(String[] args)
    {
        BagInterface<Integer> bag = new CircularBag<>();
        displayBag(bag);

        for (int i = 0; i < 10; i++) {
            bag.add(i);
            displayBag(bag);
        }

        System.out.printf("%d in bag? %b%n", 4, bag.contains(4));
        System.out.printf("%d in bag? %b%n", 10, bag.contains(10));

        System.out.print("Remove 4: ");
            bag.remove(4);
            displayBag(bag);
        System.out.print("Remove 0: ");
            bag.remove(0);
            displayBag(bag);
        System.out.print("Remove 9: ");
            bag.remove(9);
            displayBag(bag);
        System.out.println("Remove last 6: ");
        for (int j = 0; j < 6; j++) {
            bag.remove();
            displayBag(bag);
        }
        System.out.println("Remove 1: ");
        bag.remove(1);
        displayBag(bag);
        System.out.println("Add -1: ");
        bag.add(-1);
        displayBag(bag);
        System.out.println("Remove last: ");
        bag.remove();
        displayBag(bag);

        bag.clear();
        int[] e = {2, 7, 1, 8, 2, 8, 1, 8, 2, 8, 4, 5, 9, 0, 4, 5};
        for (int i = 0; i < e.length; i++)
            bag.add(e[i]);
        displayBag(bag);

        System.out.printf("%d occurs %d times in bag%n", 8,
            bag.getFrequencyOf(8));
        System.out.printf("%d occurs %d times in bag%n", 2,
            bag.getFrequencyOf(2));
        System.out.printf("%d occurs %d times in bag%n", 6,
            bag.getFrequencyOf(6));
    }
}
