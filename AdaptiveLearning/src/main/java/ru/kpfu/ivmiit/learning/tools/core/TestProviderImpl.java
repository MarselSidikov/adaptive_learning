package ru.kpfu.ivmiit.learning.tools.core;

import ru.kpfu.ivmiit.learning.tools.models.Answers;
import ru.kpfu.ivmiit.learning.tools.models.Test;

import java.util.List;

/**
 * @author Marsel Sidikov (Kazan Federal University)
 */
public class TestProviderImpl implements TestProvider {
	@Override
	public Test getTest(List<Integer> results, int materialId) {
		return null;
	}

	@Override
	public int getResult(String userToken, Answers answers) {
		return 0;
	}
}
