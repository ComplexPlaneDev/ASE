package github.com.ASE;

public class PersonImpl implements Person {
	private int num;
    private String name;

    public PersonImpl() {
    	this.num = 0;
    }
    
    @Override
    public String getName() {
    	return name;
	}
    
    @Override
    public void setName(String n) {
    	this.num++;
    	this.name = n;
    }
    
    @Override
    public int getNum() {
    	return this.num;
    }
}