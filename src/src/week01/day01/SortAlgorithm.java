package week01.day01;

import assistant.Utils;

/**
 * description: 排序算法汇总
 * -------------------------------时间复杂度            空间复杂度      是否具有稳定性
 * 1.选择 select sort             O(n ^ 2)              O(1)              否
 * 2.冒泡 bubble sort             O(n ^ 2)              O(1)              是
 * 3.插入 insertion sort          O(n ^ 2)              O(1)              是
 * 4.归并 merge sort              O(n * log(n))         O(n)              是
 * 5.随快 random quick sort       O(n * log(n))         O(log(n))         否
 * 6.堆 heap sort                 O(n * log(n))         O(1)              否
 * 7.计数 count sort              O(n)                  O(m)              是
 * 8.基数 radix sort              O(n)                  O(m)              是
 * 下面都以非递减排序为例
 * author: Tang
 * package: week01.day01
 * 2024/1/15 16:51
 */
public class SortAlgorithm {
    private static final int MAX_N = 1000;
    private static final int[] help = new int[MAX_N];

    public static void main(String[] args) {
        int[] arr = {1, 3, 5, 7, 9, 8, 6, 4, 2, 1};
        Utils.printArray(arr);
        randomQuickSortImprove(arr, 0, arr.length - 1);
        Utils.printArray(arr);
    }

    /*
     * 选择排序：O(n ^ 2) O(1) 不稳定
     * 思路：每次从剩余未排序的元素中选择最小的放到当前位置
     * */
    public static void selectSort(int[] arr) {
        int n = arr.length;
        int minIndex;
        for (int i = 0; i < n; i++) {
            minIndex = i;
            for (int j = i; j < n; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            Utils.swap(arr, i, minIndex);
        }
    }

    /*
     * 冒泡排序：O(n ^ 2) O(1) 等于的时候不交换能达到稳定
     * 思路：两两比较
     * */
    public static void bubbleSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n - i; j++) {
                if (arr[i] > arr[j]) {
                    Utils.swap(arr, i, j);
                }
            }
        }
    }

    /*
     * 插入排序：O(n ^ 2) O(1) 稳定
     * 思路：假设当前已遍历完的元素有序, 从剩余元素中每次取一个插入到正确的位置
     * */
    public static void insertionSort(int[] arr) {
        int n = arr.length;
        for (int i = 1; i < n; i++) {
            for (int j = i; j > 0; j--) {
                if (arr[j] < arr[j - 1]) {
                    Utils.swap(arr, j, j - 1);
                }
            }
        }
    }

    /*
     * 归并排序递归版本
     * 注意 mid 属于左区间
     * */
    public static void mergeSort(int[] arr, int left, int right) {
        if (left == right) {
            return;
        }
        int mid = left + ((right - left) >> 1);
        mergeSort(arr, left, mid);
        mergeSort(arr, mid + 1, right);
        merge(arr, left, mid, right);
    }

    /*
     * step从 1 => 2 => 4 => 8...
     * 当mid > n-1的时候说明前 step 个元素已经完成排好序, 最后右侧只剩一个元素了
     * right得取 n-1 和正常迭代值left + (step << 1) - 1中的较小值, 可能出现最后一次的右部不足的情况
     * */
    public static void mergeSortNoRecursion(int[] arr) {
        int n = arr.length;
        int left, mid, right;
        for (int step = 1; step < n; step <<= 1) {
            for (left = 0; left < n; left = right + 1) {
                mid = left + step - 1;
                if (mid + 1 > n) {
                    break;
                }
                right = Math.min(n - 1, left + (step << 1) - 1);
                merge(arr, left, mid, right);
            }
        }
    }

    /*
     * 合并区间, 左右两个窗口滑动
     * 注意 index 从 left 开始
     * */
    public static void merge(int[] arr, int left, int mid, int right) {

        int index = left;
        int l = left, r = mid + 1;
        while (l <= mid && r <= right) {
            help[index++] = arr[l] < arr[r] ? arr[l++] : arr[r++];
        }
        while (l <= mid) {
            help[index++] = arr[l++];
        }
        while (r <= right) {
            help[index++] = arr[r++];
        }
        while (--index >= left) {
            arr[index] = help[index];
        }
    }

    public static void randomQuickSort(int[] arr, int left, int right) {
        if (left >= right) {
            return;
        }
        int partVal = arr[(int) (left + Math.random() * (right - left + 1))];
        int partIndex = partitionIndex(arr, left, right, partVal);
        randomQuickSort(arr, left, partIndex - 1);
        randomQuickSort(arr, partIndex + 1, right);
    }

    public static int partitionIndex(int[] arr, int left, int mid, int partVal) {
        int n = arr.length;
        int partIndex = 0;
        int slow, fast;
        for (slow = left, fast = left; fast < n; fast++) {
            if (arr[fast] <= partVal) {
                Utils.swap(arr, slow, fast);
                if (arr[slow] == partVal) {
                    partIndex = slow;
                }
                slow++;
            }
        }
        Utils.swap(arr, partIndex, --slow);
        return slow;
    }

    public static void randomQuickSortImprove(int[] arr, int left, int right) {
        if (left >= right) {
            return;
        }
        int partVal = arr[left + (int) (Math.random() * (right - left + 1))];
        int[] partIndexes = partitionIndexes(arr, left, right, partVal);
        randomQuickSortImprove(arr, left, partIndexes[0] - 1);
        randomQuickSortImprove(arr, partIndexes[1] + 1, right);

    }

    public static int[] partitionIndexes(int[] arr, int left, int right, int partVal) {
        int l = left, r = right;
        for (int i = l; i <= r; ) {
            if (arr[i] < partVal) {
                Utils.swap(arr, i++, l++);
            } else if (arr[i] > partVal) {
                Utils.swap(arr, r--, i);
            } else {
                i++;
            }
        }
        return new int[]{l, r};
    }


}
