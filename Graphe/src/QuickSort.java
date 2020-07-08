import java.util.ArrayList;
import java.util.Random;

class QuickSort
{
    SortEnumerator sortEnumerator;
    ArrayList<Integer> sortList;
    Random random;

    public QuickSort(SortEnumerator se) {
        sortEnumerator = se;
        random = new Random();
        sortList = new ArrayList<>();
        for (int i = 0; i < Main.graphe.size(); i++){
            sortList.add(i);
        }
        sort(0, sortList.size() - 1);
    }

    public int partition(int low, int high)
    {
        Edge pivot = Main.graphe.get(sortList.get(high));
        int i = (low-1); // index of smaller element
        for (int j=low; j<high; j++)
        {
            // If current element is smaller than the pivot
            if (compare(pivot, Main.graphe.get(sortList.get(j))))
            {
                i++;

                // swap i and j
                int temp = sortList.get(i);
                sortList.set(i, sortList.get(j));
                sortList.set(j, temp);
            }
        }

        // swap arr[i+1] and arr[high] (or pivot)
        int temp = sortList.get(i+1);
        sortList.set(i+1, sortList.get(high));
        sortList.set(high, temp);

        return i+1;
    }


    /* The main function that implements QuickSort()
      arr[] --> Array to be sorted,
      low  --> Starting index,
      high  --> Ending index */
    public void sort(int low, int high)
    {
        if (low < high)
        {
            /* pi is partitioning index, arr[pi] is
              now at right place */
            int pi = partition(low, high);

            // Recursively sort elements before
            // partition and after partition
            sort(low, pi-1);
            sort(pi+1, high);
        }
    }

    public boolean compare(Edge e1, Edge e2) {
        switch (sortEnumerator){
            case RANDOM:
                return random.nextBoolean();
            case ASCENDING:
                if (e1.successors.size() > e2.successors.size())
                    return true;
                if (e1.successors.size() == e2.successors.size() && e1.getVertex() > e2.getVertex())
                    return true;
                break;
            case DESCENDING:
                if (e1.successors.size() < e2.successors.size())
                    return true;
                if (e1.successors.size() == e2.successors.size() && e1.getVertex() > e2.getVertex())
                    return true;
                break;
            case ASCENDINGEDGES:
                if (e1.getVertex() >= e2.getVertex())
                    return true;
                break;
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder(sortEnumerator + " : {");
        for (int i = 0; i < sortList.size(); i++){
            str.append(sortList.get(i) + 1 );
            if (i<sortList.size() - 1)
                str.append(", ");
        }
        return str.append("}").toString();
    }
}