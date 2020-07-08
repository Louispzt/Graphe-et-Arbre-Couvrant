public class MutedInteger {
    int value = 1;

    public void increment () {
        ++value;
    }

    public void decrement () {
        --value;
    }

    public int  get (){
        return value;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }
}
