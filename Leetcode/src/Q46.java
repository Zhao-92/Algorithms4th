import java.util.ArrayList;
import java.util.List;

/**
 * Created by ethan.zcl on 2017/6/8.
 */
public class Q46 {
    public List<List<Integer>> permute(int[] nums) {
        List<Integer> list = new ArrayList<Integer>();
        List<List<Integer>> ans = new ArrayList<List<Integer>>();
        boolean[] used = new boolean[nums.length];

        help(nums, used, list, ans);

        return ans;
    }

    public void help(int[] nums, boolean[] used, List<Integer> list, List<List<Integer>> ans) {

        for(int i=0; i<nums.length; i++) {
            if (used[i]) continue;
            list.add(nums[i]);
            used[i] = true;
            if (list.size() == nums.length) {
                List<Integer> tmp = new ArrayList<Integer>();
                tmp.addAll(list);
                ans.add(tmp);
            } else {
                help(nums, used, list, ans);
                used[i] = false;
            }
            list.remove(nums[i-1]);
        }
    }
}
