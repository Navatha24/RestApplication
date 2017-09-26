package additionservice;

public class AdditionDaoService implements AdditionInterface {

	public AdditionDaoService() {
	}

	@Override
	public Integer getSumOfTwoNumbers(int firstnumber, int secondnumber) throws Exception {
		System.err.println("firstnumber"+firstnumber+ "secondnumber"+secondnumber);
		return firstnumber + secondnumber;
	}
}
