package assistant;

/**
 * description: 工具类
 * author: Tang
 * package: assistant
 * 2024/1/15 17:10
 */
public class Utils {
    /*
     * 创建数组长度为number, 取值范围为[0, value)的数组
     * */
    public static int[] createArray(int number, int value) {
        int[] arr = new int[number];
        for (int i = 0; i < number; i++) {
            arr[i] = (int) (Math.random() * value);
        }
        return arr;
    }

    /*
     * 打印数组
     * */
    public static void printArray(int[] arr) {
        if (arr == null || arr.length == 0) {
            System.out.println("数组不存在或为空");
            return;
        }
        System.out.print("[");
        for (int i = 0; i < arr.length - 1; i++) {
            System.out.printf("%d, ", arr[i]);
        }
        System.out.printf("%d]\n", arr[arr.length - 1]);
    }

    /*
     * 交换数组arr中i和j两个位置的数字
     * */
    public static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
