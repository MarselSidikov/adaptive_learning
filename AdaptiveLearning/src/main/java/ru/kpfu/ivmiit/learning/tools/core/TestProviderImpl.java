package ru.kpfu.ivmiit.learning.tools.core;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import ru.kpfu.ivmiit.learning.tools.models.Answer;
import ru.kpfu.ivmiit.learning.tools.models.Question;
import ru.kpfu.ivmiit.learning.tools.models.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Marsel Sidikov (Kazan Federal University)
 */
public class TestProviderImpl extends NamedParameterJdbcDaoSupport implements TestProvider {

    private static final String HSQL_COUNT_LESSON_ID = "SELECT COUNT(*) FROM Lessons WHERE " +
            "id = :materialId";

    private static final String HSQL_MAIN_TEST_ID_BY_LESSON_ID = "SELECT main_test_id FROM Lessons WHERE " +
            "id = :materialId";

    private static final String HSQL_GET_TEST_BY_ID = "SELECT (*) FROM Test WHERE " +
            "id = :testID";

    private static final String HSQL_GET_QUESTIONS_IDS_BY_TEST_ID = "SELECT question_ids FROM Test WHERE " +
            "id = :testID";

    private static final String HSQL_GET_QUESTION_BY_ID = "SELECT (*) FROM Questions WHERE " +
            "id = :questionID";

    private static final String HSQL_GET_ANSWER_BY_ID = "SELECT (*) FROM Answers WHERE " +
            "id = :answerID";

    private RowMapper<Answer> answerRowMapper = new RowMapper<Answer>() {
        @Override
        public Answer mapRow(ResultSet resultSet, int i) throws SQLException {
            int id  =  resultSet.getInt("id");
            String answers = resultSet.getString("answer");
            int relQuestionID  = resultSet.getInt("rel_questions_id");

            Answer answer = new Answer(answers);
            answer.setId(id);
            answer.setRelQuestionID(relQuestionID);

            return answer;
        }
    };

    private RowMapper<Question> questionsRowMapper = new RowMapper<Question>() {
        @Override
        public Question mapRow(ResultSet resultSet, int i) throws SQLException {
            int id = resultSet.getInt("id");
            String questions = resultSet.getString("question");
            int correctAnswerId = resultSet.getInt("correctAnswerId");
            int block = resultSet.getInt("block");

            SqlParameterSource namedParameters = new MapSqlParameterSource("answerID", correctAnswerId);

            Answer correctAnswer = getNamedParameterJdbcTemplate().queryForObject(HSQL_GET_ANSWER_BY_ID,
                    namedParameters, answerRowMapper);

            Question question = new Question(id, questions, block, correctAnswerId);
            question.setCorrectAnswer(correctAnswer);

            return question;
        }
    };

    private RowMapper<Test> testRowMapper = new RowMapper<Test>() {
        @Override
        public Test mapRow(ResultSet resultSet, int i) throws SQLException {
            Test test = new Test();

            int id = resultSet.getInt("id");
            List<Question> questionsList= new ArrayList<Question>();

            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("testID", id);

            String questionsIDs = getNamedParameterJdbcTemplate().queryForObject(HSQL_GET_QUESTIONS_IDS_BY_TEST_ID,
                    paramMap, String.class);

            String[] questionsIDsArray = questionsIDs.split(";");
            for (String questionID : questionsIDsArray) {
                paramMap.put("questionID", Integer.parseInt(questionID));
                Question question = getNamedParameterJdbcTemplate().queryForObject(HSQL_GET_QUESTION_BY_ID,
                        paramMap, questionsRowMapper);
                questionsList.add(question);
            }

            test.setId(id);
            test.setQuestions(questionsList);

            return test;
        }
    };

	@Override
	public Test getTest(List<Integer> results, int materialId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("materialId", materialId);

        int count = getNamedParameterJdbcTemplate().queryForObject(HSQL_COUNT_LESSON_ID, paramMap, Integer.class);
        if (count == 1) {
            Integer testID = getNamedParameterJdbcTemplate().queryForObject(HSQL_MAIN_TEST_ID_BY_LESSON_ID,
                    paramMap, Integer.class);

            if (testID != null) {
                paramMap.put("testID", testID);

                return getNamedParameterJdbcTemplate().queryForObject(HSQL_GET_TEST_BY_ID, paramMap, testRowMapper);
            }
            else {
                throw new IllegalArgumentException();
            }
        }
        else {
            throw new IllegalArgumentException();
        }
	}

	@Override
	public int getResult(String userToken, Answer answers) {
		return 0;
	}
}
