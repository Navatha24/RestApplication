package additionservice;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.*;

@RestController
public class AdditionServiceController {
	
	@RequestMapping(value = "/addition", method = RequestMethod.GET, headers = "Accept=application/xml")
	public @ResponseBody Integer getAdditionResult(@PathVariable int number1, @PathVariable int number2)
			throws Exception {
		AdditionDaoService addService = new AdditionDaoService();
		int sum = addService.getSumOfTwoNumbers(number1, number2);
		return sum;
	}
}
