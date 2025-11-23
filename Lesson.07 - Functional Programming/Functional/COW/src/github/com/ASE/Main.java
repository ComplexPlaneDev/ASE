package github.com.ASE;

public class Main {
	public static void main(String[] args) {
		Person p = new PersonImpl();
		p.setName("Bob");
		
		Person cow = CopyOnWrite.wrap(p, Person.class);
		System.out.println(p.getName() + " - " + cow.getName());
		System.out.println(p.getNum() + " - " + cow.getNum());
		
		cow.setName("Alice");
		System.out.println(p.getName() + " - " + cow.getName());
		System.out.println(p.getNum() + " - " + cow.getNum());
	}
}
