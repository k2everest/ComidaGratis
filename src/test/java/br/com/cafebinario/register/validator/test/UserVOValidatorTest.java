package br.com.cafebinario.register.validator.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.cafebinario.main.Main;
import br.com.cafebinario.register.vo.NewUserVO;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { Main.class })
public class UserVOValidatorTest {

	@Autowired
	private Validator validator;

	@SuppressWarnings("rawtypes")
	private Map<Object, Class> voMap;

	@Before
	public void setupValid() {
		voMap = new HashMap<>();
		voMap.put(NewUserVO.createUserVOJUnitValidTest(), NewUserVO.class);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void test() {
		List vos = loadVOs();
		vos.forEach(vo -> Assert.assertTrue(test(vo, voMap.get(vo)).isEmpty()));
	}

	@Test()
	public void testInvalid() {
		NewUserVO userVO = NewUserVO.createUserVOJUnitInvalidTest();
		List<ConstraintViolation<NewUserVO>> list = test(userVO, NewUserVO.class);
		Assert.assertTrue(list.size() == 5);
	}

	@SuppressWarnings("rawtypes")
	private List loadVOs() {
		return new ArrayList<>(voMap.values());
	}

	public <T> List<ConstraintViolation<T>> test(T bean, Class<T> clazz) {
		Set<ConstraintViolation<T>> setConstraintViolationUserVO = validator.validate(bean);

		List<ConstraintViolation<T>> list = new ArrayList<>();
		setConstraintViolationUserVO.forEach(constraintViolationUserVO -> {
			System.out.println("RootBean" + constraintViolationUserVO.getRootBean());
			System.out.println("InvalidValue=" + constraintViolationUserVO.getInvalidValue());
			System.out.println("Message=" + constraintViolationUserVO.getMessage());
			System.out.println("-----------------------------------");
			list.add(constraintViolationUserVO);
		});

		return list;
	}
}
