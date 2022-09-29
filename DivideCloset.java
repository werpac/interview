import java.util.ArrayList;
import java.util.Arrays;

public class DivideCloset {

    //可行的层板移动方案的汇总
    ArrayList<ArrayList<Integer>> res = new ArrayList<>();
    //正在搜索的某种移动方案，存储已经移动过的层板
    ArrayList<Integer> current = new ArrayList<>();

    //每个层板的初始位置，下标0，对应最下面的层板
    int[] heights;
    //层板的个数
    int n;
    //这个层板是否移动到了最终位置
    boolean[] isVisited;

    /*
    树形搜索，回溯
    程序中使用下标，
    n个层板，n+1个空间，每个空间为2000/(n+1)，所以每个层板应该在的位置为：i*[2000/(n+1)]  (i为下标)

    主要的递归函数：attempt(int num){//尝试移动下标为num的层板，如果层板可以移动，则继续向下搜索
        if(层板num现在不能移动到最终位置){
            return;
        }
        标记移动了层板num
        更新搜索路径current
        记录层板num目前位置，便于回溯

        if(全部层板都移动到相应位置){
            将目前的搜索方案current加入到方案汇总res中
            不再继续向下搜索了，要恢复搜索前的状态
            标记未移动层板num
            删除搜索路径current的最后一位
            恢复层板num位置
        }

        向下搜索
        for（层板i：0到n-1）{
            if（层板i还没有移动）{
                递归调用 attempt(int i);
            }
        }

        不再继续向下搜索了，要恢复搜索前的状态
        标记未移动层板num
        删除搜索路径current的最后一位
        恢复层板num位置

    }
     */

    public static void main(String[] args) {
        DivideCloset divideCloset = new DivideCloset();
        divideCloset.decision(4,new int[]{50,600,700,1000});
        System.out.println(divideCloset.res);
    }

    /*
    顶层搜索
    输入参数：
    n：层板个数
    heights：每个层板的初始位置
     */
    public void decision(int n, int[] heights){
        this.n = n;
        this.heights = heights;
        isVisited = new boolean[n];
        Arrays.fill(isVisited,0, n, false);
        for (int i = 0; i < n; i++) {
            attempt(i);
        }
        return ;
    }

    //尝试移动第num（下标）个层板，如果可以移动，继续向下搜索
    //调用方法时，保证num层板还未移动
    public void attempt(int num){
        if(!moveAble(num)) return;

        isVisited[num] = true;
        int temp = heights[num];
        heights[num] = (num+1)*(2000/(n+1));
        current.add(num+1);

        if (isAllVisited()){
            ArrayList<Integer> solution = new ArrayList<>();
            for (Integer integer : current) {
                solution.add(new Integer(integer));
            }
            res.add(solution);

            isVisited[num] = false;
            heights[num] = temp;
            current.remove(current.size()-1);
            return;
        }
        for(int i=0; i<n; i++){
            if(!isVisited[i]){
                attempt(i);
            }
        }
        isVisited[num] = false;
        heights[num] = temp;
        current.remove(current.size()-1);
    }

    //判断是否所有的层板都已经移动过了
    public boolean isAllVisited(){
        for (int i = 0; i < n; i++) {
            if(isVisited[i] == false){
                return false;
            }
        }
        return true;
    }

    //判断num下标的层板能否移动到应该在的位置
    public boolean moveAble(int num){
        int finalPos = (num+1)*(2000/(n+1));
        if(finalPos == heights[num]) return true;

        if(heights[num]<finalPos){
            if(num+1 < n && heights[num+1]<=finalPos) {
                return false;
            }else{
                return true;
            }
        }else{
            if(num-1 >=0 && heights[num-1] >= finalPos){
                return false;
            }else{
                return true;
            }
        }
    }
}
